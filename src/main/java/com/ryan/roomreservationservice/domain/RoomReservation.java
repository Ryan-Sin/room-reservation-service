package com.ryan.roomreservationservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
public class RoomReservation {

    // 고유 키
    private Long roomReservationId;

    // 객실 고유 키
    private Long roomId;

    // 예약 상태(Ex - 예약정지: BLOCK, 예약대기: PENDING, 예약완료: CONFIRMED)
    private String reservationStatus;

    // 예약일 시작일
    private LocalDateTime reservationStartDate;

    // 예약일 종료일
    private LocalDateTime reservationEndDate;

    // 입실일
    private LocalTime checkInTime;

    // 퇴실일
    private LocalTime checkOutTime;

    // 성인 인원수
    private int numberOfAdults;

    // 아동 인원수
    private Integer numberOfChildren;

    // 유아 인원수
    private Integer numberOfInfants;

    // 기본 객실 가격
    private Integer defaultRoomPrice;

    // 총 결제 가격
    private Integer totalPricePaid;

    // 예약자 이름
    private String reservationPerson;

    // 예약자 휴대폰
    private String reservationPersonPhone;

    // 비상 연락처
    private String emergencyPhone;

    // 이메일
    private String email;

    // 도착예정 시간
    private LocalDateTime estimatedTimeOfArrival;

    // 예약 요청사항
    private String reservationRequest;

    // 유입 경로
    private String funnels;

    public Boolean checkAvailabilityStatus(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        if (this.reservationStartDate.getYear() > checkInDate.getYear() || this.reservationEndDate.getYear() > checkOutDate.getYear())
            return false;

        if ((this.reservationStartDate.getYear() > checkInDate.getYear() && this.reservationStartDate.getMonth().compareTo(checkInDate.getMonth()) < 0) ||
                (this.reservationEndDate.getYear() > checkOutDate.getYear() && this.reservationEndDate.getMonth().compareTo(checkOutDate.getMonth()) > 0))
            return false;

        if ((this.reservationStartDate.getDayOfMonth() > checkInDate.getDayOfMonth()) ||
                (this.reservationEndDate.getDayOfMonth() > checkOutDate.getDayOfMonth())) return false;

        return true;
    }

}
