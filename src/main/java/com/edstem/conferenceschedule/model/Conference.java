package com.edstem.conferenceschedule.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conferenceId;
    private String name;
    private String description;
    private LocalDate startDate;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm ")
    private LocalTime startTime;

    private LocalDate endDate;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm ")
    private LocalTime endTime;

    private String location;
    private String codeOfConduct;

    @OneToMany
    private List<Schedule> schedules;
}