package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddSimplexMAchine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddSimplexRepository extends JpaRepository<AddSimplexMAchine, Long> {
    List<AddSimplexMAchine> findByStatus(boolean status);
}
