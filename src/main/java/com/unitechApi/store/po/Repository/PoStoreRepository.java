package com.unitechApi.store.po.Repository;

import com.unitechApi.store.po.Model.PoStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PoStoreRepository extends JpaRepository<PoStore ,Long> {
    @Modifying
    @Transactional
    @Query(value = "update PoStore p set  p.deleteView=:deleteView where p.poId=:poId")
    void changeStattus(boolean deleteView, Long poId);
    @Query(value = "select d from PoStore d  JOIN d.itemPoSet r  where r.itemId =:item_id and d.poId=:poId")
    PoStore findBypo_item_data_(Long item_id,Long poId);
    @Query(value = "select p from PoStore  p")
    List<PoStore> findByDeleteView();
}
