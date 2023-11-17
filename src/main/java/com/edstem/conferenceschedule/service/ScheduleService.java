package com.edstem.conferenceschedule.service;

import com.edstem.conferenceschedule.contract.ScheduleRequest;
import com.edstem.conferenceschedule.contract.ScheduleResponse;
import com.edstem.conferenceschedule.exception.ConferenceNotFoundException;
import com.edstem.conferenceschedule.model.Conference;
import com.edstem.conferenceschedule.model.Schedule;
import com.edstem.conferenceschedule.repository.ConferenceRepository;
import com.edstem.conferenceschedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    public final ConferenceRepository conferenceRepository;
    public final ScheduleRepository scheduleRepository;
    @Autowired
    public ScheduleService(ConferenceRepository conferenceRepository, ScheduleRepository scheduleRepository){
        this.conferenceRepository = conferenceRepository;
        this.scheduleRepository = scheduleRepository;
    }
    public ScheduleResponse addScheduleToAConference(Long conferenceId, ScheduleRequest scheduleRequest) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(()->new ConferenceNotFoundException("Conference not found"));

        Schedule schedule = Schedule.builder()
                .talk(scheduleRequest.getTalk())
                .time(scheduleRequest.getTime())
                .name(scheduleRequest.getName())
                .bio(scheduleRequest.getBio())
                .build();
        Schedule savedSchedule = scheduleRepository.save(schedule);
        conference.getSchedules().add(savedSchedule);
        conferenceRepository.save(conference);
        return ScheduleResponse.builder()
                .id(savedSchedule.getId())
                .talk(savedSchedule.getTalk())
                .time(savedSchedule.getTime())
                .name(savedSchedule.getName())
                .bio(savedSchedule.getBio())
                .build();
    }
}


