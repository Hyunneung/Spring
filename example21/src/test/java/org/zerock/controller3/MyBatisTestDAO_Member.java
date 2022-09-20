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
	
	@Autowired // 필드에 @Autowired 애노테이션 붙이면 생성자로 주입할 필요 없다
	private SqlSessionTemplate sqlSession;
	
	@Test // 테스트할 메소드에 @Test 애노테이션
	public void searchCount() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("search_field", "gender");
		map.put("search_word", "%여%");
		int count = sqlSession.selectOne("Member.listCount", map);
		logger.info("==========" + count + "==========");
	}
	
	@Test
	public void selectList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startrow", 1);
		map.put("endrow", 3);
		map.put("search_field", "gender");
		map.put("search_word", "%여%");
		List<Member> list = sqlSession.selectList("Member.list", map);
		
		logger.info("========== selectList() ==========");
		for(Member m : list) {
			logger.info("아이디 : " + m.getId());
			logger.info("비밀번호 : " + m.getPassword());
			logger.info("나이 : " + m.getAge());
			logger.info("이름 : " + m.getName());
			logger.info("이메일 : " + m.getEmail());
			logger.info("성별 : " + m.getGender());
			logger.info("==============================");
		}
	}
	
}
