package com.unitechApi.purchase.RawMaterial.sampling.samplingRepository;

import com.unitechApi.purchase.RawMaterial.sampling.samplingModel.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<SampleEntity ,Long> {
}
