package org.zerock.b01.security.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User implements OAuth2User{
	
	private String mid;
	private String mpw;
	private String email;
	private boolean del;
	private boolean social;
	
	private Map<String,Object> props;//소셜 로그인 정보

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

	@Override
	public Map<String, Object> getAttributes() {
		return this.getProps();
	}

	@Override
	public String getName() {
		return this.mid;
	}
	
}
