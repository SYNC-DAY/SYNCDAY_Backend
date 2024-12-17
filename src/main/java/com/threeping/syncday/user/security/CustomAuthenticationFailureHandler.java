package com.threeping.syncday.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = exception.getMessage();
        log.error(message);
        /*  */
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");

        CommonException commonException;
        if (Objects.equals(exception.getMessage(), ErrorCode.NOT_FOUND_USER.getMessage())) {
            commonException = new CommonException(ErrorCode.NOT_FOUND_USER);
        } else if (Objects.equals(exception.getMessage(), ErrorCode.INVALID_PASSWORD.getMessage())) {
            commonException = new CommonException(ErrorCode.INVALID_PASSWORD);
        }
        else {
            commonException = new CommonException(ErrorCode.LOGIN_FAILURE);
        }

        ResponseDTO<Object> errorResponse = ResponseDTO.fail(commonException);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));


    }
}
