package com.supersapiens.athlete.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ApiError {
    private Date timeStamp;
    private String errorCode;
    private String message;
    private String path;
}