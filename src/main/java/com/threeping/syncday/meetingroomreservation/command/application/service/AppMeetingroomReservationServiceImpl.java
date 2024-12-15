package com.threeping.syncday.meetingroomreservation.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.meetingroom.command.aggregate.entity.Meetingroom;
import com.threeping.syncday.meetingroom.command.domain.repository.MeetingroomRepository;
import com.threeping.syncday.meetingroomreservation.command.aggregate.dto.MeetingroomReservationDTO;
import com.threeping.syncday.meetingroomreservation.command.aggregate.entity.MeetingroomReservation;
import com.threeping.syncday.meetingroomreservation.command.domain.repository.MeetingroomReservationRepository;
import com.threeping.syncday.meetingroomreservation.command.infrastructure.InfraMeetingroomReservationService;
import com.threeping.syncday.schedule.command.aggregate.dto.ScheduleDTO;
import com.threeping.syncday.schedule.command.aggregate.entity.MeetingStatus;
import com.threeping.syncday.schedule.command.aggregate.entity.PublicStatus;
import com.threeping.syncday.schedule.command.aggregate.entity.Schedule;
import com.threeping.syncday.schedule.command.domain.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AppMeetingroomReservationServiceImpl implements AppMeetingroomReservationService {

    private final MeetingroomReservationRepository meetingroomReservationRepository;
    private final ModelMapper modelMapper;
    private final MeetingroomRepository meetingroomRepository;
    private final ScheduleRepository scheduleRepository;
    private final InfraMeetingroomReservationService infraMeetingroomReservationService;

    @Autowired
    public AppMeetingroomReservationServiceImpl(MeetingroomReservationRepository meetingroomReservationRepository,
                                                ModelMapper modelMapper,
                                                MeetingroomRepository meetingroomRepository,
                                                ScheduleRepository scheduleRepository,
                                                InfraMeetingroomReservationService infraMeetingroomReservationService
    ) {
        this.meetingroomReservationRepository = meetingroomReservationRepository;
        this.modelMapper = modelMapper;
        this.meetingroomRepository = meetingroomRepository;
        this.scheduleRepository = scheduleRepository;
        this.infraMeetingroomReservationService = infraMeetingroomReservationService;
    }

    @Transactional
    @Override
    public List<MeetingroomReservationDTO> addMeetingroomReservation(MeetingroomReservationDTO meetingroomReservationDTO) {
        List<MeetingroomReservationDTO> responseList = new ArrayList<>();

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setTitle(meetingroomReservationDTO.getTitle());
        scheduleDTO.setContent(meetingroomReservationDTO.getContent());
        scheduleDTO.setStartTime(meetingroomReservationDTO.getStartTime());
        scheduleDTO.setEndTime(meetingroomReservationDTO.getEndTime());
        scheduleDTO.setMeetingroomId(meetingroomReservationDTO.getMeetingroomId());
        scheduleDTO.setMeetingStatus(MeetingStatus.ACTIVE);
        scheduleDTO.setPublicStatus(PublicStatus.PUBLIC);
        scheduleDTO.setUserId(meetingroomReservationDTO.getUserId());
//        scheduleDTO.setAttendeeIds(new ArrayList<>());
        scheduleDTO.setAttendeeIds(meetingroomReservationDTO.getAttendeeIds());

        ScheduleDTO newScheduleDTO = infraMeetingroomReservationService.requestAddSchedule(scheduleDTO);
        log.info("New schedule: {}", newScheduleDTO);

        // Schedule 및 Meetingroom 엔티티를 조회하여 설정
        Schedule schedule = scheduleRepository.findById(newScheduleDTO.getScheduleId())
                .orElseThrow(() -> new CommonException(ErrorCode.INTERNAL_SERVER_ERROR));
        Meetingroom meetingRoom = meetingroomRepository.findById(newScheduleDTO.getMeetingroomId())
                .orElseThrow(() -> new CommonException(ErrorCode.INTERNAL_SERVER_ERROR));

        // 10분 단위로 MeetingroomReservation 생성 및 저장
        Timestamp startTime = meetingroomReservationDTO.getStartTime();
        Timestamp endTime = meetingroomReservationDTO.getEndTime();

        while (startTime.before(endTime)) {
            MeetingroomReservation reservation = new MeetingroomReservation();
            reservation.setSchedule(schedule.getScheduleId());
            reservation.setMeetingRoom(meetingRoom.getMeetingroomId());
            reservation.setMeetingTime(startTime);
//            reservation.setMeetingTime(new Timestamp(startTime.getTime()));

            MeetingroomReservation savedReservation = meetingroomReservationRepository.save(reservation);

            // 수동으로 DTO로 매핑
            MeetingroomReservationDTO responseDTO = new MeetingroomReservationDTO();
            responseDTO.setScheduleId(schedule.getScheduleId());
            responseDTO.setMeetingroomReservationId(savedReservation.getMeetingroomReservationId());
            responseDTO.setMeetingroomId(newScheduleDTO.getMeetingroomId());

            responseDTO.setStartTime(newScheduleDTO.getStartTime());
            responseDTO.setEndTime(newScheduleDTO.getEndTime());
            responseDTO.setTitle(newScheduleDTO.getTitle());
            responseDTO.setContent(newScheduleDTO.getContent());
            responseDTO.setUserId(newScheduleDTO.getUserId());

            responseList.add(responseDTO);

            // 10분 추가
            //startTime = startTime.plus(Duration.ofMinutes(10));
            startTime = new Timestamp(startTime.getTime() + Duration.ofMinutes(10).toMillis());
        }

        return responseList;
    }

    @Transactional
    @Override
    public void deleteMeetingroomReservationByScheduleId(Long scheduleId) {
        // Schedule ID와 관련된 모든 MeetingroomReservation을 조회
        List<MeetingroomReservation> reservations = meetingroomReservationRepository.findBySchedule(scheduleId);
        if (reservations.isEmpty()) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        // 관련된 MeetingroomReservation 삭제
        reservations.forEach(reservation -> {
            meetingroomReservationRepository.delete(reservation);
            log.info("Deleted meetingroom reservation with ID: {} and associated schedule ID: {}", reservation.getMeetingroomReservationId(), scheduleId);
        });

        // Schedule 관련 삭제 작업 수행
        infraMeetingroomReservationService.requestDeleteSchedule(scheduleId);
    }

}

