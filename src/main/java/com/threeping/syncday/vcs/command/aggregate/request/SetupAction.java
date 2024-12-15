package com.threeping.syncday.vcs.command.aggregate.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SetupAction {
    @JsonProperty("install")
    INSTALL,
    @JsonProperty("uninstall")
    UNINSTALL,

    @JsonProperty("update")
    UPDATE,
    @JsonProperty("suspend")
    SUSPEND
}
