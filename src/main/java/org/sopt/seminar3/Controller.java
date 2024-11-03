package org.sopt.seminar3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private final SoptMemberRepository soptMemberRepository;

    public Controller(SoptMemberRepository soptMemberRepository){
        this.soptMemberRepository = soptMemberRepository;
    }
    @PostMapping("/member")
    void postMember() {
        soptMemberRepository.save(
                new SoptMemberEntity("yon", 12)
        );
    }

    @GetMapping("/members")
    ResponseEntity<String> getMembers() {
        List<SoptMemberEntity> all = soptMemberRepository.findAll();

        return ResponseEntity.ok(all.stream().findFirst().get().toString());
    }

}
