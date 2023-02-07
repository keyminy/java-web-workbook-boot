package org.zerock.b01.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Member;
import org.zerock.b01.domain.MemberRole;
import org.zerock.b01.dto.MemberJoinDTO;
import org.zerock.b01.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	
	@Override
	public void join(MemberJoinDTO memberJoinDTO) throws MidExistException {
		String mid = memberJoinDTO.getMid();
		boolean exist = memberRepository.existsById(mid);
		if(exist) {
			throw new MidExistException();
		}
		//기존에 가입된 mid가 아닌 경우, 회원가입 진행
		Member member = modelMapper.map(memberJoinDTO, Member.class);
		//비밀번호 암호화
		member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
		member.addRole(MemberRole.USER);
		log.info("=========================");
		log.info(member);
		log.info(member.getRoleSet());
		
		memberRepository.save(member);
	}
}
