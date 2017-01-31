package com.sing.payment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.sing.payment.dto.PaymentMsgDto;
import com.sing.payment.model.SingPayment;
import com.sing.payment.service.SingPaymentService;
import com.sing.payment.util.CreditCardUtil;
import com.sing.payment.util.PaypalObjHandler;

@CrossOrigin
@Controller
@Api(value = "/paypal", description = "payment API")
@RequestMapping(value = "Paypal")
public class PayPalPaymentController {

	@Autowired
	private RestTemplate simpleRestTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private SingPaymentService singPaymentService;

	private static final String clientId = "AZXD9MsjzMzcnkoiBov9YOoQuQD_iR5_edjEjvEe7Lq_-6m";
	private static final String clientSecret = "EIdeTsXDakPwyNYk_nttUzZpRK8sCcRqRFyCWRlpkJKHSM";
	/*
		@Value("${paypal.clientId}")
		private static String clientId;

		@Value("${paypal.clientSecret}")
		private static String clientSecret;
	*/
	private static final Logger logger = LoggerFactory.getLogger(PayPalPaymentController.class);

	@ApiOperation(notes = " Java version .  ", httpMethod = "POST", value = "Paypal payment test")
	@RequestMapping(value = "/creditCardPayment", method = RequestMethod.POST)
	@ResponseBody
	public PaymentMsgDto payPalCreditCardPayment(@RequestBody SingPayment paymentDto) {
		logger.info("Start Paypal payment process");

		PaymentMsgDto paymentMsgDto = new PaymentMsgDto();
		try {
			CreditCardUtil creditCardUtil = new CreditCardUtil();
			PaypalObjHandler paypalObjHandler = new PaypalObjHandler();

			paymentDto = creditCardUtil.checkCreditCard(paymentDto);

			if (paymentDto.getPaymentMethod().equals("paypal")) {

				logger.info("vaild, start paypal payment");
				Date cDate = new Date();
				paymentDto.setCreateDate(cDate);
				paymentDto.setLastModifiedDate(cDate);
				paymentDto.setProcessStatus("processing");
				singPaymentService.savePayment(paymentDto);

				paymentMsgDto.setPaymentId(paymentDto.getSingPaymentId());
				APIContext context = new APIContext(clientId, clientSecret, "sandbox");

				Map<String, String> config = context.getConfigurationMap();
				config.put("http.ReadTimeOut", "60000");
				config.put("http.ConnectionTimeOut", "60000");
				context.setConfigurationMap(config);

				//================================

				Payment payment = paypalObjHandler.convertToPayPalPaymentObj(paymentDto);

				payment.create(context);

				Payment createdPayment = payment.create(context);

				if (createdPayment.getId() != null) {
					logger.info("payment OK");
					paymentDto.setReferCode(createdPayment.getId());
					paymentDto.setProcessStatus(createdPayment.getState());
					paymentMsgDto.setPaypalPaymentId(createdPayment.getId());

					// set the redis
					ValueOperations<String, SingPayment> valueopsPayment = redisTemplate.opsForValue();
					String key = paymentDto.getCustomerName() + String.valueOf(paymentDto.getSingPaymentId());
					valueopsPayment.set(key, paymentDto);

				} else {
					paymentDto.setProcessStatus("failed");
					if (createdPayment.getFailedTransactions() != null) {
						logger.error("payment fail : " + createdPayment.getFailureReason());
						paymentDto.setRemark(createdPayment.getFailureReason());

					} else {
						logger.error("unexcepted error");
						paymentDto.setRemark("unexcepted error");
					}
				}
				cDate = new Date();
				paymentDto.setLastModifiedDate(cDate);
				singPaymentService.updatePayment(paymentDto);

			} else {
				logger.warn("invaild, process block");
				throw new Exception();
			}

		} catch (Exception e) {

			logger.error("error : cannnot get the token" + e);
		}
		return paymentMsgDto;
	}

}
