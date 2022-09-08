package org.zerock.controller.example19;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component("mbe")
public class MessageBeanEn implements MessageBean {
	@Override
	public void sayHello(String name) {
		System.out.println("Hello!" + name);
	}
	
	public MessageBeanEn() {
		System.out.println("MessageBeanEn 생성자");
	}
	
	@PreDestroy
	public void destroyMethod() {
		System.out.println("MessageBeanEn의 destroyMethod() 메소드");
	}
}
