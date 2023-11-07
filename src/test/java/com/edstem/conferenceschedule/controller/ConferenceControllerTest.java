package com.edstem.conferenceschedule.controller;

import com.edstem.conferenceschedule.contract.*;
import com.edstem.conferenceschedule.service.ConferenceService;
import com.edstem.conferenceschedule.service.ScheduleService;
import com.edstem.conferenceschedule.service.SpeakerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import static org.mockito.Mockito.
        when
        ;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.
        delete
        ;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.
        post
        ;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.
        status
        ;
@SpringBootTest
@AutoConfigureMockMvc
public class ConferenceControllerTest {
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    public ConferenceService conferenceService;
    @MockBean
    public ScheduleService scheduleService;
    @MockBean
    public SpeakerService speakerService;
    @Test
    public void testAddANewConference() throws Exception {
        ConferenceRequest conferenceRequest = new ConferenceRequest();
        conferenceRequest.setName("Test Conference");
        conferenceRequest.setDescription("A test conference");
        conferenceRequest.setLocation("Test Location");
        conferenceRequest.setFoodOptions(List.
                of
                        ("Vegan", "Gluten-free"));
        conferenceRequest.setHotelOptions(List.
                of
                        ("Hotel A", "Hotel B"));
        conferenceRequest.setCodeOfConduct("Test Code of Conduct");
        ConferenceResponse response = new ConferenceResponse();
        response.setId(1L);
        response.setName("Test Conference");
        response.setDescription("A test conference");
        response.setLocation("Test Location");
        response.setFoodOptions(List.
                of
                        ("Vegan", "Gluten-free"));
        response.setHotelOptions(List.
                of
                        ("Hotel A", "Hotel B"));
        response.setCodeOfConduct("Test Code of Conduct");

        when
                (conferenceService.addANewConference(conferenceRequest)).thenReturn(response);
        mockMvc.perform(
                        post
                                ("/conferences")
                                .contentType(MediaType.
                                        APPLICATION_JSON
                                )
                                .content(new ObjectMapper().writeValueAsString(conferenceRequest)))
                .andExpect(
                        status
                                ().isOk());
    }
    @Test
    public void testAddScheduleToAConference() throws Exception {
        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setTalk("Test Talk");
        ScheduleResponse response = new ScheduleResponse();
        response.setId(1L);
        response.setTalk("Test Talk");

        when
                (scheduleService.addScheduleToAConference(1L, scheduleRequest)).thenReturn(response);
        mockMvc.perform(
                        post
                                ("/conferences/schedule/1")
                                .content(new ObjectMapper().writeValueAsString(scheduleRequest))
                                .contentType("application/json"))
                .andExpect(
                        status
                                ().isOk());
    }
    @Test
    public void testAddSpeakerToAConferenceSchedule() throws Exception {
        SpeakerRequest speakerRequest = SpeakerRequest.
                builder
                        ()
                .name("test")
                .bio("bio")
                .build();
        SpeakerResponse response = SpeakerResponse.builder()
                .id(1L)
                .name("test")
                .bio("bio")
                .build();
        when(speakerService.addSpeakerToAConferenceSchedule(1L, 1L, speakerRequest)).thenReturn(response);
        mockMvc.perform(post("/conferences/schedule/speaker/1/1")
                        .content(new ObjectMapper().writeValueAsString(speakerRequest))
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetConferenceById() throws Exception {
        ConferenceResponse conferenceResponse = ConferenceResponse.builder()
                .id(1L)
                .name("test-name")
                .description("test-desc")
                .build();
        when(conferenceService.getConferenceById(1L)).thenReturn(conferenceResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/conferences/1"))
                .andExpect(status().isOk());
    }

}

