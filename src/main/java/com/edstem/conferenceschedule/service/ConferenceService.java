package com.edstem.conferenceschedule.service;

import com.edstem.conferenceschedule.contract.request.ConferenceRequest;
import com.edstem.conferenceschedule.contract.request.ScheduleRequest;
import com.edstem.conferenceschedule.contract.response.ConferenceResponse;
import com.edstem.conferenceschedule.contract.response.ScheduleResponse;
import com.edstem.conferenceschedule.model.Conference;
import com.edstem.conferenceschedule.model.Schedule;
import com.edstem.conferenceschedule.model.Speaker;
import com.edstem.conferenceschedule.repository.ConferenceRepository;
import com.edstem.conferenceschedule.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;


    public ConferenceResponse addANewConference(ConferenceRequest conferenceRequest) {
        Conference conference = modelMapper.map(conferenceRequest, Conference.class);
        Conference saved = conferenceRepository.save(conference);
        ConferenceResponse response = modelMapper.map(saved, ConferenceResponse.class);
        return response;
    }

    public ConferenceResponse getConferenceById(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conference not found"));
        return ConferenceResponse.builder()
                .conferenceId(conference.getConferenceId())
                .name(conference.getName())
                .description(conference.getDescription())
                .startDate(conference.getStartDate())
                .startTime(conference.getStartTime())
                .endDate(conference.getEndDate())
                .endTime(conference.getEndTime())
                .location(conference.getLocation())
                .build();
    }

    public String deleteAConferenceById(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conference not found"));
        conferenceRepository.delete(conference);

        return "Successfully deleted the conference with ID " + conferenceId;
    }

    public String removeSpeakerFromConferenceScheduleById(Long conferenceId, Long scheduleId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conference not found"));

        Schedule speakerToRemoveSchedule = conference.getSchedules().stream().filter(schedule -> schedule.getScheduleId().equals(scheduleId))
                .findFirst()
                .orElse(null);

        if (speakerToRemoveSchedule == null) {
            throw new EntityNotFoundException("Speaker not found to remove");
        }
        Speaker speakerToRemove = speakerToRemoveSchedule.getSpeaker();
        Schedule updatedScheduleAfterRemove = Schedule.builder()
                .scheduleId(speakerToRemoveSchedule.getScheduleId())
                .time(speakerToRemoveSchedule.getTime())
                .talk(speakerToRemoveSchedule.getTalk())
                .speaker(null)
                .build();
        Schedule saved = scheduleRepository.save(updatedScheduleAfterRemove);
        conferenceRepository.save(conference);

        return "Successfully removed the speaker with ID " + speakerToRemove.getSpeakerId() + "from schedule.";
    }

    public String updateScheduleById(Long conferenceId, Long scheduleId, ScheduleRequest scheduleRequest) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conference not found"));

        Schedule schedule = conference.getSchedules().stream().filter(s -> s.getScheduleId().equals(scheduleId))
                .findFirst()
                .orElse(null);
        if (schedule == null) {
            throw new EntityNotFoundException("Schedule not found");
        }
        Schedule updated = Schedule.builder()
                .scheduleId(schedule.getScheduleId())
                .talk(scheduleRequest.getTalk())
                .time(scheduleRequest.getTime())
                .duration(scheduleRequest.getDuration())
                .name(schedule.getName())
                .day(schedule.getDay())
                .bio(schedule.getBio())
                .build();
        Schedule saved = scheduleRepository.save(updated);
        conferenceRepository.save(conference);
        return "Successfully updated the schedule with ID " + saved.getScheduleId();
    }


    public List<ScheduleResponse> listAllSchedulesByConferenceId(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conference not found"));

        List<ScheduleResponse> responseList = conference.getSchedules().stream()
                .map(schedule -> ScheduleResponse.builder()
                        .scheduleId(schedule.getScheduleId())
                        .talk(schedule.getTalk())
                        .name(schedule.getName())
                        .bio(schedule.getBio())
                        .duration(schedule.getDuration())
                        .day(schedule.getDay())
                        .time(schedule.getTime())
                        .build())
                .collect(Collectors.toList());

        return responseList;

    }

    public List<ConferenceResponse> getAllConferences() {
        return conferenceRepository.findAll().stream()
                .map(conference -> modelMapper.map(conference, ConferenceResponse.class))
                .collect(Collectors.toList());
    }


    public String updateConferenceById(Long conferenceId, ConferenceRequest conferenceRequest) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conference not found"));

        Conference updated = Conference.builder()
                .conferenceId(conference.getConferenceId())
                .name(conferenceRequest.getName())
                .description(conferenceRequest.getDescription())
                .startDate(conferenceRequest.getStartDate())
                .startTime(conferenceRequest.getStartTime())
                .endDate(conferenceRequest.getEndDate())
                .endTime(conferenceRequest.getEndTime())
                .location(conferenceRequest.getLocation())
                .schedules(conference.getSchedules())
                .build();
        Conference saved = conferenceRepository.save(updated);
        return "Successfully updated the conference with ID " + saved.getConferenceId();
    }

    public Page<ConferenceResponse> getPageable(Pageable pageable) {
        Page<Conference> tickets = conferenceRepository.findAll(pageable);
        return tickets.map(appList -> modelMapper.map(appList, ConferenceResponse.class));
    }
}













