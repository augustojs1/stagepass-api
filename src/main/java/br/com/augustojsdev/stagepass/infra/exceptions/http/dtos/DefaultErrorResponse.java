package br.com.augustojsdev.stagepass.infra.exceptions.http.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DefaultErrorResponse {
    private int statusCode;
    private String error;
    private String message;
    private OffsetDateTime timestamp;
    private String path;
}