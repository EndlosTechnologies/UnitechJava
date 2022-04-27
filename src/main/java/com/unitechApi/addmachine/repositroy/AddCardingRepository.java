package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddCardingMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddCardingRepository extends JpaRepository<AddCardingMachine, Long> {
    List<AddCardingMachine> findByStatus(boolean status);
}
