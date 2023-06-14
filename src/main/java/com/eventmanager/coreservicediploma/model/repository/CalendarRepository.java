package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.calendar.Calendar;
import com.eventmanager.coreservicediploma.model.entity.calendar.CalendarStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findCalendarsByDateAndStatus(Date date, CalendarStatus status);
}
