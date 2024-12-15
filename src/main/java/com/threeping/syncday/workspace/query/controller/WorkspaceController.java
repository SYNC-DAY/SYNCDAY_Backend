package com.threeping.syncday.workspace.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.workspace.query.dto.WorkspaceSearchResponse;
import com.threeping.syncday.workspace.query.service.WorkspaceSearchService;
import com.threeping.syncday.workspace.query.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workSpaceService;
    private final WorkspaceSearchService searchService;

    @Autowired
    public WorkspaceController(WorkspaceService workSpaceService, WorkspaceSearchService searchService) {
        this.workSpaceService = workSpaceService;
        this.searchService = searchService;
    }

    @GetMapping("/")
    public ResponseDTO<?> findAllWorkspaces(){
        return ResponseDTO.ok(workSpaceService.getAllWorkspaces());
    }

    @GetMapping("/projs/{projId}")
    public ResponseDTO<?> findWorkspacesByProjId(@PathVariable("projId") Long projId){
        return ResponseDTO.ok(workSpaceService.getWorkspacesByProjId(projId));
    }

    @GetMapping("/{workspaceId}")
    public ResponseDTO<?> findWorkspaceById(@PathVariable("workspaceId") Long workspaceId){
        return ResponseDTO.ok(workSpaceService.getWorkspaceInfo(workspaceId));
    }

    @Operation(summary = "워크스페이스 검색",
            description = "워크스페이스 이름, VCSTAG를 키워드로 워크스페이스를 검색합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "워크스페이스 검색 성공",
                            content = @Content(schema = @Schema(implementation = WorkspaceSearchResponse.class))
                    )
            }
    )
    @GetMapping("/search")
    public ResponseDTO<?> searchWorkspace(@RequestParam String keyword){
        return ResponseDTO.ok(searchService.searchWorkspace(keyword));
    }
}
