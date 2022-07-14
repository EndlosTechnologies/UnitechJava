package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.IndentQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<IndentQuantity ,Long> {
}
