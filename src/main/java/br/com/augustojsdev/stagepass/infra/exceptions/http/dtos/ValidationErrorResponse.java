package br.com.augustojsdev.stagepass.infra.exceptions.http.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ValidationErrorResponse {

    private int statusCode;

    private String message;

    private Map<String, List<String>> errors;

    private OffsetDateTime timestamp;

    private String path;
}