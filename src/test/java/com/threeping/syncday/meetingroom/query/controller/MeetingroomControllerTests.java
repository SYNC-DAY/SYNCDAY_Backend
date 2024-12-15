package com.threeping.syncday.meetingroom.query.controller;

import com.threeping.syncday.meetingroom.query.service.MeetingroomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MeetingroomControllerTests {

//    @Autowired
//    MeetingroomController meetingroomController;

    @Autowired
    MeetingroomService meetingroomService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회의실 전체 조회 테스트")
    @Test
    void testGetAllMeetingrooms() throws Exception {
        mockMvc.perform(get("/api/meetingroom"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}