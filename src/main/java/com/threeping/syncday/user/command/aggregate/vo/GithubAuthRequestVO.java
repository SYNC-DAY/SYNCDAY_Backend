package com.threeping.syncday.user.command.aggregate.vo;

import lombok.Value;

@Value
public class GithubAuthRequestVO {

    private String code;

    private String state;

}
