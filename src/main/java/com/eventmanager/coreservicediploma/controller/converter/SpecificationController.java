package com.eventmanager.coreservicediploma.controller.converter;

import com.eventmanager.coreservicediploma.model.entity.user.Specification;
import com.eventmanager.coreservicediploma.model.entity.user.dto.specification.SpecificationDto;
import com.eventmanager.coreservicediploma.model.repository.SpecificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    private SpecificationRepository specificationRepository;

    public SpecificationController(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SpecificationDto>> getAll(@RequestParam(name = "role", required = false) String role) {
        List<Specification> allSpecifications = specificationRepository.findAll();

        if (role != null){
            allSpecifications = allSpecifications.stream().filter(spec -> spec.getRole().getName().equals(role)).toList();
        }

        return ResponseEntity
                .ok(allSpecifications.stream()
                        .map(SpecificationDto::toDto)
                        .toList());
    }
}
