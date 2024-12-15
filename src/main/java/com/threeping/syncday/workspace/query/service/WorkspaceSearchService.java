package com.threeping.syncday.workspace.query.service;

import com.threeping.syncday.workspace.query.dto.WorkspaceSearchResponse;

import java.util.List;

public interface WorkspaceSearchService {
    List<WorkspaceSearchResponse> searchWorkspace(String keyword);
}
