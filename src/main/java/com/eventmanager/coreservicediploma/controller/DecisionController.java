package com.eventmanager.coreservicediploma.controller;

import com.eventmanager.coreservicediploma.decision.service.AssignmentService;
import com.eventmanager.coreservicediploma.model.entity.user.Specification;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import com.eventmanager.coreservicediploma.model.entity.user.dto.UserInfoDto;
import com.eventmanager.coreservicediploma.model.entity.user.dto.specification.SpecificationDto;
import com.eventmanager.coreservicediploma.model.repository.SpecificationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/decision")
public class DecisionController
{

    private final SpecificationRepository specificationRepository;
    private final AssignmentService assignmentService;

    public DecisionController(SpecificationRepository specificationRepository,
                              AssignmentService assignmentService)
    {
        this.specificationRepository = specificationRepository;
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public Map<SpecificationDto, UserInfoDto> getAssignmentDecision(@RequestParam(value = "date", required = false) String date,
                                                                    @RequestBody(required = false) List<Long> specIds) throws ParseException
    {

        Date dateParam = new Date();

        if (!StringUtils.isBlank(date)){
            dateParam = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("ru-RU")).parse(date);
        }

        if (specIds == null)
        {
            specIds = List.of(5L, 6L, 7L);
        }

        List<Specification> specifications = specificationRepository.findAllById(specIds);

        Map<Specification, UserInfo> solution = assignmentService.getSolution(specifications, dateParam);

        Map<SpecificationDto, UserInfoDto> response = new HashMap<>();

        solution.forEach((k, v) -> response.put(SpecificationDto.toShortDto(k), UserInfoDto.toShortDto(v)));

        return response;
    }
}
