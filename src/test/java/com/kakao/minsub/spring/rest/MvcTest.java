package com.kakao.minsub.spring.rest;

import com.kakao.minsub.spring.config.db.LocalMysqlConfig;
import com.kakao.minsub.spring.controller.ProfileController;
import com.kakao.minsub.spring.model.Profile;
import com.kakao.minsub.spring.service.ProfileService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import javax.ws.rs.core.MediaType;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProfileController.class)
public class MvcTest {
    @BeforeClass
    public static void resolveActiveProfiles() {
        System.setProperty("spring.profiles.active", "development");
    }
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProfileService profileService;
    
    @Test
    public void profileTest() throws Exception {
        given(profileService.findOne(1))
                .willReturn(Profile.builder().name("test").searchId("testId").build());
        
        this.mockMvc.perform(get("/profiles/{id}", "1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                ;
    }
}
