package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddBloowroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddBloowRoomRepository extends JpaRepository<AddBloowroom, Long> {
    List<AddBloowroom> findByStatus(boolean status);
}
