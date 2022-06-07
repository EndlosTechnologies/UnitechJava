package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddLapFormer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddLapFormerRepository extends JpaRepository<AddLapFormer , Long> {
    List<AddLapFormer> findByStatus(boolean status);
    Boolean existsByName(String name);
}
