package org.zerock.b01.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberSecurityDTO extends User{
	
	private String mid;
	private String mpw;
	private String email;
	private boolean del;
	private boolean social;

	public MemberSecurityDTO(String username, String password,String email
			,boolean del, boolean social,
			Collection<? extends GrantedAuthority> authorities) {
		//구현 해줘야하는 상위 클래스 생성자 파라미터 3개
		super(username, password, authorities);
		
		this.mid = username;
		this.mpw = password;
		this.email = email;
		this.del = del;
		this.social = social;
	}
	
}
