package com.selec.moondrift.boss.exception.handler;

import com.selec.moondrift.boss.exception.MissingMemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExportExceptionHandler {

    @ExceptionHandler(MissingMemberException.class)
    ResponseEntity<Object> handleMissingMemberException(MissingMemberException ex) {
        return ResponseEntity.internalServerError()
                .body(ex.getDiscordName() + "(" + ex.getMemberId() + ")" + " is missing in the map");
    }

}
