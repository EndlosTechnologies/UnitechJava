package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddRingFramesMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddRingFrameRepossitory extends JpaRepository<AddRingFramesMachine, Long> {
    List<AddRingFramesMachine> findByStatus(boolean status);
}
