package com.unitechApi.addmachine.repositroy;

import com.unitechApi.addmachine.model.AddWasteRoomeMAchine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddWasteRoomRepository extends JpaRepository<AddWasteRoomeMAchine, Long> {
    List<AddWasteRoomeMAchine> findByStatus(boolean status);
}
