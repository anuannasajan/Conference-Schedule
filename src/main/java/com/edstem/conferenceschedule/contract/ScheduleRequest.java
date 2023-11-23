package com.edstem.conferenceschedule.contract;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequest {
    @NotEmpty(message = "Talk is mandatory")
    private String talk;
    private LocalTime time;
    private String duration;
    private String day;
    private String name;
    private String bio;
}

