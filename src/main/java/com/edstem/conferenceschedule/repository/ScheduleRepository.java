package com.edstem.conferenceschedule.repository;

import com.edstem.conferenceschedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
