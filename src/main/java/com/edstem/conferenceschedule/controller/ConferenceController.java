package com.edstem.conferenceschedule.controller;

import com.edstem.conferenceschedule.contract.ConferenceRequest;
import com.edstem.conferenceschedule.contract.ConferenceResponse;
import com.edstem.conferenceschedule.contract.ScheduleRequest;
import com.edstem.conferenceschedule.contract.ScheduleResponse;
import com.edstem.conferenceschedule.contract.SpeakerRequest;
import com.edstem.conferenceschedule.contract.SpeakerResponse;
import com.edstem.conferenceschedule.service.ConferenceService;
import com.edstem.conferenceschedule.service.ScheduleService;
import com.edstem.conferenceschedule.service.SpeakerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conferences")
@CrossOrigin(origins = "*")

public class ConferenceController {
    public final ConferenceService conferenceService;
    public final ScheduleService scheduleService;
    public final SpeakerService speakerService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService, ScheduleService scheduleService, SpeakerService speakerService) {
        this.conferenceService = conferenceService;
        this.scheduleService = scheduleService;
        this.speakerService = speakerService;
    }

    @PostMapping()
    public ResponseEntity<ConferenceResponse> addANewConference(@Valid @RequestBody ConferenceRequest conferenceRequest) {
        ConferenceResponse response = conferenceService.addANewConference(conferenceRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/schedule/{conferenceId}")
    public ResponseEntity<ScheduleResponse> addScheduleToAConference(@PathVariable Long conferenceId, @Valid @RequestBody ScheduleRequest scheduleRequest) {
        ScheduleResponse response = scheduleService.addScheduleToAConference(conferenceId, scheduleRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/schedule/speaker/{conferenceId}/{scheduleId}")
    public ResponseEntity<SpeakerResponse> addSpeakerToAConferenceSchedule
            (@PathVariable Long conferenceId, @PathVariable Long scheduleId, @Valid @RequestBody SpeakerRequest speakerRequest) {

        SpeakerResponse response =
                speakerService.addSpeakerToAConferenceSchedule(conferenceId, scheduleId, speakerRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{conferenceId}")
    public ResponseEntity<ConferenceResponse> getConferenceById(@PathVariable Long conferenceId) {
        ConferenceResponse response = conferenceService.getConferenceById(conferenceId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{conferenceId}")
    public ResponseEntity<String> deleteAConferenceById(@PathVariable Long conferenceId) {
        String response = conferenceService.deleteAConferenceById(conferenceId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/speaker/{conferenceId}/{scheduleId}")
    public ResponseEntity<String> removeSpeakerFromConferenceScheduleById
            (@PathVariable Long conferenceId, @PathVariable Long scheduleId) {
        String response =
                conferenceService.removeSpeakerFromConferenceScheduleById(conferenceId, scheduleId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{conferenceId}/{scheduleId}")
    public ResponseEntity<String> updateScheduleById(@PathVariable Long conferenceId, @PathVariable Long scheduleId,
                                                     @RequestBody ScheduleRequest scheduleRequest) {
        String response = conferenceService.updateScheduleById(conferenceId, scheduleId, scheduleRequest);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/schedule/view-all/{conferenceId}")
    public ResponseEntity<List<ScheduleResponse>> listAllSchedulesByConferenceId(@PathVariable Long conferenceId) {
        List<ScheduleResponse> responseList = conferenceService.listAllSchedulesByConferenceId(conferenceId);
        return ResponseEntity.ok(responseList);
    }


    @GetMapping("/list-all")
    public ResponseEntity<List<ConferenceResponse>> listAllConference(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "3") int pageSize) {

        List<ConferenceResponse> response = conferenceService.getAllConferences(pageNumber,pageSize);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update/{conferenceId}")
    public ResponseEntity<String> updateConferenceById(@PathVariable Long conferenceId,
                                                       @Valid @RequestBody ConferenceRequest conferenceRequest) {
        String response = conferenceService.updateConferenceById(conferenceId, conferenceRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/schedule/view-speaker/{conferenceId}/{scheduleId}")
    public ResponseEntity<SpeakerResponse> getSpeakerOfASchedule(@PathVariable Long conferenceId, @PathVariable Long scheduleId) {
        SpeakerResponse response = speakerService.getSpeakerOfASchedule(conferenceId, scheduleId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/list/speakers")
    public ResponseEntity<List<SpeakerResponse>> viewAllSpeakers(){
        return new ResponseEntity<>(speakerService.viewAllSpeakers(), HttpStatus.OK);
    }
}






