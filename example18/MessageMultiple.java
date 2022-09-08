package org.zerock.controller.example18;

public class MessageMultiple {
	
	private MessageBean messagebean;
	public void setBean(MessageBean messagebean) {
		this.messagebean = messagebean;
	}

	// 持失切
	public MessageMultiple() {
		System.out.println("MessageMultiple 持失切");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}
	
	
	public void initMethod() {
		System.out.println("MessageMultiple税 initMethod() 五社球");
	}
}
