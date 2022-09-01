package org.zerock.controller.example12;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class MessageMultiple {
	
	@Resource(name="mbk")
	private MessageBean messagebean;
	
		
	// 持失切
	public MessageMultiple() {
		System.out.println("MessageMultiple 持失切");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}

}
