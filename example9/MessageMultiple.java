package org.zerock.controller.example9;

import org.springframework.beans.factory.annotation.Autowired;

public class MessageMultiple {
	@Autowired
	private MessageBean messagebean;
	
		
	// ������
	public MessageMultiple() {
		System.out.println("MessageMultiple ������");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}

}
