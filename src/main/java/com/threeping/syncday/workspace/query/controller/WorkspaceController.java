package com.threeping.syncday.workspace.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.workspace.query.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/projs")
    public ResponseDTO<?> getWorkspacesByProjs(@RequestParam("projIds") List<Long> projIds){

        return ResponseDTO.ok(workspaceService.getWorkspacesByProjIds(projIds));
    }
}
