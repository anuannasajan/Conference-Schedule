package com.edstem.conferenceschedule.contract;

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
    //@NotNull(message = "Conference name is mandatory")
    @NotEmpty(message = "Conference name should not be empty")
    private String name;

    private String description;
    private LocalDate endDate;
    private LocalTime endTime;

    //@NotNull(message = "Conference location is mandatory")
    @NotEmpty(message = "Conference location should not be empty")
    private String location;

    private List<String> foodOptions;
    private List<String> hotelOptions;
    private String codeOfConduct;
}


