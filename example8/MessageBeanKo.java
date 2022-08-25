package org.zerock.controller.example8;

public class MessageBeanKo implements MessageBean {
	@Override
	public void sayHello(String name) {
		System.out.println("안녕하세요!" + name);
	}
	
	public MessageBeanKo() {
		System.out.println("MessageBeanKo 생성자");
	}
}
