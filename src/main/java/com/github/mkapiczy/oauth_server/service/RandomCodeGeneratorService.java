package com.github.mkapiczy.oauth_server.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
public class RandomCodeGeneratorService {

    private SecureRandom random = new SecureRandom();

    public String generateRandom32SignCode(){
        return new BigInteger(130, random).toString(32);
    }

}
