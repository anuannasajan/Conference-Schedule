package com.edstem.conferenceschedule.service;

import com.edstem.conferenceschedule.contract.request.SpeakerRequest;
import com.edstem.conferenceschedule.contract.response.SpeakerResponse;
import com.edstem.conferenceschedule.model.Conference;
import com.edstem.conferenceschedule.model.Schedule;
import com.edstem.conferenceschedule.model.Speaker;
import com.edstem.conferenceschedule.repository.ConferenceRepository;
import com.edstem.conferenceschedule.repository.ScheduleRepository;
import com.edstem.conferenceschedule.repository.SpeakerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeakerService {
    public final ConferenceRepository conferenceRepository;
    public final ScheduleRepository scheduleRepository;
    public final SpeakerRepository speakerRepository;
    public final ModelMapper modelMapper;

    public SpeakerResponse addSpeakerToAConferenceSchedule
            (Long conferenceId, Long scheduleId, SpeakerRequest speakerRequest) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conference not found"));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule Not Found"));

        Speaker speaker = Speaker.builder()
                .name(speakerRequest.getName())
                .bio(speakerRequest.getBio())
                .build();
        Speaker savedSpeaker = speakerRepository.save(speaker);
        Schedule updatedSchedule = Schedule.builder()
                .scheduleId(schedule.getScheduleId())
                .talk(schedule.getTalk())
                .time(schedule.getTime())
                .speaker(savedSpeaker)
                .build();
        Schedule savedSchedule = scheduleRepository.save(updatedSchedule);
        conferenceRepository.save(conference);
        return SpeakerResponse.builder()
                .speakerId(savedSpeaker.getSpeakerId())
                .name(savedSpeaker.getName())
                .bio(savedSpeaker.getBio())
                .build();
    }

    public SpeakerResponse getSpeakerOfASchedule(Long conferenceId, Long scheduleId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conference not found"));
        Schedule scheduleFound = conference.getSchedules().stream().filter(schedule ->
                schedule.getScheduleId().equals(scheduleId)).findFirst().orElse(null);
        if (scheduleFound == null) {
            throw new EntityNotFoundException("Schedule not found");
        }
        Speaker speakerFound = scheduleFound.getSpeaker() != null ? scheduleFound.getSpeaker() : null;
        if (speakerFound == null) {
            throw new EntityNotFoundException("Speaker not found");
        }
        return SpeakerResponse.builder()
                .speakerId(speakerFound.getSpeakerId())
                .name(speakerFound.getName())
                .bio(speakerFound.getBio())
                .build();
    }

    public List<SpeakerResponse> viewAllSpeakers() {
        List<Speaker> SpeakerList = speakerRepository.findAll();
        return SpeakerList.stream()
                .map(user -> modelMapper.map(user, SpeakerResponse.class))
                .collect(Collectors.toList());
    }

}

