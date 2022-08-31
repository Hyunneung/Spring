package org.zerock.controller.example11;

import javax.annotation.Resource;

public class MessageMultiple {
	
	@Resource(name="mk")
	private MessageBean messagebean;
	
		
	// 持失切
	public MessageMultiple() {
		System.out.println("MessageMultiple 持失切");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}

}
