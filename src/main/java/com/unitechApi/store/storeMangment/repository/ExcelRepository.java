package com.unitechApi.store.storeMangment.repository;

import com.unitechApi.store.storeMangment.Model.ExcelItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelRepository extends JpaRepository<ExcelItem ,Long> {
}
