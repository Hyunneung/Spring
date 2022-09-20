package org.zerock.controller3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DataSourceTest {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceTest.class);
	
	@Autowired
	private DataSource dataSource; // root-context.xml에 설정된 DataSource를 자동으로 주입
	
	@Test
	public void testConnection() {
		try(Connection conn = dataSource.getConnection()){
			logger.info("확인용 conn : " + conn);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQuery() {
		try(Connection conn = dataSource.getConnection()){
			PreparedStatement pstmt = conn.prepareStatement("select to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss') from dual");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			logger.info("현재시각 : " + rs.getString(1));
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
