package com.unitechApi.user.Repository;

import com.unitechApi.user.model.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassWordRepository extends JpaRepository<PasswordEntity ,Long> {
}
