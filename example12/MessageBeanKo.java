package org.zerock.controller.example12;

import org.springframework.stereotype.Component;

@Component("mbk")
public class MessageBeanKo implements MessageBean {
	@Override
	public void sayHello(String name) {
		System.out.println("�ȳ��ϼ���!" + name);
	}
	
	public MessageBeanKo() {
		System.out.println("MessageBeanKo ������");
	}
}
