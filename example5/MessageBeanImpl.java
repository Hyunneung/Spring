package org.zerock.controller.example5;

public class MessageBeanImpl implements MessageBean {
	private String name;
	private String greeting;
	
	// 생성자
	public MessageBeanImpl(String name) {
		this.name = name;
	}
	
	// 오버라이딩
	@Override
	public void sayHello() {
		System.out.println(greeting + name + "!");
	}
	
	// setter
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
}
