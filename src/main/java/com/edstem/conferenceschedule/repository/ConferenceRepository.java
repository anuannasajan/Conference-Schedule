package com.edstem.conferenceschedule.repository;

import com.edstem.conferenceschedule.model.Conference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    Page<Conference> findAll(Pageable pageable);
}

