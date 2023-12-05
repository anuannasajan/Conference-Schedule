package com.edstem.conferenceschedule.service;

import com.edstem.conferenceschedule.contract.request.ScheduleRequest;
import com.edstem.conferenceschedule.contract.response.ScheduleResponse;
import com.edstem.conferenceschedule.model.Conference;
import com.edstem.conferenceschedule.model.Schedule;
import com.edstem.conferenceschedule.repository.ConferenceRepository;
import com.edstem.conferenceschedule.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    public final ConferenceRepository conferenceRepository;
    public final ScheduleRepository scheduleRepository;

    public ScheduleResponse addScheduleToAConference(Long conferenceId, ScheduleRequest scheduleRequest) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conference not found"));

        Schedule schedule = Schedule.builder()
                .talk(scheduleRequest.getTalk())
                .time(scheduleRequest.getTime())
                .duration(scheduleRequest.getDuration())
                .name(scheduleRequest.getName())
                .day(scheduleRequest.getDay())
                .bio(scheduleRequest.getBio())
                .build();
        Schedule savedSchedule = scheduleRepository.save(schedule);
        conference.getSchedules().add(savedSchedule);
        conferenceRepository.save(conference);
        return ScheduleResponse.builder()
                .scheduleId(savedSchedule.getScheduleId())
                .talk(savedSchedule.getTalk())
                .time(savedSchedule.getTime())
                .day(savedSchedule.getDay())
                .duration(savedSchedule.getDuration())
                .name(savedSchedule.getName())
                .bio(savedSchedule.getBio())
                .build();
    }

    public void deleteASchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
        scheduleRepository.delete(schedule);
    }

    public String deleteSchedule(Long conferenceId, Long scheduleId) {
        Optional<Conference> conference = conferenceRepository.findById(conferenceId);
        if (conference.isPresent()) {
            List<Schedule> schedules = conference.get().getSchedules();
            schedules.removeIf(schedule -> schedule.getScheduleId().equals(scheduleId));
            conferenceRepository.save(conference.get());
        } else {
            throw new EntityNotFoundException("Conference not found for id: " + conferenceId);
        }
        return "Successfully deleted the schedule with ID " + scheduleId;
    }

}




