package org.zerock.controller.example8;

public class MessageMultiple {
	private MessageBean messagebean;
	
	// setter
	public void setMessagebean(MessageBean messagebean) {
		this.messagebean = messagebean;
	}
		
	// ������
	public MessageMultiple() {
		System.out.println("MessageMultiple ������");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}

}
