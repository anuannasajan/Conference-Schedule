package com.edstem.conferenceschedule.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConferenceResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private String location;
    private List<String> foodOptions;
    private List<String> hotelOptions;
    private String codeOfConduct;
    private List<ScheduleResponse> schedules;
}

