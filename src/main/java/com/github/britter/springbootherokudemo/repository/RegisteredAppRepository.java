package com.github.britter.springbootherokudemo.repository;

import com.github.britter.springbootherokudemo.entity.RegisteredApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredAppRepository extends JpaRepository<RegisteredApp, Long> {
}
