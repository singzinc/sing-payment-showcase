package com.sing.payment.service;

import java.util.List;

import com.sing.payment.model.SingPayment;

public interface SingPaymentService {

	public SingPayment savePayment(SingPayment singPayment) throws Exception;

	public SingPayment updatePayment(SingPayment singPayment) throws Exception;

	public List<SingPayment> findAll() throws Exception;

}
