package org.zerock.controller.example16;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReadTest {
	@Value("���ڿ� ����")
	private String readtest;
	
	public void print() {
		System.out.println(readtest);
	}
}
