package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.IndentQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface QuantityRepository extends JpaRepository<IndentQuantity, Long> {
    @Query("select i FROM IndentQuantity i where i.indentItemQuantity.indentId=:indentId ")
    List<IndentQuantity> findAllByIndentid(Long indentId);

    @Query(value = "SELECT store_management.po_item_data.po_id ,store_management.personal_order.ind_id, store_management.indent_quantity.indent_id, store_management.indent_quantity.item_id,indent_quantity.estimatedprice FROM store_management.po_item_data INNER JOIN store_management.personal_order ON personal_order.poid = po_item_data.po_id INNER JOIN store_management.indent_quantity ON indent_quantity.indent_id = personal_order.ind_id WHERE store_management.po_item_data.item_id=?1 and store_management.indent_quantity.item_id=?2", nativeQuery = true)
    Map<String, String> findByData(Long itemId, Long indentitemId);

    @Query("select i FROM IndentQuantity i where i.indentItemQuantity.indentId=:indentId and i.quantityId=:quantityId")
    List<IndentQuantity> findByIndentID(Long indentId, Long quantityId);
    @Query("select i FROM IndentQuantity i where i.indentItemQuantity.indentId=:indentId and i.storeItemIndentQuantityData.itemId=:quantityId")
    List<IndentQuantity> findByIndentIdAndItemId(Long indentId, Long quantityId);
}
