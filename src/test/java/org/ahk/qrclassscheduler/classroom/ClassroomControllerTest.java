package org.ahk.qrclassscheduler.classroom;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClassroomControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("get method: /api/v1/classrooms")
    public void get_classrooms_endpoint() throws Exception {
        mockMvc.perform(get("/api/v1/classrooms")
                        .with(httpBasic("user1", "pass1"))
                        .cookie(new Cookie("JSESSIONID", "123")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$.[0].classRoomQrCode").isNotEmpty())
                .andExpect(jsonPath("$.[1].classRoomQrCode").isNotEmpty())
                .andExpect(jsonPath("$.[2].classRoomQrCode").isNotEmpty())
                .andExpect(jsonPath("$.[3].classRoomQrCode").isNotEmpty())
                .andExpect(jsonPath("$.[4].classRoomQrCode").isNotEmpty())
                .andExpect(jsonPath("$.[5].classRoomQrCode").isNotEmpty())
                .andExpect(jsonPath("$.[6].classRoomQrCode").isNotEmpty())
                .andExpect(jsonPath("$.[7].classRoomQrCode").isNotEmpty())
                .andExpect(jsonPath("$.[8].classRoomQrCode").isNotEmpty())
                .andExpect(jsonPath("$.[9].classRoomQrCode").isNotEmpty())
                .andExpect(content().string(containsString("a")));
    }

}