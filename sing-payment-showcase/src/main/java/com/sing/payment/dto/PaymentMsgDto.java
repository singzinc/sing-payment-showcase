package com.sing.payment.dto;

public class PaymentMsgDto {
	private Long paymentId;
	private String paypalPaymentId;

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaypalPaymentId() {
		return paypalPaymentId;
	}

	public void setPaypalPaymentId(String paypalPaymentId) {
		this.paypalPaymentId = paypalPaymentId;
	}

}
