package org.zerock.controller.example11;

import javax.annotation.Resource;

public class MessageMultiple {
	
	@Resource(name="mk")
	private MessageBean messagebean;
	
		
	// ������
	public MessageMultiple() {
		System.out.println("MessageMultiple ������");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}

}
