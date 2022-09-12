package com.unitechApi.store.po.Repository;

import com.unitechApi.store.po.Model.PoStore;
import com.unitechApi.store.po.view.PoByIndentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface PoStoreRepository extends JpaRepository<PoStore ,Long> , JpaSpecificationExecutor<PoStore> {
    @Modifying
    @Transactional
    @Query(value = "update PoStore p set  p.deleteView=:deleteView where p.poId=:poId")
    void changeStattus(boolean deleteView, Long poId);
//    @Query("select d from PoStore d  JOIN d.itemPoSet r join d.indentDAta item where r.itemId =:item_id and item.indentId=:indentId ORDER BY  d.indentDAta.indentId ASC")
//    List<PoStore> findBypo_item_data_(Long item_id,Long indentId );
    @Query(value = "select p from PoStore  p")
    List<PoStore> findByDeleteView();
    @Query(value = "select p from PoStore  p where p.poNumber=:poNumber")
    List<?>  findByPosNumber(String poNumber);
    @Query(value = "select p from PoStore  p where p.utrNumber=:utrNumber")
    List<?> findByUtrNumber(String utrNumber);
    @Query(value = "SELECT p from PoStore p where  p.indentDAta.indentId =:indentId")
     List<?> findByIndentLis(Long indentId);

    @Query(value = "select indent.indentnumber,indent.indentid ,indent.indentstatus,indent.comment,\n" +
            "personal_order.amount ,personal_order.ind_id,personal_order.poname,personal_order.ponumber,personal_order.poid\n" +
            "from store_management.personal_order  JOIN store_management.indent on personal_order.ind_id = indent.indentid \n" +
            "WHERE personal_order.ind_id = ?1",nativeQuery = true)
     List<Map<?,?>> getByIndentId(Long indent);
    @Query(value = "select new com.unitechApi.store.po.view.PoByIndentView (i.indentId,i.indentNumber,p.amount,p.poNumber,p.poName ,i.indentStatus,i.comment,p.poId) " +
            "from PoStore p JOIN Indent i on p.indentDAta.indentId = i.indentId  where i.indentId=:indent")
    List<PoByIndentView> getIndentId(Long indent);

}
