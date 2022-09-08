package org.zerock.controller.example19;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("mm")
public class MessageMultiple {
	
	@Resource(name="mbe")
	private MessageBean messagebean;

	// ������
	public MessageMultiple() {
		System.out.println("MessageMultiple ������");
	}
	
	public void print() {
		messagebean.sayHello("Spring");
	}
	
	@PostConstruct
	public void initMethod() {
		System.out.println("MessageMultiple�� initMethod() �޼ҵ�");
	}
}
