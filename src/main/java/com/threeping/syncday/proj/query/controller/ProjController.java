package com.threeping.syncday.proj.query.controller;

import com.threeping.syncday.proj.query.service.ProjService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projs")
public class ProjController {

    private final ProjService projService;



}
