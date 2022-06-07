package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddComber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddComberRepository extends JpaRepository<AddComber ,Long> {
    List<AddComber> findByStatus(boolean status);
    Boolean existsByName(String name);
}
