package org.zerock.controller.example12;

import org.springframework.stereotype.Component;

@Component("mbk")
public class MessageBeanKo implements MessageBean {
	@Override
	public void sayHello(String name) {
		System.out.println("안녕하세요!" + name);
	}
	
	public MessageBeanKo() {
		System.out.println("MessageBeanKo 생성자");
	}
}
