package com.unitechApi.purchase.RawMaterial.Po.Repository;

import com.unitechApi.purchase.RawMaterial.Po.Model.PoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoRepository extends JpaRepository<PoModel ,Long> {
}
