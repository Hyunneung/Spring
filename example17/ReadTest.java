package org.zerock.controller.example17;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReadTest {
	@Value("${name}")
	private String name;
	@Value("${age}")
	private String age;
	
	public void print() {
		System.out.println(name);
		System.out.println(age);
	}
}
