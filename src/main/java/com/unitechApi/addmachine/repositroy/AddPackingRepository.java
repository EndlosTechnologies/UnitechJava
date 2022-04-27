package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddPackingMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddPackingRepository extends JpaRepository<AddPackingMachine, Long> {
    List<AddPackingMachine> findByStatus(boolean status);
}
