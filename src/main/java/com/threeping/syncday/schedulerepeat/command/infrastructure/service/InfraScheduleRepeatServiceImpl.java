package com.threeping.syncday.schedulerepeat.command.infrastructure.service;

import com.threeping.syncday.schedule.command.aggregate.dto.ScheduleDTO;
import com.threeping.syncday.schedule.command.application.service.AppScheduleService;
import com.threeping.syncday.schedulerepeat.command.aggregate.dto.CreateRepeatedScheduleDTO;
import com.threeping.syncday.schedulerepeat.command.aggregate.dto.CreateScheduleRepeatDTO;
import com.threeping.syncday.schedulerepeat.command.aggregate.dto.SendScheduleRepeatMailDTO;
import com.threeping.syncday.schedulerepeat.command.aggregate.vo.ScheduleDurationVO;
import com.threeping.syncday.schedulerepeatparticipant.command.application.service.ScheduleRepeatParticipantService;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class InfraScheduleRepeatServiceImpl implements InfraScheduleRepeatService {

    private final AppScheduleService appScheduleService;
    private final ScheduleRepeatParticipantService scheduleRepeatParticipantService;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    InfraScheduleRepeatServiceImpl(AppScheduleService appScheduleService,
                                   ScheduleRepeatParticipantService scheduleRepeatParticipantService,
                                   JavaMailSender javaMailSender,
                                   SpringTemplateEngine templateEngine){
        this.appScheduleService = appScheduleService;
        this.scheduleRepeatParticipantService = scheduleRepeatParticipantService;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }


    @Override
    public void createSchedule(ScheduleDurationVO scheduleDuration,
                               CreateRepeatedScheduleDTO createRepeatedScheduleDTO,
                               Long repeatOrder) {
        ScheduleDTO newScheduleDTO = makeNewScheduleDTO(scheduleDuration,createRepeatedScheduleDTO,repeatOrder);

        appScheduleService.addSchedule(newScheduleDTO);
    }

    @Override
    public void createScheduleRepeatParticipants(Long repeatId, CreateScheduleRepeatDTO createScheduleRepeatDTO) {
        scheduleRepeatParticipantService.createScheduleRepeatParticipant(repeatId,createScheduleRepeatDTO);
    }

    @Override
    public void sendMailToRepeatScheduleParticipants(SendScheduleRepeatMailDTO sendScheduleRepeatMailDTO){
        Map<String,Object> varMap = new HashMap<>();
        varMap.put("sender",sendScheduleRepeatMailDTO.getUserName());
        varMap.put("title",sendScheduleRepeatMailDTO.getTitle());
        varMap.put("date",sendScheduleRepeatMailDTO.getStartTime().toLocalDateTime());
        varMap.put("recurrence",sendScheduleRepeatMailDTO.getRecurrenceType());

        String title =
                sendScheduleRepeatMailDTO.getUserName() + "님의 "
                        + sendScheduleRepeatMailDTO.getTitle() + "일정(반복) 초대";

        for(String email : sendScheduleRepeatMailDTO.getEmails()) {
            sendMail(email,title,varMap);
        }
    }

    private ScheduleDTO makeNewScheduleDTO(ScheduleDurationVO scheduleDuration,
                                           CreateRepeatedScheduleDTO createRepeatedScheduleDTO,
                                           Long repeatOrder) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setUserId(createRepeatedScheduleDTO.getUserId());
        scheduleDTO.setTitle(createRepeatedScheduleDTO.getTitle());
        scheduleDTO.setContent(createRepeatedScheduleDTO.getContent());
        scheduleDTO.setStartTime(scheduleDuration.start());
        scheduleDTO.setEndTime(scheduleDuration.end());
        scheduleDTO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        scheduleDTO.setPublicStatus(createRepeatedScheduleDTO.getPublicStatus());
        scheduleDTO.setMeetingStatus(createRepeatedScheduleDTO.getMeetingStatus());
        scheduleDTO.setAttendeeIds(createRepeatedScheduleDTO.getParticipants());
        return scheduleDTO;
    }

    private void sendMail(String email, String title, Map<String,Object> varMap){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(setContext(varMap), true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
    private String setContext(Map<String,Object> varMap) {
        Context context = new Context();
        context.setVariables(varMap);
        return templateEngine.process("SCHEDULE_REPEAT", context);
    }
}
