package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.calendar.Calendar;
import com.eventmanager.coreservicediploma.model.entity.calendar.CalendarStatus;
import com.eventmanager.coreservicediploma.model.entity.user.Specification;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


public interface CalendarRepository extends JpaRepository<Calendar, Long>{

    List<Calendar> findCalendarsByDateAndSpecificationInAndStatus(Date date, List<Specification> specifications, CalendarStatus status);
}
