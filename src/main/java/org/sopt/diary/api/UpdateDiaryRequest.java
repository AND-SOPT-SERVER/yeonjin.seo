package org.sopt.diary.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateDiaryRequest {

    @NotBlank(message = "body는 필수 입력입니다.")
    @Size(max = 30, message = "body는 최대 30자까지 입력 가능합니다.")
    private String body;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
