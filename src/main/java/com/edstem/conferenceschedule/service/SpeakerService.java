package com.edstem.conferenceschedule.service;

import com.edstem.conferenceschedule.contract.SpeakerRequest;
import com.edstem.conferenceschedule.contract.SpeakerResponse;
import com.edstem.conferenceschedule.exception.ConferenceNotFoundException;
import com.edstem.conferenceschedule.exception.ScheduleNotFoundException;
import com.edstem.conferenceschedule.model.Conference;
import com.edstem.conferenceschedule.model.Schedule;
import com.edstem.conferenceschedule.model.Speaker;
import com.edstem.conferenceschedule.repository.ConferenceRepository;
import com.edstem.conferenceschedule.repository.ScheduleRepository;
import com.edstem.conferenceschedule.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpeakerService {
    public final ConferenceRepository conferenceRepository;
    public final ScheduleRepository scheduleRepository;
    public SpeakerRepository speakerRepository;
    @Autowired
    public SpeakerService(ConferenceRepository conferenceRepository,
                          ScheduleRepository scheduleRepository, SpeakerRepository speakerRepository){
        this.conferenceRepository = conferenceRepository;
        this.scheduleRepository = scheduleRepository;
        this.speakerRepository = speakerRepository;
    }
    public SpeakerResponse addSpeakerToAConferenceSchedule
            (Long conferenceId, Long scheduleId, SpeakerRequest speakerRequest) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(()->new ConferenceNotFoundException("Conference not found"));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()->new ScheduleNotFoundException("Schedule Not Found"));

        Speaker speaker = Speaker.builder()
                .name(speakerRequest.getName())
                .bio(speakerRequest.getBio())
                .build();
        Speaker savedSpeaker = speakerRepository.save(speaker);
        Schedule updatedSchedule = Schedule.builder()
                .id(schedule.getId())
                .talk(schedule.getTalk())
                .time(schedule.getTime())
                .speaker(savedSpeaker)
                .build();
        Schedule savedSchedule = scheduleRepository.save(updatedSchedule);
        conferenceRepository.save(conference);
        return SpeakerResponse.builder()
                .id(savedSpeaker.getId())
                .name(savedSpeaker.getName())
                .bio(savedSpeaker.getBio())
                .build();
    }

}

