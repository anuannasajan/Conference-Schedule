package com.edstem.conferenceschedule.service;

import com.edstem.conferenceschedule.contract.ConferenceRequest;
import com.edstem.conferenceschedule.contract.ConferenceResponse;
import com.edstem.conferenceschedule.contract.ScheduleRequest;
import com.edstem.conferenceschedule.contract.ScheduleResponse;
import com.edstem.conferenceschedule.contract.SpeakerResponse;
import com.edstem.conferenceschedule.exception.ConferenceNotFoundException;
import com.edstem.conferenceschedule.exception.ScheduleNotFoundException;
import com.edstem.conferenceschedule.model.Conference;
import com.edstem.conferenceschedule.model.Schedule;
import com.edstem.conferenceschedule.model.Speaker;
import com.edstem.conferenceschedule.repository.ConferenceRepository;
import com.edstem.conferenceschedule.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new ConferenceNotFoundException("Conference not found"));
        return ConferenceResponse.builder()
                .id(conference.getId())
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
                .orElseThrow(() -> new ConferenceNotFoundException("Conference not found"));
        conferenceRepository.delete(conference);

        return "Successfully deleted the conference with ID " + conferenceId;
    }

    public String removeSpeakerFromConferenceScheduleById(Long conferenceId, Long scheduleId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException("Conference not found"));

        Schedule speakerToRemoveSchedule = conference.getSchedules().stream().filter(schedule -> schedule.getId().equals(scheduleId))
                .findFirst()
                .orElse(null);

        if (speakerToRemoveSchedule == null) {
            throw new EntityNotFoundException("Speaker not found to remove");
        }
        Speaker speakerToRemove = speakerToRemoveSchedule.getSpeaker();
        Schedule updatedScheduleAfterRemove = Schedule.builder()
                .id(speakerToRemoveSchedule.getId())
                .time(speakerToRemoveSchedule.getTime())
                .talk(speakerToRemoveSchedule.getTalk())
                .speaker(null)
                .build();
        Schedule saved = scheduleRepository.save(updatedScheduleAfterRemove);
        conferenceRepository.save(conference);

        return "Successfully removed the speaker with ID " + speakerToRemove.getId() + "from schedule.";
    }

    public String updateScheduleById(Long conferenceId, Long scheduleId, ScheduleRequest scheduleRequest) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException("Conference not found"));

        Schedule schedule = conference.getSchedules().stream().filter(s -> s.getId().equals(scheduleId))
                .findFirst()
                .orElse(null);
        if (schedule == null) {
            throw new ScheduleNotFoundException("Schedule not found");
        }
        Schedule updated = Schedule.builder()
                .id(schedule.getId())
                .talk(scheduleRequest.getTalk())
                .time(scheduleRequest.getTime())
                .duration(scheduleRequest.getDuration())
                .speaker(schedule.getSpeaker())
                .build();
        Schedule saved = scheduleRepository.save(updated);
        conferenceRepository.save(conference);
        return "Successfully updated the schedule with ID " + saved.getId();
    }

    public List<ScheduleResponse> listAllSchedulesByConferenceId(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException("Conference not found"));

        List<ScheduleResponse> responseList = conference.getSchedules().stream()
                .map(schedule -> ScheduleResponse.builder()
                        .id(schedule.getId())
                        .talk(schedule.getTalk())
                        .name(schedule.getName())
                        .bio(schedule.getBio())
                        .duration(schedule.getDuration())
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
                .orElseThrow(() -> new ConferenceNotFoundException("Conference not found"));

        Conference updated = Conference.builder()
                .id(conference.getId())
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
        return "Successfully updated the conference with ID " + saved.getId();
    }

}







