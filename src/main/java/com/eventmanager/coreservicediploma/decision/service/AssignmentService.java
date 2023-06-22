package com.eventmanager.coreservicediploma.decision.service;

import com.eventmanager.coreservicediploma.model.entity.calendar.Calendar;
import com.eventmanager.coreservicediploma.model.entity.calendar.CalendarStatus;
import com.eventmanager.coreservicediploma.model.entity.decision.route.RouteAssignment;
import com.eventmanager.coreservicediploma.model.entity.decision.route.RouteAssignmentDecision;
import com.eventmanager.coreservicediploma.model.entity.decision.spec.Assignment;
import com.eventmanager.coreservicediploma.model.entity.decision.spec.AssignmentDecision;
import com.eventmanager.coreservicediploma.model.entity.decision.AssignmentStatus;
import com.eventmanager.coreservicediploma.model.entity.user.Location;
import com.eventmanager.coreservicediploma.model.entity.user.Specification;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import com.eventmanager.coreservicediploma.model.repository.CalendarRepository;
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
import java.util.List;

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
                new double[userCount][specificationCount];

        for (int i = 0; i < userCount; i++)
        {
            UserInfo currentInfo = userInfos.get(i);

            for (int j = 0; j < specificationCount; j++)
            {
                Specification currentSpec = specs.get(j);

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


        MPVariable[][] x = new MPVariable[userCount][specificationCount];

        for (int i = 0; i < userCount; i++)
        {
            for (int j = 0; j < specificationCount; j++)
            {
                x[i][j] = solver.makeIntVar(0, 1, "");
            }
        }

        // Constraints
        // Each worker is assigned to at most one task.
        for (int i = 0; i < userCount; ++i)
        {
            MPConstraint constraint = solver.makeConstraint(0, 1, "");
            for (int j = 0; j < specificationCount; ++j)
            {
                constraint.setCoefficient(x[i][j], 1);
            }
        }
        // Each task is assigned to exactly one worker.
        for (int j = 0; j < specificationCount; ++j)
        {
            MPConstraint constraint = solver.makeConstraint(1, 1, "");
            for (int i = 0; i < userCount; ++i)
            {
                constraint.setCoefficient(x[i][j], 1);
            }
        }

        // Целевая функция
        MPObjective objective = solver.objective();
        for (int i = 0; i < userCount; ++i)
        {
            for (int j = 0; j < specificationCount; ++j)
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

            for (int i = 0; i < userCount; ++i)
            {
                for (int j = 0; j < specificationCount; ++j)
                {
                    // Test if x[i][j] is 0 or 1 (with tolerance for floating point
                    // arithmetic).

                    if (x[i][j].solutionValue() > 0.5)
                    {
                        UserInfo userInfo = userInfos.get(i);
                        Specification specification = specs.get(j);

                        if (Math.abs(costs[i][j] - CANNOT) < 0.5)
                        {
                            break;
                        }

                        Assignment assignment = new Assignment(specification, userInfo, costs[i][j], null);
                        decision.getDecision().add(assignment);
                        decision.increaseCost(assignment.getCost());

                        log.info("Spec " + specification.getName() + " assigned to user " + userInfo.getName() + ".  Cost = " + costs[i][j]);
                    }
                }
            }

            if (decision.getDecision().size() == 0)
            {
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

    public RouteAssignmentDecision getRouteSolution(List<Location> locations)
    {

        MPSolver solver = MPSolver.createSolver("SCIP");

        if (solver == null)
        {
            log.error("Could not create solver SCIP");
            throw new RuntimeException("Could not create solver SCIP");
        }

        RouteAssignmentDecision decision = new RouteAssignmentDecision();
        decision.setStatus(AssignmentStatus.OPTIMAL);


        if (locations.size() < 2)
        {
            log.error("Cannot calculate route!");
            throw new RuntimeException("Too few points!");
        }

        decision.setStart(locations.stream().findFirst().orElseThrow());
        decision.setFinish(locations.get(locations.size() - 1));


        List<Location> targets = locations.subList(1, locations.size());
        List<Location> sources = locations.subList(0, locations.size() - 1);


        //Вертикаль - worker
        int sourceCount = sources.size();
        //Горизонталь - task
        int targetCount = targets.size();

        double[][] costs = new double[targetCount][sourceCount];


        for (int i = 0; i < sourceCount; i++)
        {
            Location source = sources.get(i);


            for (int j = 0; j < targetCount; j++)
            {
                Location target = targets.get(j);

                if (source.equals(target)

//                        || (source.equals(decision.getStart()) && target.equals(decision.getFinish()))
                ){
                    costs[i][j] = CANNOT;
                }
                else {
                    costs[i][j] = getRouteCost(source, target);
                }

                log.info("Cost from " + sources.get(i) + " to" + targets.get(j) + " = " + costs[i][j]);
            }
        }


        MPVariable[][] x = new MPVariable[targetCount][sourceCount];

        for (int i = 0; i < sourceCount; i++)
        {
            for (int j = 0; j < targetCount; j++)
            {
                x[i][j] = solver.makeIntVar(0, 1, "");
            }
        }

        // Constraints
        // Each worker is assigned to at most one task.
        for (int i = 0; i < sourceCount; ++i)
        {
            MPConstraint constraint = solver.makeConstraint(0, 1, "");
            for (int j = 0; j < targetCount; ++j)
            {
                constraint.setCoefficient(x[i][j], 1);
            }
        }

        // Each task is assigned to exactly one worker.
        for (int j = 0; j < targetCount; ++j)
        {
            MPConstraint constraint = solver.makeConstraint(1, 1, "");
            for (int i = 0; i < sourceCount; ++i)
            {
                constraint.setCoefficient(x[i][j], 1);
            }
        }

        // Целевая функция
        MPObjective objective = solver.objective();
        for (int i = 0; i < sourceCount; ++i)
        {
            for (int j = 0; j < targetCount; ++j)
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

            for (int i = 0; i < sourceCount; ++i)
            {
                for (int j = 0; j < targetCount; ++j)
                {
                    // Test if x[i][j] is 0 or 1 (with tolerance for floating point
                    // arithmetic).

                    if (x[i][j].solutionValue() > 0.5)
                    {
                        Location source = sources.get(i);
                        Location target = targets.get(j);

                        RouteAssignment routeAssignment = new RouteAssignment(source, target, costs[i][j], null);
                        decision.getDecision().add(routeAssignment);
                        decision.increaseCost(routeAssignment.getCost());

//                        if (source.equals(decision.getStart())){
//                            log.info("From " + "start" + " to " + target.getStreet() + ".  Cost = " + costs[i][j]);
//                        }
//
//                        if (target.equals(decision.getFinish())){
//                            log.info("From " + source.getStreet() + " to " + "finish" + ".  Cost = " + costs[i][j]);
//                            continue;
//                        }

                        log.info("From " + source.getStreet() + " to " + target.getStreet() + ".  Cost = " + costs[i][j]);

                    }
                }
            }

            if (decision.getDecision().size() == 0)
            {
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

    private double getRouteCost(Location target, Location source)
    {
        double EARTH_RADIUS = 6372795;
        double PI = Math.PI;


        double latFrom = Double.parseDouble(source.getLat()) * PI / 180;
        double latTo = Double.parseDouble(target.getLat()) * PI / 180;

        double lonFrom = Double.parseDouble(source.getLon()) * PI / 180;
        double lonTo = Double.parseDouble(source.getLon()) * PI / 180;

        double cosLatFrom = Math.cos(latFrom);
        double cosLatTo = Math.cos(latTo);
        double sinLatFrom = Math.sin(latFrom);
        double sinLatTo = Math.sin(latTo);

        double delta = lonTo - lonFrom;
        double cosDelta = Math.cos(delta);
        double sinDelta = Math.sin(delta);

        double x = sinLatFrom * sinLatTo + cosLatFrom * cosLatTo * cosDelta;
        double y = Math.sqrt(Math.pow(cosLatTo * sinDelta, 2) + Math.pow(cosLatFrom * sinLatTo - sinLatFrom * cosLatTo * cosDelta, 2));

        double ad = Math.atan2(y, x);

        return ad * EARTH_RADIUS;
    }
}
