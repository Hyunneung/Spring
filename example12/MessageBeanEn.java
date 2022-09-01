package org.zerock.controller.example12;

import org.springframework.stereotype.Component;

@Component("mbe")
public class MessageBeanEn implements MessageBean {
	@Override
	public void sayHello(String name) {
		System.out.println("Hello!" + name);
	}
	
	public MessageBeanEn() {
		System.out.println("MessageBeanEn »ý¼ºÀÚ");
	}
}
