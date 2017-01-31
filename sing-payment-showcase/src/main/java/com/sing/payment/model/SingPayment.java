package com.sing.payment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "sing_payment")
@Audited
public class SingPayment extends AbstractTimestampEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sing_payment_id", unique = true, nullable = false, columnDefinition = "bigint")
	private Long singPaymentId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_phone")
	private String customerPhone;

	@Column(name = "currency")
	private String currency;

	@Column(name = "price")
	private String price;

	@Column(name = "credit_card_holder_name")
	private String creditCardHolderName;

	//@Transient
	@Column(name = "credit_card_number")
	private String creditCardNumber;

	@Column(name = "credit_card_exp_mon")
	private String creditCardExpMon;

	@Column(name = "credit_card_exp_year")
	private String creditCardExpYear;

	@Column(name = "credit_card_ccv")
	private String creditCardCCV;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "remark")
	private String remark;

	@Column(name = "process_status")
	private String processStatus;

	@Column(name = "refer_code")
	private String referCode;

	@Column(name = "card_type")
	private String cardType;

	@Transient
	private String nonce;

	public Long getSingPaymentId() {
		return singPaymentId;
	}

	public void setSingPaymentId(Long singPaymentId) {
		this.singPaymentId = singPaymentId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}

	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardCCV() {
		return creditCardCCV;
	}

	public void setCreditCardCCV(String creditCardCCV) {
		this.creditCardCCV = creditCardCCV;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getReferCode() {
		return referCode;
	}

	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}

	public String getCreditCardExpMon() {
		return creditCardExpMon;
	}

	public void setCreditCardExpMon(String creditCardExpMon) {
		this.creditCardExpMon = creditCardExpMon;
	}

	public String getCreditCardExpYear() {
		return creditCardExpYear;
	}

	public void setCreditCardExpYear(String creditCardExpYear) {
		this.creditCardExpYear = creditCardExpYear;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

}
