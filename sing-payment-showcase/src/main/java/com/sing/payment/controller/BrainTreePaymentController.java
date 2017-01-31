package com.sing.payment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

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

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.sing.payment.dto.PaymentMsgDto;
import com.sing.payment.model.SingPayment;
import com.sing.payment.service.SingPaymentService;
import com.sing.payment.util.CreditCardUtil;

@CrossOrigin
@Controller
@Api(value = "/BrainTree", description = "BrainTree payment API")
@RequestMapping(value = "BrainTree")
public class BrainTreePaymentController {
	private static final Logger logger = LoggerFactory.getLogger(BrainTreePaymentController.class);

	@Autowired
	private SingPaymentService singPaymentService;

	@Autowired
	private RedisTemplate redisTemplate;
	/*
		@Value("${braintree.merchant.id}")
		private static String braintreeMerchantId;

		@Value("${braintree.publicKey}")
		private static String braintreePublicKey;

		@Value("${braintree.privateKey}")
		private static String braintreePrivateKey;
	*/
	private static BraintreeGateway gateway = new BraintreeGateway(
	Environment.SANDBOX,
	"2w4zqqjrsm",
	"7253kfrd5h",
	"2abf5473e069"
	);

	@ApiOperation(notes = " RestTemplate test .  ", httpMethod = "POST", value = "  RestTemplate test ")
	@RequestMapping(value = "/baintreePayment", method = RequestMethod.POST)
	@ResponseBody
	public PaymentMsgDto brainpPayment(@RequestBody SingPayment paymentDto) throws Exception {

		//System.out.println(braintreeMerchantId);
		PaymentMsgDto paymentMsgDto = new PaymentMsgDto();
		logger.info("start brainTreePayment");
		Date cDate = new Date();

		paymentDto.setCreateDate(cDate);
		paymentDto.setLastModifiedDate(cDate);

		CreditCardUtil creditCardUtil = new CreditCardUtil();
		paymentDto = creditCardUtil.checkCreditCard(paymentDto);

		if (paymentDto.getPaymentMethod().equals("braintree")) {
			singPaymentService.savePayment(paymentDto);

			TransactionRequest request = new TransactionRequest()
			.amount(new BigDecimal(paymentDto.getPrice()))
			.paymentMethodNonce(paymentDto.getNonce())
			.options()
			.submitForSettlement(true)
			.done();

			Result<Transaction> brainTreeResult = gateway.transaction().sale(request);

			cDate = new Date();
			paymentDto.setLastModifiedDate(cDate);
			if (brainTreeResult.isSuccess()) {
				logger.info("Payment OK");
				paymentDto.setRemark(brainTreeResult.getMessage());
				paymentDto.setProcessStatus("compelete");
				//singPaymentService.updatePayment(paymentDto);
			} else {
				logger.error("fail");
				paymentDto.setProcessStatus("fail");
				paymentDto.setRemark(brainTreeResult.getMessage());
			}
			singPaymentService.updatePayment(paymentDto);
			//----- set the redis ------
			ValueOperations<String, SingPayment> valueopsPayment = redisTemplate.opsForValue();
			String key = paymentDto.getCustomerName() + String.valueOf(paymentDto.getSingPaymentId());
			valueopsPayment.set(key, paymentDto);
			paymentMsgDto.setPaymentId(paymentDto.getSingPaymentId());
		}
		return paymentMsgDto;
	}

	@ApiOperation(notes = " get braintree token  ", httpMethod = "GET", value = "  RestTemplate test ")
	@RequestMapping(value = "/baintreeToken", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, String> getBaintreeToken() {
		logger.info("start get braintree token");
		String token = gateway.clientToken().generate().toString();
		HashMap<String, String> tokenObj = new HashMap<String, String>();
		tokenObj.put("token", token);
		return tokenObj;
	}

}
