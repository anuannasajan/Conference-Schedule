package com.edstem.conferenceschedule.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Speaker {
    private String bio;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long speakerId;
    private String name;
}
