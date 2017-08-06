package com.github.mkapiczy.oauth_server.service;

import com.github.mkapiczy.oauth_server.entity.db.RegisteredApp;
import com.github.mkapiczy.oauth_server.repository.RegisteredAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteredAppService {

    @Autowired
    private RegisteredAppRepository appRepository;

    public RegisteredApp findAppByAppId(String appId) {
        List<RegisteredApp> apps = appRepository.findByAppId(appId);
        if (apps.isEmpty()) {
            throw new RuntimeException("No app for given client_id found:" + appId);
        } else {
            return apps.get(0);
        }
    }
}
