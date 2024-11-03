package org.sopt.seminar3.member.api;

import jakarta.validation.Valid;
import org.sopt.seminar3.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> singup(@Valid @RequestBody CreateMemberRequest request){
        final Long memberId = memberService.signupMember(request.getUserName(), request.getPassword(), request.getNickname());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "회원가입이 완료되었습니다.", "memberId", memberId));
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin(@Valid @RequestBody  SigninMemberRequest request) {
        final Long memberId = memberService.signinMember(request.getUserName(), request.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "로그인에 성공하였습니다.", "memberId", memberId));
    }

}
