package com.threeping.syncday.projmember.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.projmember.query.service.ProjMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/proj-members")
public class ProjMemberController {
    private final ProjMemberService projMemberService;

    @GetMapping("/users/{userId}")
    public ResponseDTO<?> findProjMembersByUserId(@PathVariable Long userId) {
        return ResponseDTO.ok(projMemberService.getProjMembersByUserId(userId));
    }

}
