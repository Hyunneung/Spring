package org.zerock.controller.example6;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutputter implements Outputter  {
	private String filePath;
	
	// 생성자
	public FileOutputter() {
		System.out.println("FileOutputter 생성자");
	}

	// setter
	public void setFilePath(String filePath) {
		this.filePath = filePath;
		System.out.println("FileOutputter의 setFilePath()");
	}

	// 오버라이딩
	@Override
	public void output(String message) throws IOException {
		System.out.println("FileOutputter의 output() - 오버라이딩");
		FileWriter out = new FileWriter(filePath);
		out.write(message);
		out.close();
	}
}
