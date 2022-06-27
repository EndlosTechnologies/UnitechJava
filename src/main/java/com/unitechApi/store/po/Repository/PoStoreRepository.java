package com.unitechApi.store.po.Repository;

import com.unitechApi.store.po.Model.PoStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PoStoreRepository extends JpaRepository<PoStore ,Long> {
    @Query(value = "update PoStore p set  p.deleteView=:status")
    void changeStattus(Boolean status);
}
