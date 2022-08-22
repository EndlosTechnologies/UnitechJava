package com.unitechApi.securityService.Controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.securityService.Dto.SecuritySave;
import com.unitechApi.securityService.Entity.EventEntity;
import com.unitechApi.securityService.Entity.SecurityModel;
import com.unitechApi.securityService.Event.EventType;
import com.unitechApi.securityService.Repository.EventRepository;
import com.unitechApi.securityService.Service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/unitech/api/v1/security")
@RestController
public class SecurityController {

    private final SecurityService securityService;
    private final EventRepository eventRepository;


    public SecurityController(SecurityService securityService, EventRepository eventRepository) {
        this.securityService = securityService;
        this.eventRepository = eventRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SaveData(@RequestBody SecurityModel securitySave, Principal principal) {
        SecurityModel securityModel = securityService.saveData(securitySave);
        eventRepository.save(new EventEntity("saved Data ", EventType.REQUEST_SAVE, principal.getName()));
        return new ResponseEntity<>(PageResponse.SuccessResponse(securityModel), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllData(Principal principal) {
        List<SecuritySave> Data = securityService.FindAll();
        eventRepository.save(new EventEntity("view Data ",EventType.REQUEST_VIEW_ALL, principal.getName()));
        return new ResponseEntity<>(PageResponse.SuccessResponse(Data), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetById(@PathVariable Long id,Principal principal) {
        Optional<?> data = securityService.FindById(id);
        eventRepository.save(new EventEntity("view By id Get Data",EventType.REQUEST_VIEW, principal.getName()));
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping("/statusA/{id}")
    public ResponseEntity<?> acceptStatus(@PathVariable Long id,Principal principal) {
        SecurityModel securityModel = securityService.changeAStatus(id);
        eventRepository.save(new EventEntity("status changes "+securityModel.getCstatus() ,EventType.REQUEST_STATUS_CHANGE, principal.getName()));
        return new ResponseEntity<>(PageResponse.SuccessResponse(securityModel), HttpStatus.OK);
    }

    @GetMapping("/statusR/{id}")
    public ResponseEntity<?> RejectStatus(@PathVariable Long id, Principal principal ) {
        SecurityModel securityModel = securityService.changeAStatus(id);
        eventRepository.save(new EventEntity("status changes",EventType.REQUEST_STATUS_CHANGE, principal.getName()));
        return new ResponseEntity<>(PageResponse.SuccessResponse(securityModel), HttpStatus.OK);
    }
}
