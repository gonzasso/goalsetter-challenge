package com.goalsetter.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder(builderMethodName = "authRequestBuilder")
@JsonInclude(NON_NULL)
public class AuthRequest {

    private String email;

    private String password;

    public static AuthRequestBuilder builder() {
        return authRequestBuilder();
    }
}
