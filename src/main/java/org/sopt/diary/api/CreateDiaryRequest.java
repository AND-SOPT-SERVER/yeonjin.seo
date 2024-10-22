package org.sopt.diary.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateDiaryRequest {

    @NotBlank(message = "title은 필수 입력입니다.")
    private String title;

    @NotBlank(message = "body는 필수 입력입니다.")
    @Size(max = 30, message = "body는 최대 30자까지 입력 가능합니다.")
    private String body;

    public CreateDiaryRequest() {
    }

    public CreateDiaryRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}