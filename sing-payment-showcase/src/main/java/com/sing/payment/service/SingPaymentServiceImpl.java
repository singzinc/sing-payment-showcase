package com.sing.payment.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sing.payment.dao.SingPaymentDao;
import com.sing.payment.model.SingPayment;

@Service
public class SingPaymentServiceImpl implements SingPaymentService {
	private static final Logger logger = LoggerFactory.getLogger(SingPaymentServiceImpl.class);

	@Autowired
	private SingPaymentDao singPaymentDao;

	@Override
	public SingPayment savePayment(SingPayment singPayment) throws Exception {
		singPaymentDao.save(singPayment);
		return singPayment;
	}

	@Override
	public SingPayment updatePayment(SingPayment singPayment) throws Exception {
		singPaymentDao.update(singPayment);
		return singPayment;
	}

	public List<SingPayment> findAll() throws Exception {
		return singPaymentDao.findAll();
	}
}
