package com.threeping.syncday.schedule.command.Infrastructure;

import com.threeping.syncday.scheduleparticipant.command.application.service.AppScheduleParticipantService;
import com.threeping.syncday.user.command.application.dto.UserDTO;
import com.threeping.syncday.user.query.service.UserQueryService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class InfraScheduleServiceImpl implements InfraScheduleService{

    private final AppScheduleParticipantService appScheduleParticipantService;
    private final UserQueryService userQueryService;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public InfraScheduleServiceImpl(AppScheduleParticipantService appScheduleParticipantService,
                                    UserQueryService userQueryService,
                                    JavaMailSender javaMailSender,
                                    TemplateEngine templateEngine) {
        this.appScheduleParticipantService = appScheduleParticipantService;
        this.userQueryService = userQueryService;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void requestAddScheduleParticipant(Long userId, Long scheduleId, List<Long> attendeeIds, Timestamp notificationTime) {
        appScheduleParticipantService.addScheduleParticipant(userId, scheduleId, attendeeIds, notificationTime);
    }

    @Override
    public void requestUpdateScheduleParticipant(Long userId, Long scheduleId, List<Long> attendeeIds) {
        appScheduleParticipantService.updateScheduleParticipant(userId, scheduleId, attendeeIds);
    }

    @Override
    public UserDTO findUserById(Long userId){
        return userQueryService.findById(userId);
    }

    @Async
    @Override
    public void sendMailToParticipants(List<String> emails,
                                       String title,
                                       Map<String, Object> varMap){
        try{
            MimeMessage[] mimeMessages = emails.stream().map(
                    email -> {
                        try {
                            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
                            helper.setTo(email);
                            helper.setSubject(title);
                            helper.setText(setContext(varMap),true);
                            return mimeMessage;
                        } catch (MessagingException e) {
                            log.info("Failed to create email for :{}",email,e);
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .toArray(MimeMessage[]::new);

            javaMailSender.send(mimeMessages);
            log.info("emails sent");
        } catch (Exception e){
            log.info("Failed to send emails",e);
        }

    }

    private String setContext(Map<String,Object> varMap) {
        Context context = new Context();
        context.setVariables(varMap);
        return templateEngine.process("SCHEDULE", context);
    }
}
