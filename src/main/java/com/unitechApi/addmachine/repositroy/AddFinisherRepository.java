package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddFinisherMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddFinisherRepository extends JpaRepository<AddFinisherMachine, Long> {
    List<AddFinisherMachine> findByStatus(boolean status);}
