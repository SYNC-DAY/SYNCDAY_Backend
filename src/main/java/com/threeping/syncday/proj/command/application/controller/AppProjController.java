package com.threeping.syncday.proj.command.application.controller;
import com.threeping.syncday.proj.command.application.service.AppProjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/projs")
public class AppProjController {
    private final AppProjService appProjService;

    @Autowired
    public AppProjController(AppProjService appProjService){
        this.appProjService = appProjService;
    }



}