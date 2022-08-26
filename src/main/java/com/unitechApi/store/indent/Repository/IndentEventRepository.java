package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.IndentCreateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndentEventRepository extends JpaRepository<IndentCreateHistory,Long> {
    @Query("SELECT i from IndentCreateHistory  i where i.indentId=:indentId ORDER BY i.indent_history_id DESC ")
     List<IndentCreateHistory> findByIndentId(Long indentId);
    @Query("SELECT  i from  IndentCreateHistory  i where  i.indentNumber=:indentNumber order by i.indent_history_id DESC")
    List<?> findByIndentNumber(String indentNumber);
}
