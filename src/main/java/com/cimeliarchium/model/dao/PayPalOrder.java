package com.cimeliarchium.model.dao;

import java.net.URI;

public class PayPalOrder {

	private String orderId;
	private String username;
	private String amount;
	private URI approvalLink;

	public PayPalOrder withOrderId(String orderId) {
		this.orderId = orderId;
		return this;
	}

	public String getOrderId() {
		return orderId;
	}

	public PayPalOrder withUsername(String username) {
		this.username = username;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmount() {
		return amount;
	}

	public PayPalOrder withApprovalLink(URI approvalLink) {
		this.approvalLink = approvalLink;
		return this;
	}

	public URI getApprovalLink() {
		return approvalLink;
	}
}
