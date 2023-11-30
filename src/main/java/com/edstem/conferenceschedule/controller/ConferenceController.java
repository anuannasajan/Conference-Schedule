package com.edstem.conferenceschedule.controller;

import com.edstem.conferenceschedule.contract.request.ConferenceRequest;
import com.edstem.conferenceschedule.contract.response.ConferenceResponse;
import com.edstem.conferenceschedule.service.ConferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/conferences")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ConferenceController {
    public final ConferenceService conferenceService;

    @PostMapping
    public ResponseEntity<ConferenceResponse> addANewConference(@Valid @RequestBody ConferenceRequest conferenceRequest) {
        ConferenceResponse response = conferenceService.addANewConference(conferenceRequest);
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

    @GetMapping("/list-all")
    public ResponseEntity<List<ConferenceResponse>> listAllConference() {
        List<ConferenceResponse> response = conferenceService.getAllConferences();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{conferenceId}")
    public ResponseEntity<String> updateConferenceById(@PathVariable Long conferenceId,
                                                       @Valid @RequestBody ConferenceRequest conferenceRequest) {
        String response = conferenceService.updateConferenceById(conferenceId, conferenceRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pageable")
    public Page<ConferenceResponse> getPageable(
            @PageableDefault(sort = "conferenceId", direction = Sort.Direction.DESC) Pageable pageable) {
        return conferenceService.getPageable(pageable);
    }
}



