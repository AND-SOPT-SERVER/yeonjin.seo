package org.sopt.seminar3.member.service;

import org.sopt.seminar3.member.repository.MemberEntity;
import org.sopt.seminar3.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long signupMember(final String userName, final String password, final String nickname) {
        if (memberRepository.existsByUserName(userName)) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }

        MemberEntity memberEntity = new MemberEntity(userName, password, nickname);
        MemberEntity savedMember = memberRepository.save(memberEntity);
        return savedMember.getId();
    }

    public Long signinMember(final String userName, final String password) {
        MemberEntity memberEntity = memberRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("로그인에 실패하였습니다."));

        if (!memberEntity.getPassword().equals(password)) {
            throw new IllegalArgumentException("로그인에 실패하였습니다.");
        }

        return memberEntity.getId();
    }
}
