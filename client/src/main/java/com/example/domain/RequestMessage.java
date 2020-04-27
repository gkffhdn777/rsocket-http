package com.example.domain;

public class RequestMessage {

	private String reqMessage;

	public RequestMessage() {
	}

	public RequestMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}

	public String getReqMessage() {
		return reqMessage;
	}

	@Override
	public String toString() {
		return "RequestMessage{" +
				"reqMessage='" + reqMessage + '\'' +
				'}';
	}
}
