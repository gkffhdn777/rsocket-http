package com.example.domain;

public class ResponseMessage {

	private String resMessage;

	public ResponseMessage() {
	}

	public ResponseMessage(String resMessage) {
		this.resMessage = resMessage;
	}

	public String getResMessage() {
		return resMessage;
	}

	@Override
	public String toString() {
		return "ResponseMessage{" +
				"resMessage='" + resMessage + '\'' +
				'}';
	}
}
