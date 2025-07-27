package com.microservice.learn.api.gateway.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    /**
     * The timestamp when the error occurred.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * The HTTP status code indicating the error.
     */
    private int status;

    /**
     * The error type or category.
     */
    private String error;

    /**
     * A descriptive message explaining the error.
     */
    private String message;

}