package org.sopt.seminar3.member.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateMemberRequest {

    @NotBlank(message="user_name은 필수 입력입니다.")
    @Size(max=15, message = "user_name은 최대 15자까지 입력 가능합니다.")
    private String userName;

    @NotBlank(message = "password는 필수 입력입니다.")
    @Size(max=20, message = "password는 최대 20자까지 입력 가능합니다.")
    private String password;

    @NotBlank(message = "nickname은 필수 입력입니다.")
    @Size(max=8, message = "nickname은 최대 8자까지 입력 가능합니다.")
    private String nickname;

    public CreateMemberRequest() {
    }

    public CreateMemberRequest(String userName, String password, String nickname){
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
