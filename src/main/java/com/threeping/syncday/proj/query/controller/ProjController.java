package com.threeping.syncday.proj.query.controller;

import com.threeping.syncday.proj.query.aggregate.dto.ProjectSearchResponse;
import com.threeping.syncday.proj.query.service.ProjService;
import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.proj.query.service.ProjectSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projs")
@Slf4j
public class ProjController {

    private final ProjService projService;
    private final ProjectSearchService searchService;

    @Autowired
    public ProjController(ProjService projService, ProjectSearchService searchService){
        this.projService = projService;
        this.searchService = searchService;
    }

    @GetMapping("/")
    public ResponseDTO<?> findAllProjs(){
        return ResponseDTO.ok(projService.getAllProjs());
    }

    @GetMapping("/{projId}")
    public ResponseDTO<?> findProjById(@PathVariable("projId") Long projId){
        return ResponseDTO.ok(projService.getProjById(projId));
    }


    @Operation(summary = "프로젝트 검색",
            description = "프로젝트 이름, VCSTAG를 키워드로 프로젝트를 검색합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로젝트 검색 성공",
                            content = @Content(schema = @Schema(implementation = ProjectSearchResponse.class))
                    )
            }
    )
    @GetMapping("/search")
    public ResponseDTO<?> searchProjs(@RequestParam String keyword){
        return ResponseDTO.ok(searchService.searchProject(keyword));
    }
}
