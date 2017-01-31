package com.sing.payment.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.fatehi.creditcardnumber.AccountNumber;
import us.fatehi.creditcardnumber.BankCard;
import us.fatehi.creditcardnumber.ExpirationDate;
import us.fatehi.creditcardnumber.Name;
import us.fatehi.creditcardnumber.ServiceCode;

import com.sing.payment.model.SingPayment;

public class CreditCardUtil {
	private static final Logger logger = LoggerFactory.getLogger(CreditCardUtil.class);

	public SingPayment checkCreditCard(SingPayment paymentDto) {
		AccountNumber pan = new AccountNumber(paymentDto.getCreditCardNumber());

		if (pan.isPrimaryAccountNumberValid() && pan.passesLuhnCheck() && pan.isLengthValid()) {

			//ExpirationDate expiration = new ExpirationDate(Integer.parseInt(paymentDto.getCreditCardExpYear()), Integer.parseInt(paymentDto.getCreditCardExpMon()));
			ExpirationDate expiration = new ExpirationDate(2018, 9);
			paymentDto.setCardType(pan.getCardBrand().name());

			if (expiration.isExpired() == false) {
				logger.info("credit card : vaild");
				Name name = new Name(paymentDto.getCreditCardHolderName());
				ServiceCode serviceCode = new ServiceCode(paymentDto.getCreditCardCCV());
				BankCard card = new BankCard(pan, expiration, name, serviceCode);
				return this.determinePaymentMethod(paymentDto);
			} else {
				logger.error("invaild credit card data");
				paymentDto.setPaymentMethod("invaild");
				return paymentDto;
			}
		} else {
			logger.error("invaild credit card data");
			paymentDto.setPaymentMethod("invaild");
			return paymentDto;
		}
	}

	private SingPayment determinePaymentMethod(SingPayment paymentDto) {
		String cardType = "";
		if (cardType.equals("AMEX")) {
			if (paymentDto.getCurrency().equals("USD")) {
				// paypal
				paymentDto.setPaymentMethod("paypal");
			} else {
				// show error message
			}
		} else {
			if (paymentDto.getCurrency().equals("USD") || paymentDto.getCurrency().equals("EUR") || paymentDto.getCurrency().equals("AUD")) {
				// paypal
				paymentDto.setPaymentMethod("paypal");
			} else {
				// braintree
				paymentDto.setPaymentMethod("braintree");
			}
		}
		return paymentDto;
	}

	/*
			Service code values common in financial cards:
			
			First digit
			
			1: International interchange OK
			2: International interchange, use IC (chip) where feasible
			5: National interchange only except under bilateral agreement
			6: National interchange only except under bilateral agreement, use IC (chip) where feasible
			7: No interchange except under bilateral agreement (closed loop)
			9: Test
			Second digit
			
			0: Normal
			2: Contact issuer via online means
			4: Contact issuer via online means except under bilateral agreement
			Third digit
			
			0: No restrictions, PIN required
			1: No restrictions
			2: Goods and services only (no cash)
			3: ATM only, PIN required
			4: Cash only
			5: Goods and services only (no cash), PIN required
			6: No restrictions, use PIN where feasible
			7: Goods and services only (no cash), use PIN where feasible
	 */

}
