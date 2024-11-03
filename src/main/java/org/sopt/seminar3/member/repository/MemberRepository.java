package org.sopt.seminar3.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


    boolean existsByUserName(String userName);

    Optional<MemberEntity> findByUserName(String userName);
}
