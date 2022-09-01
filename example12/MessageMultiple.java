package org.zerock.controller.example12;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class MessageMultiple {
	
	@Resource(name="mbk")
	private MessageBean messagebean;
	
		
	// ������
	public MessageMultiple() {
		System.out.println("MessageMultiple ������");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}

}
