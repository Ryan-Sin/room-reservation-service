package com.ryan.roomreservationservice.adapter.in.web.dto;

import com.ryan.roomreservationservice.domain.Accommodation;
import com.ryan.roomreservationservice.domain.record.LocalDateRange;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

public class ReservationDto {

    @Getter
    @ToString
    @NoArgsConstructor
    public static class ReserveRequestDto {
        @Schema(description = "예약할 객실 이름", example = "레드룸1")
        private String roomName;

        @Schema(description = "예약 기간 (시작일과 종료일 포함)", example = "{\"start\": \"2024-09-29\", \"end\": \"2024-10-01\"}")
        private LocalDateRange reservationDate;
    }

    @Getter
    @Builder
    @ToString
    public static class GetReservationsResponseDto {
        @Schema(description = "예약 ID", example = "1")
        private Long reservationId;

        @Schema(description = "예약 날짜 (시작일과 종료일 포함)", example = "{\"start\": \"2024-09-29\", \"end\": \"2024-10-01\"}")
        private LocalDateRange reservationDate;

        @Schema(description = "숙소 정보")
        private Accommodation accommodation;
    }

    @Getter
    @Setter
    @ToString
    public static class ConfirmAccommodationReservationByMemberRequestDto {
        @Schema(description = "확인할 객실 이름", example = "레드룸1")
        private String roomName;

        @Schema(description = "예약 기간 (시작일과 종료일 포함)", example = "2024-09-29")
        private String start;

        @Schema(description = "예약 기간 (시작일과 종료일 포함)", example = "2024-10-01")
        private String end;
    }

    @Getter
    @Builder
    @ToString
    public static class ConfirmAccommodationReservationByMemberResponseDto {
        @Schema(description = "숙소 예약 확인 여부", example = "true")
        boolean confirmAccommodation;
    }
}
