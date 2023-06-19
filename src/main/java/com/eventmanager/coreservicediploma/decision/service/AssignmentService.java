package com.eventmanager.coreservicediploma.decision.service;

import com.eventmanager.coreservicediploma.model.entity.calendar.Calendar;
import com.eventmanager.coreservicediploma.model.entity.calendar.CalendarStatus;
import com.eventmanager.coreservicediploma.model.entity.decision.Assignment;
import com.eventmanager.coreservicediploma.model.entity.decision.AssignmentDecision;
import com.eventmanager.coreservicediploma.model.entity.decision.AssignmentStatus;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AssignmentService
{

    private final CalendarRepository calendarRepository;
    private final int CANNOT = 999999999;

    public AssignmentService(CalendarRepository calendarRepository)
    {
        this.calendarRepository = calendarRepository;
    }

    @PostConstruct
    private void init()
    {
        Loader.loadNativeLibraries();
    }


    public AssignmentDecision getSolution(List<Specification> specs, Date date)
    {

        MPSolver solver = MPSolver.createSolver("SCIP");

        if (solver == null)
        {
            log.error("Could not create solver SCIP");
            throw new RuntimeException("Could not create solver SCIP");
        }

        AssignmentDecision decision = new AssignmentDecision();
        decision.setStatus(AssignmentStatus.OPTIMAL);

        List<Calendar> calendars = calendarRepository.findCalendarsByDateAndSpecificationInAndStatus(date, specs, CalendarStatus.WORKING);
        List<UserInfo> userInfos = calendars.stream().map(Calendar::getUserInfo).distinct().toList();

        //Вертикаль - worker
        int specificationCount =
                specs.size();
        //Горизонталь - task
        int userCount =
                userInfos.size();


        double[][] costs =
                new double[specificationCount][userCount];

        for (int i = 0; i < specificationCount; i++)
        {
            Specification currentSpec = specs.get(i);

            for (int j = 0; j < userCount; j++)
            {
                UserInfo currentInfo = userInfos.get(j);

                Calendar currentCalendar = calendars.stream()
                        .filter(calendar -> calendar
                                .getSpecification().getId()
                                .equals(currentSpec.getId()))
                        .filter(calendar -> calendar
                                .getUserInfo().getId()
                                .equals(currentInfo.getId()))
                        .findFirst().orElse(null);

                costs[i][j] = currentCalendar == null ? CANNOT : currentCalendar.getPrice();
            }
        }


        MPVariable[][] x = new MPVariable[specificationCount][userCount];

        for (int i = 0; i < specificationCount; i++)
        {
            for (int j = 0; j < userCount; j++)
            {
                x[i][j] = solver.makeIntVar(0, 1, "");
            }
        }

        // Constraints
        // Each worker is assigned to at most one task.
        for (int i = 0; i < specificationCount; ++i)
        {
            MPConstraint constraint = solver.makeConstraint(0, 1, "");
            for (int j = 0; j < userCount; ++j)
            {
                constraint.setCoefficient(x[i][j], 1);
            }
        }
        // Each task is assigned to exactly one worker.
        for (int j = 0; j < userCount; ++j)
        {
            MPConstraint constraint = solver.makeConstraint(1, 1, "");
            for (int i = 0; i < specificationCount; ++i)
            {
                constraint.setCoefficient(x[i][j], 1);
            }
        }

        // Целевая функция
        MPObjective objective = solver.objective();
        for (int i = 0; i < specificationCount; ++i)
        {
            for (int j = 0; j < userCount; ++j)
            {
                objective.setCoefficient(x[i][j], costs[i][j]);
            }
        }
        objective.setMinimization();


        // Solve
        MPSolver.ResultStatus resultStatus = solver.solve();

        // Print solution.
        // Check that the problem has a feasible solution.
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL || resultStatus == MPSolver.ResultStatus.FEASIBLE)
        {
            log.info("Total cost: " + objective.value() + "\n");

            for (int i = 0; i < specificationCount; ++i)
            {
                for (int j = 0; j < userCount; ++j)
                {
                    // Test if x[i][j] is 0 or 1 (with tolerance for floating point
                    // arithmetic).

                    if (x[i][j].solutionValue() > 0.5)
                    {
                        Specification specification = specs.get(i);
                        UserInfo userInfo = userInfos.get(j);

                        if (Math.abs(costs[i][j] - CANNOT) < 0.5){
                            break;
                        }

                        Assignment assignment = new Assignment(specification, userInfo, costs[i][j], null);
                        decision.getDecision().add(assignment);
                        decision.increaseCost(assignment.getCost());

                        log.info("Spec " + specification.getName() + " assigned to user " + userInfo.getName() + ".  Cost = " + costs[i][j]);
                    }
                }
            }

            if (decision.getDecision().size() == 0){
                decision.setStatus(AssignmentStatus.BAD);
            }
        }
        else
        {
            log.warn("No solution found.");
            decision.setStatus(AssignmentStatus.BAD);
        }

        return decision;
    }

    @Data
    @AllArgsConstructor
    static class InfoCost
    {
        private UserInfo userInfo;
        private double cost;
    }
}
