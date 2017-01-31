package com.sing.payment.util;

import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.sing.payment.model.SingPayment;

public class PaypalObjHandler {

	public Payment convertToPayPalPaymentObj(SingPayment paymentDto) {
		String cardType = "";
		if (paymentDto.getCardType().toLowerCase().equals("americanexpress")) {
			cardType = "amex";
		} else {
			cardType = paymentDto.getCardType().toLowerCase();
		}

		CreditCard creditCard = new CreditCard()
		.setType(cardType)
		.setNumber(paymentDto.getCreditCardNumber())
		.setExpireMonth(Integer.parseInt(paymentDto.getCreditCardExpMon()))
		.setExpireYear(Integer.parseInt(paymentDto.getCreditCardExpYear()))
		.setCvv2(paymentDto.getCreditCardCCV())
		.setFirstName("Joe")
		.setLastName("Shopper");

		//creditCard.create(context);
		//System.out.println(creditCard.toJSON());

		// Payment details
		//Details details = new Details();
		//details.setShipping("1");
		//details.setSubtotal("5");
		//details.setTax("1");

		// Total amount
		Amount amount = new Amount();
		amount.setCurrency(paymentDto.getCurrency().toUpperCase());
		amount.setTotal(paymentDto.getPrice());
		System.out.println("price from DTO :" + paymentDto.getPrice());
		//amount.setDetails(details);

		// Transaction details
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
		.setDescription("This is the payment transaction description.");

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// Set funding instrument
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);

		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");
		System.out.println(payer.getPaymentMethod());

		// Set payment details
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		System.out.println(payment.toJSON());
		return payment;

	}

}
