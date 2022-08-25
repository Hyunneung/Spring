package org.zerock.controller.example9;

import org.springframework.beans.factory.annotation.Autowired;

public class MessageMultiple {
	@Autowired
	private MessageBean messagebean;
	
		
	// 持失切
	public MessageMultiple() {
		System.out.println("MessageMultiple 持失切");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}

}
