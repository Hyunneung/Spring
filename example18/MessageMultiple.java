package org.zerock.controller.example18;

public class MessageMultiple {
	
	private MessageBean messagebean;
	public void setBean(MessageBean messagebean) {
		this.messagebean = messagebean;
	}

	// ������
	public MessageMultiple() {
		System.out.println("MessageMultiple ������");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}
	
	
	public void initMethod() {
		System.out.println("MessageMultiple�� initMethod() �޼ҵ�");
	}
}
