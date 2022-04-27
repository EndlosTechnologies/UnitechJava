package com.unitechApi.purchase.RawMaterial.Contract.Repository;

import com.unitechApi.purchase.RawMaterial.Contract.Model.PartyLotModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyLotRepository extends JpaRepository<PartyLotModel ,Long> {
}
