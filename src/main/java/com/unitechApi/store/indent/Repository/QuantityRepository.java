package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuantityRepository extends JpaRepository<IndentQuantity ,Long> {
    @Query("select i FROM IndentQuantity i where i.indentqua.indentId=:indentId")
    List<IndentQuantity> findAllByIndentid(Long indentId);
}
