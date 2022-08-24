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
    @Query("select d from PoStore d  JOIN d.itemPoSet r join d.indentDAta item where r.itemId =:item_id and item.indentId=:indentId ORDER BY  d.indentDAta.indentId ASC")
    List<PoStore> findBypo_item_data_(Long item_id,Long indentId );
    @Query(value = "select p from PoStore  p")
    List<PoStore> findByDeleteView();
}
