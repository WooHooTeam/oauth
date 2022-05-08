package com.security.oauth.handler;

import com.security.oauth.response.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseMessage> handleAuthenticationException(AuthenticationException e){
        ResponseMessage responseMessage = ResponseMessage.builder()
                    .responseTime(new Date())
                    .build();

        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.UNAUTHORIZED);
    }
}
