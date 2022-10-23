package com.goalsetter.api.dtos;

import lombok.Data;

@Data
public class TokenPayload {

    private String rid;

    private String usertype;

    private String[] familyMembersIds;

    private Long exp;

    private String iss;

    private String aud;
}
