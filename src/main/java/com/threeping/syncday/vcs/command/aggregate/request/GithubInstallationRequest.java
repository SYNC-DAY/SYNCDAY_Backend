package com.threeping.syncday.vcs.command.aggregate.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class GithubInstallationRequest {
    @JsonProperty("installation_id")
     Long installationId;


    @JsonProperty("user_id")
     Long userId;

    @JsonProperty("setup_action")
     SetupAction setupAction;
}
