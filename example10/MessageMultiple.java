package org.zerock.controller.example10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MessageMultiple {
	@Autowired
	@Qualifier("mk")
	private MessageBean messagebean;
	
		
	// ������
	public MessageMultiple() {
		System.out.println("MessageMultiple ������");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}

}
