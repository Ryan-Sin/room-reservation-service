package com.ryan.roomreservationservice.domain;

import com.ryan.roomreservationservice.util.enums.ReservationStatus;
import com.ryan.roomreservationservice.util.enums.RoomStatus;
import com.ryan.roomreservationservice.util.exception.CommonException;
import com.ryan.roomreservationservice.util.exception.ErrorMessage;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.Period;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationSlotTest {

    @Test
    public void 예약가능_객실_필터링() {
        // given(준비): 어떠한 데이터가 준비되었을 때
        Room room = Room.builder()
                .roomId(1L)
                .zoneCode("Asia/Seoul")
                .roomStatus(RoomStatus.EXPOSURE_POSSIBLE)
                .roomName("그린룸1")
                .roomSize("4")
                .build();

        Instant now = Instant.now();

        List<ReservationSlot> reservationSlots = IntStream.range(1, 11)
                .mapToObj(i -> {
                            ReservationStatus status = (i == 1) ? ReservationStatus.BLOCK : ReservationStatus.AVAILABLE;
                            return ReservationSlot.builder()
                                    .room(room)
                                    .reservationStatus(status)
                                    .reservationDate(now.plus(Period.ofDays(i)))
                                    .build();
                        }
                ).toList();

        // when(실행): 어떠한 함수를 실행하면
        List<ReservationSlot> availableReservationSlots = reservationSlots.stream()
                .filter(ReservationSlot::isAvailableStatus).toList();

        // then(검증): 어떠한 결과가 나와야 한다.
        assertThat(availableReservationSlots.size()).isEqualTo(9);
    }


    @Test
    public void 객실_예약대기_상태로_변경() {
        // given(준비): 어떠한 데이터가 준비되었을 때
        Room room = Room.builder()
                .roomId(1L)
                .zoneCode("Asia/Seoul")
                .roomStatus(RoomStatus.EXPOSURE_POSSIBLE)
                .roomName("그린룸1")
                .roomSize("4")
                .build();

        Instant now = Instant.now();

        ReservationSlot reservationSlot = ReservationSlot.builder()
                .room(room)
                .reservationStatus(ReservationStatus.AVAILABLE)
                .reservationDate(now)
                .build();

        // when(실행): 어떠한 함수를 실행하면
        reservationSlot.transitionToPending();

        // then(검증): 어떠한 결과가 나와야 한다.
        assertThat(reservationSlot.getReservationStatus()).isEqualTo(ReservationStatus.PENDING);
    }

    @Test
    public void 예약대기_상태로_변경실패() {
        // given(준비): 어떠한 데이터가 준비되었을 때
        Room room = Room.builder()
                .roomId(1L)
                .zoneCode("Asia/Seoul")
                .roomStatus(RoomStatus.EXPOSURE_POSSIBLE)
                .roomName("그린룸1")
                .roomSize("4")
                .build();

        Instant now = Instant.now();

        ReservationSlot reservationSlot = ReservationSlot.builder()
                .room(room)
                .reservationStatus(ReservationStatus.BLOCK)
                .reservationDate(now)
                .build();

        // when(실행): 어떠한 함수를 실행하면
        CommonException commonException = assertThrows(CommonException.class, () -> reservationSlot.transitionToPending());

        // then(검증): 어떠한 결과가 나와야 한다.
        assertThat(commonException.getClientErrorMessage()).isEqualTo(ErrorMessage.NOT_TRANSITION_TO_PENDING);
    }

    @Test
    public void 예약완료_상태로_변경() {
        // given(준비): 어떠한 데이터가 준비되었을 때
        Room room = Room.builder()
                .roomId(1L)
                .zoneCode("Asia/Seoul")
                .roomStatus(RoomStatus.EXPOSURE_POSSIBLE)
                .roomName("그린룸1")
                .roomSize("4")
                .build();

        Instant now = Instant.now();

        ReservationSlot reservationSlot = ReservationSlot.builder()
                .room(room)
                .reservationStatus(ReservationStatus.PENDING)
                .reservationDate(now)
                .build();

        // when(실행): 어떠한 함수를 실행하면
        reservationSlot.confirmReservation();

        // then(검증): 어떠한 결과가 나와야 한다.
        assertThat(reservationSlot.getReservationStatus()).isEqualTo(ReservationStatus.CONFIRMED);
    }

    @Test
    public void 예약완료_상태로_변경실패() {
        // given(준비): 어떠한 데이터가 준비되었을 때
        Room room = Room.builder()
                .roomId(1L)
                .zoneCode("Asia/Seoul")
                .roomStatus(RoomStatus.EXPOSURE_POSSIBLE)
                .roomName("그린룸1")
                .roomSize("4")
                .build();

        Instant now = Instant.now();

        ReservationSlot reservationSlot = ReservationSlot.builder()
                .room(room)
                .reservationStatus(ReservationStatus.AVAILABLE)
                .reservationDate(now)
                .build();

        // when(실행): 어떠한 함수를 실행하면
        CommonException commonException = assertThrows(CommonException.class, () -> reservationSlot.confirmReservation());

        // then(검증): 어떠한 결과가 나와야 한다.
        assertThat(commonException.getClientErrorMessage()).isEqualTo(ErrorMessage.NOT_CONFIRM_RESERVATION);
    }
}