package org.zerock.controller.example6;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutputter implements Outputter  {
	private String filePath;
	
	// ������
	public FileOutputter() {
		System.out.println("FileOutputter ������");
	}

	// setter
	public void setFilePath(String filePath) {
		this.filePath = filePath;
		System.out.println("FileOutputter�� setFilePath()");
	}

	// �������̵�
	@Override
	public void output(String message) throws IOException {
		System.out.println("FileOutputter�� output() - �������̵�");
		FileWriter out = new FileWriter(filePath);
		out.write(message);
		out.close();
	}
}
