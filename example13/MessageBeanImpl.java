package org.zerock.controller.example13;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

public class MessageBeanImpl implements MessageBean {
	private String name;
	private String greeting;
	private Outputter outputter;
	
	// 생성자
	public MessageBeanImpl(String name) {
		this.name = name;
		System.out.println("MessageBeanImpl의 생성자");
	}
	
	// 오버라이딩
	@Override
	public void sayHello() {
		String message = greeting + name + "!";
		System.out.println("MessageBeanImpl의 sayHello() - 오버라이딩 : " + message);
		try {
			outputter.output(message);
		} catch(IOException ie) {
			ie.printStackTrace();
		}
	}
	
	// setter
	public void setGreeting(String greeting) {
		this.greeting = greeting;
		System.out.println("MessageBeanImpl의 setGreeting()");
	}
	
	@Autowired
	public void setOutputter(Outputter outputter) {
		this.outputter = outputter;
		System.out.println("MessageBeanImpl의 setOutputter()");
	}
	
	
}
