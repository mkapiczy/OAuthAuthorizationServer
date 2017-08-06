package com.github.mkapiczy.oauth_server.repository;

import com.github.mkapiczy.oauth_server.entity.db.Code;
import com.github.mkapiczy.oauth_server.entity.db.RegisteredApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisteredAppRepository extends JpaRepository<RegisteredApp, Long> {

    List<RegisteredApp> findByAppId(String appId);
    List<RegisteredApp> findByAccessToken(Code accessToken);


}
