package org.zerock.controller.example18;

public class MessageBeanEn implements MessageBean {
	@Override
	public void sayHello(String name) {
		System.out.println("Hello!" + name);
	}
	
	public MessageBeanEn() {
		System.out.println("MessageBeanEn 생성자");
	}
	
	public void destroyMethod() {
		System.out.println("MessageBeanEn의 destroyMethod() 메소드");
	}
}
