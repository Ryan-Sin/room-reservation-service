package com.ryan.roomreservationservice.domain;

import com.ryan.roomreservationservice.domain.enums.PaymentMethod;

public class RegularCardPayment implements PaymentProcess {
    @Override
    public boolean support(PaymentMethod paymentMethod) {
        return PaymentMethod.REGULAR_CARD == paymentMethod;
    }

    @Override
    public PaymentInfo pay(Reservation reservation, long price) {
        //Todo 외부 PG사 결제 로직 처리
        String transactionId = "";
        String receipt = "https://****.com";
        return new PaymentInfo(transactionId, receipt);
    }

    @Override
    public void cancel(String transactionId) {

    }
}
