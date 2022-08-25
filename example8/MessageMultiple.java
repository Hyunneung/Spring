package org.zerock.controller.example8;

public class MessageMultiple {
	private MessageBean messagebean;
	
	// setter
	public void setMessagebean(MessageBean messagebean) {
		this.messagebean = messagebean;
	}
		
	// 持失切
	public MessageMultiple() {
		System.out.println("MessageMultiple 持失切");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}

}
