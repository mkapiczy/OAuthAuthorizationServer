package com.github.britter.springbootherokudemo.repository;

import com.github.britter.springbootherokudemo.entity.RegisteredApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisteredAppRepository extends JpaRepository<RegisteredApp, Long> {

    List<RegisteredApp> findByAppId(String appId);
}
