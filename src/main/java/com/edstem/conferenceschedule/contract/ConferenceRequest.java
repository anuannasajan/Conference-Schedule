package com.edstem.conferenceschedule.contract;

import com.edstem.conferenceschedule.model.Schedule;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConferenceRequest {

    @NotEmpty(message = "Conference name should not be empty")
    private String name;

    private String description;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;


    @NotEmpty(message = "Conference location should not be empty")
    private String location;
}


