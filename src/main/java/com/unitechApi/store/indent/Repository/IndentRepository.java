package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IndentRepository extends JpaRepository<Indent,Long> , JpaSpecificationExecutor<Indent> {
    List<Indent> findByIndentStatus(IndentStatus indentStatus);
    List<Indent> findByCreated(Date date);
    @Query("select i from Indent  i where    DATE(i.created) between :start and :end ")
    Page<List<Indent>> ffindByDateBEtween(@Param("start") Date start,@Param("end")  Date end,Pageable pageable );
    @Query("select  i from  Indent  i where  i.indentNumber=: indenetNumber")
    List<?> findByIndentNumber(String indenetNumber);

    @Query("select i from Indent  i where    DATE(i.created) between :start and :end AND i.indentNumber LIKE %:indentNumber%")
    Page<List<Indent>> ffindByDateBEtweenWithindentNumber(@Param("start") Date start,@Param("end")  Date end,Pageable pageable,String indentNumber );
    Page<Indent> findAll(Pageable pageable);


    @Query("select  i from Indent i where lower(i.createdDate) like  lower(concat('%',:keyword,'%') ) or lower(i.indentStatus) like  lower(concat('%',:keyword,'%') ) ")
    Page<Indent> searchInAllIndent(String keyword,Pageable pageable);

  //  List<Indent> findByVendorDetailsIdAndAndIndentId(Long vendorId, Long indentId);

}
