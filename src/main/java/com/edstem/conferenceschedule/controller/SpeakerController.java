package com.edstem.conferenceschedule.controller;

import com.edstem.conferenceschedule.contract.request.SpeakerRequest;
import com.edstem.conferenceschedule.contract.response.SpeakerResponse;
import com.edstem.conferenceschedule.service.ConferenceService;
import com.edstem.conferenceschedule.service.SpeakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/speaker")
public class SpeakerController {

    private final SpeakerService speakerService;
    private final ConferenceService conferenceService;

    @PostMapping("/schedule/{conferenceId}/{scheduleId}")
    public ResponseEntity<SpeakerResponse> addSpeakerToAConferenceSchedule
            (@PathVariable Long conferenceId, @PathVariable Long scheduleId, @Valid @RequestBody SpeakerRequest speakerRequest) {

        SpeakerResponse response =
                speakerService.addSpeakerToAConferenceSchedule(conferenceId, scheduleId, speakerRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{conferenceId}/{scheduleId}")
    public ResponseEntity<String> removeSpeakerFromConferenceScheduleById
            (@PathVariable Long conferenceId, @PathVariable Long scheduleId) {
        String response =
                conferenceService.removeSpeakerFromConferenceScheduleById(conferenceId, scheduleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/schedule/view-speaker/{conferenceId}/{scheduleId}")
    public ResponseEntity<SpeakerResponse> getSpeakerOfASchedule(@PathVariable Long conferenceId, @PathVariable Long scheduleId) {
        SpeakerResponse response = speakerService.getSpeakerOfASchedule(conferenceId, scheduleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SpeakerResponse>> viewAllSpeakers() {
        return new ResponseEntity<>(speakerService.viewAllSpeakers(), HttpStatus.OK);
    }
}
