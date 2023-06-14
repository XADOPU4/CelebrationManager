package com.eventmanager.coreservicediploma.decision.service;

import com.eventmanager.coreservicediploma.model.entity.calendar.CalendarStatus;
import com.eventmanager.coreservicediploma.model.entity.user.Specification;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import com.eventmanager.coreservicediploma.model.repository.CalendarRepository;
import com.eventmanager.coreservicediploma.model.service.UserService;
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AssignmentService {

    private final UserService userService;

    //todo change to service
    private final CalendarRepository calendarRepository;

    public AssignmentService(UserService userService, CalendarRepository calendarRepository) {
        this.userService = userService;
        this.calendarRepository = calendarRepository;
    }

    @PostConstruct
    private void init(){
        Loader.loadNativeLibraries();
    }

    public List<UserInfo> prepareUsers(List<Specification> specs, Date date){

        List<User> allUsers = userService.getAllUsersDetailed();
        calendarRepository.findCalendarsByDateAndStatus(date, CalendarStatus.WORKING);


        return null;
    }

    public Map<Specification, UserInfo> getSolution(List<Specification> specs, List<UserInfo> users) {

        MPSolver solver = MPSolver.createSolver("SCIP");

        if (solver == null) {
            log.error("Could not create solver SCIP");
            throw new RuntimeException("Could not create solver SCIP");
        }

        //Вертикаль - worker
        int specificationCount = specs.size();
        //Горизонталь - task
        int userCount = users.size();

        MPVariable[][] x = new MPVariable[specificationCount][userCount];

        for (int i = 0; i < specificationCount; i++) {
            for (int j = 0; j < userCount; j++) {
                x[i][j] = solver.makeIntVar(0, 1, "");
            }
        }

        // Constraints
        // Each worker is assigned to at most one task.
        for (int i = 0; i < specificationCount; ++i) {
            MPConstraint constraint = solver.makeConstraint(0, 1, "");
            for (int j = 0; j < userCount; ++j) {
                constraint.setCoefficient(x[i][j], 1);
            }
        }
        // Each task is assigned to exactly one worker.
        for (int j = 0; j < userCount; ++j) {
            MPConstraint constraint = solver.makeConstraint(1, 1, "");
            for (int i = 0; i < specificationCount; ++i) {
                constraint.setCoefficient(x[i][j], 1);
            }
        }
//
//        // Целевая функция
//        MPObjective objective = solver.objective();
//        for (int i = 0; i < specificationCount; ++i) {
//            for (int j = 0; j < userCount; ++j) {
//                objective.setCoefficient(x[i][j], costs[i][j]);
//            }
//        }
//        objective.setMinimization();


        return null;
    }
}
