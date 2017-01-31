package com.sing.payment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sing.payment.dto.ResetDataDto;
import com.sing.payment.model.SingPayment;
import com.sing.payment.service.SingPaymentService;

@CrossOrigin
@Controller
@Api(value = "/checkPayment", description = "template API")
@RequestMapping(value = "checkPayment")
public class CheckPaymentController {
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private SingPaymentService singPaymentService;

	private static final Logger logger = LoggerFactory.getLogger(CheckPaymentController.class);

	@ApiOperation(notes = "check data .  ", httpMethod = "GET", value = "  check ")
	@RequestMapping(value = "/check/{key}", method = RequestMethod.GET)
	@ResponseBody
	//@JsonView(GenericJsonView.Summary.class)
	public SingPayment getPaymentCacheRecord(@PathVariable String key) {
		logger.info("start getPaymentCacheRecord ");
		ValueOperations<String, SingPayment> valueopsPayment = redisTemplate.opsForValue();
		SingPayment paymentDto = valueopsPayment.get(key);

		return paymentDto;
	}

	@ApiOperation(notes = "Rebuild the data .  ", httpMethod = "GET", value = "  check ")
	@RequestMapping(value = "/resetData", method = RequestMethod.GET)
	@ResponseBody
	//@JsonView(GenericJsonView.Summary.class)
	public ResetDataDto rebuildPaymentCacheData() throws Exception {
		logger.info("start rebuildPaymentCacheData ");
		List<SingPayment> results = singPaymentService.findAll();
		ValueOperations<String, SingPayment> valueopsPayment = redisTemplate.opsForValue();
		int count = 0;
		for (SingPayment paymentDto : results) {
			try {
				String key = paymentDto.getCustomerName() + String.valueOf(paymentDto.getSingPaymentId());
				valueopsPayment.set(key, paymentDto);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("the number of data in redis: " + count);
		ResetDataDto resetDataDto = new ResetDataDto();
		resetDataDto.setStatus("ok");
		return resetDataDto;
	}

}
