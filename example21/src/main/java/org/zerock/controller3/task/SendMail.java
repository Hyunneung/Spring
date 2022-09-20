package org.zerock.controller3.task;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.zerock.controller3.domain.MailVO;

@Component
public class SendMail {
	private static final Logger logger = LoggerFactory.getLogger(SendMail.class);
	
	@Value("#{savefolder['sendfile']}") // root-context.xml에서 빈으로 만든 프로퍼티 값 주입
	private String sendfile;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	public void sendMail(MailVO mail) {
		MimeMessagePreparator mp = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom(mail.getFrom());
				helper.setTo(mail.getTo());
				helper.setSubject(mail.getSubject());
				
				String content = "<img src='cid:Home'>" + mail.getContent();
				helper.setText(content, true);
				
				FileSystemResource file = new FileSystemResource(new File(sendfile));
				helper.addInline("Home", file);
				
				helper.addAttachment("복숭아.jpg", file);
			} // 추상메서드 prepare() end
		}; // MimeMessagePreparator end
		
		mailSender.send(mp);
		logger.info("메일 전송 성공!");
	} // sendMail() end
	
}
