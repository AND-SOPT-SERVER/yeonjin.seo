package org.sopt.seminar3.member.repository;

import jakarta.persistence.*;

@Entity
@Table(name= "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Column(name="password", nullable = false, length =30)
    private String password;

    @Column(name="nickname", nullable = false, length=10)
    private String nickname;

    public MemberEntity() {}

    public MemberEntity(String userName, String password, String nickname) {
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
