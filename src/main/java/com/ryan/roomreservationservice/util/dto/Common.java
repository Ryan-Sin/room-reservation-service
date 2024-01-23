package com.ryan.roomreservationservice.util.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ryan.roomreservationservice.util.enums.StatusCode;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Common {
    @Builder.Default
    private String createdAt = Instant.now().toString();
    @Builder.Default
    private String status = StatusCode.SUCCESS.getStatusCode();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object message;
}
