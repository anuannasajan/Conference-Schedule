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
                .location(conference.getLocation())
                .foodOptions(conference.getFoodOptions())
                .hotelOptions(conference.getHotelOptions())
                .codeOfConduct(conference.getCodeOfConduct())
                .schedules(conference.getSchedules().stream().map(schedule ->
                        ScheduleResponse.builder()
                                .id(schedule.getId())
                                .time(schedule.getTime())
                                .talk(schedule.getTalk())
                                .speaker(schedule.getSpeaker() != null ? SpeakerResponse.builder()
                                        .id(schedule.getSpeaker().getId())
                                        .name(schedule.getSpeaker().getName())
                                        .bio(schedule.getSpeaker().getBio())
                                        .build()
                                        : null)
                                .build()).collect(Collectors.toList()))
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
                        .time(schedule.getTime())
                        .speaker(schedule.getSpeaker() != null ?
                                SpeakerResponse.builder()
                                        .id(schedule.getSpeaker().getId())
                                        .name(schedule.getSpeaker().getName())
                                        .bio(schedule.getSpeaker().getBio())
                                        .build()
                                : null)
                        .build())
                .collect(Collectors.toList());

        return responseList;
    }

    public List<ConferenceResponse> listAllConferences() {
        List<Conference> conferenceList = conferenceRepository.findAll();

        return conferenceList.stream().map(conference -> ConferenceResponse.builder()
                .id(conference.getId())
                .name(conference.getName())
                .description(conference.getDescription())
//                .date(conference.getDate())
                .location(conference.getLocation())
                .foodOptions(conference.getFoodOptions())
                .hotelOptions(conference.getHotelOptions())
                .codeOfConduct(conference.getCodeOfConduct())
                .schedules(conference.getSchedules() != null ? conference.getSchedules()
                        .stream().map(schedule -> ScheduleResponse.builder()
                                .id(schedule.getId())
                                .time(schedule.getTime())
                                .talk(schedule.getTalk())
                                .speaker(schedule.getSpeaker() != null ? SpeakerResponse.builder()
                                        .id(schedule.getSpeaker().getId())
                                        .name(schedule.getSpeaker().getName())
                                        .bio(schedule.getSpeaker().getBio())
                                        .build() : null)
                                .build())
                        .collect(Collectors.toList())
                        : null)
                .build()).collect(Collectors.toList());
    }

    public String updateConferenceById(Long conferenceId, ConferenceRequest conferenceRequest) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException("Conference not found"));

        Conference updated = Conference.builder()
                .id(conference.getId())
                .name(conferenceRequest.getName())
                .description(conferenceRequest.getDescription())
//                .date(conferenceRequest.getDate())
                .location(conferenceRequest.getLocation())
                .foodOptions(conferenceRequest.getFoodOptions().stream().toList())
                .hotelOptions(conferenceRequest.getHotelOptions().stream().toList())
                .codeOfConduct(conferenceRequest.getCodeOfConduct())
                .schedules(conference.getSchedules())
                .build();
        Conference saved = conferenceRepository.save(updated);
        return "Successfully updated the conference with ID " + saved.getId();
    }

}







