package com.mobifone.signature.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Data
@Builder
public class Response {
    private Timestamp timestamp;
    private HttpStatus status;
    private String message;
    private String path;
}
