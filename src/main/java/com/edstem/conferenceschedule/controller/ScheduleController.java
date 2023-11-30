package com.edstem.conferenceschedule.controller;

import com.edstem.conferenceschedule.contract.request.ScheduleRequest;
import com.edstem.conferenceschedule.contract.response.ScheduleResponse;
import com.edstem.conferenceschedule.service.ConferenceService;
import com.edstem.conferenceschedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ConferenceService conferenceService;

    @PostMapping("/{conferenceId}")
    public ResponseEntity<ScheduleResponse> addScheduleToAConference(@PathVariable Long conferenceId, @Valid @RequestBody ScheduleRequest scheduleRequest) {
        ScheduleResponse response = scheduleService.addScheduleToAConference(conferenceId, scheduleRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{conferenceId}/{scheduleId}")
    public ResponseEntity<String> updateScheduleById(@PathVariable Long conferenceId, @PathVariable Long scheduleId,
                                                     @RequestBody ScheduleRequest scheduleRequest) {
        String response = conferenceService.updateScheduleById(conferenceId, scheduleId, scheduleRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view-all/{conferenceId}")
    public ResponseEntity<List<ScheduleResponse>> listAllSchedulesByConferenceId(@PathVariable Long conferenceId) {
        List<ScheduleResponse> responseList = conferenceService.listAllSchedulesByConferenceId(conferenceId);
        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{conferenceId}/{scheduleId}")
    public String deleteSchedule(@PathVariable Long conferenceId, @PathVariable Long scheduleId) {
        return scheduleService.deleteSchedule(conferenceId, scheduleId);
    }
}
