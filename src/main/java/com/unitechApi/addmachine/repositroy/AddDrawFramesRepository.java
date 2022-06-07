package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddDrawFramesMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddDrawFramesRepository extends JpaRepository<AddDrawFramesMachine, Long> {
    List<AddDrawFramesMachine> findByStatus(boolean status);
    Boolean existsByName(String name);
}
