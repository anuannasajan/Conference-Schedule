package com.edstem.conferenceschedule.contract;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeakerRequest {
    @NotEmpty(message = "Name is mandatory")
    private String name;
    private String bio;
}
