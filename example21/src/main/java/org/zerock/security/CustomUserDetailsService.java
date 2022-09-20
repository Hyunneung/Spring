package org.zerock.security;

import java.util.ArrayList;
import java.util.Collection;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.controller3.domain.Member;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("username(���̵�)�� �α��� �� �Է��� �� => " + username);
		Member member = sqlSession.selectOne("Member.isId", username);
		
		// �ش� ���̵�� ������ ȸ���� ���� ���
		if(member == null) {
			logger.info("username(���̵�) [" + username + " ]�� ã�� �� �����ϴ�.");
			throw new UsernameNotFoundException("username " + username + " not found");
		}
		
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		roles.add(new SimpleGrantedAuthority(member.getAuth()));
		UserDetails user = new User(username, member.getPassword(), roles);
		return user;
		
	}
	

}
