package org.zerock.controller3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.controller3.domain.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MyBatisTestDAO_Member {
	private static final Logger logger = LoggerFactory.getLogger(MyBatisTestDAO_Member.class);
	
	@Autowired // �ʵ忡 @Autowired �ֳ����̼� ���̸� �����ڷ� ������ �ʿ� ����
	private SqlSessionTemplate sqlSession;
	
	@Test // �׽�Ʈ�� �޼ҵ忡 @Test �ֳ����̼�
	public void searchCount() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("search_field", "gender");
		map.put("search_word", "%��%");
		int count = sqlSession.selectOne("Member.listCount", map);
		logger.info("==========" + count + "==========");
	}
	
	@Test
	public void selectList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startrow", 1);
		map.put("endrow", 3);
		map.put("search_field", "gender");
		map.put("search_word", "%��%");
		List<Member> list = sqlSession.selectList("Member.list", map);
		
		logger.info("========== selectList() ==========");
		for(Member m : list) {
			logger.info("���̵� : " + m.getId());
			logger.info("��й�ȣ : " + m.getPassword());
			logger.info("���� : " + m.getAge());
			logger.info("�̸� : " + m.getName());
			logger.info("�̸��� : " + m.getEmail());
			logger.info("���� : " + m.getGender());
			logger.info("==============================");
		}
	}
	
}
