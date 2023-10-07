package com.company.kunuzdemo.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppErrorDTO implements Serializable {
    private final String errorPath;
    private final String errorMessage;
    private final Integer errorCode;
    private final Object errorBody;
    private final Long timestamp;

    public AppErrorDTO(String errorPath, String errorMessage, Integer errorCode) {
        this(errorPath, errorMessage, null, errorCode);
    }

    public AppErrorDTO(String errorPath, String errorMessage, Object errorBody, Integer errorCode) {
        this.errorPath = errorPath;
        this.errorMessage = errorMessage;
        this.errorBody = errorBody;
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }
}