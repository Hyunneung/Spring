package org.zerock.controller.example18;

public class MessageMultiple {
	
	private MessageBean messagebean;
	public void setBean(MessageBean messagebean) {
		this.messagebean = messagebean;
	}

	// »ý¼ºÀÚ
	public MessageMultiple() {
		System.out.println("MessageMultiple »ý¼ºÀÚ");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}
	
	
	public void initMethod() {
		System.out.println("MessageMultipleÀÇ initMethod() ¸Þ¼Òµå");
	}
}
 
