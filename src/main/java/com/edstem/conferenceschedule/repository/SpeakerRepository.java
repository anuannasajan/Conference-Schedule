package com.edstem.conferenceschedule.repository;

import com.edstem.conferenceschedule.model.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
