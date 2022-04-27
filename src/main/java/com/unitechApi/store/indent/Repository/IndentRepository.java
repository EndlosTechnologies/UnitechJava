package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IndentRepository extends JpaRepository<Indent,Long> {
    List<Indent> findByIndentStatus(IndentStatus indentStatus);
    List<Indent> findByCreated(Date date);

}
