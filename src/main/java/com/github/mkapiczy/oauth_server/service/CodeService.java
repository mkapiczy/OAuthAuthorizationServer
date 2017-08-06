package com.github.mkapiczy.oauth_server.service;

import com.github.mkapiczy.oauth_server.entity.db.Code;
import com.github.mkapiczy.oauth_server.entity.CodeType;
import com.github.mkapiczy.oauth_server.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CodeService {
    @Autowired
    private RandomCodeGeneratorService randomCodeGeneratorService;

    @Autowired
    private CodeRepository codeRepository;


    public Code findByCodeValue(String codeValue) {
        List<Code> accessTokens = codeRepository.findByCode(codeValue);

        if (accessTokens != null && !accessTokens.isEmpty()) {
            return accessTokens.get(0);
        } else {
            throw new RuntimeException("No code found for given value");
        }
    }

    public Code createNewAuthorizationCode() {
        String code = randomCodeGeneratorService.generateRandom32SignCode();
        Code authorizationCode = new Code();
        authorizationCode.setCode(code);
        authorizationCode.setCodeType(CodeType.AUTHORIZATION_CODE);
        authorizationCode.setGenerationDate(new Date());
        authorizationCode.setValidTo(DateUtils.addMinutesToCurrentTime(2));
        authorizationCode.setValid(true);
        return codeRepository.save(authorizationCode);
    }

    public Code createNewAccessToken() {
        String code = randomCodeGeneratorService.generateRandom32SignCode();
        Code accessToken = new Code();
        accessToken.setCode(code);
        accessToken.setCodeType(CodeType.ACCESS_TOKEN);
        accessToken.setGenerationDate(new Date());
        accessToken.setValidTo(DateUtils.addMinutesToCurrentTime(3600 * 24 * 10));
        accessToken.setValid(true);
        return codeRepository.save(accessToken);
    }

    public Code createNewRefreshToken() {
        String code = randomCodeGeneratorService.generateRandom32SignCode();
        Code accessToken = new Code();
        accessToken.setCode(code);
        accessToken.setCodeType(CodeType.REFRESH_TOKEN);
        accessToken.setGenerationDate(new Date());
        accessToken.setValidTo(DateUtils.addMinutesToCurrentTime(3600 * 24 * 60));
        accessToken.setValid(true);
        return codeRepository.save(accessToken);
    }

    public boolean isAuthorizationCodeValid(Code authorizationCode, String codeToCompare) {
        String code = authorizationCode.getCode();
        if (code != null && code.equals(codeToCompare) && authorizationCode.getValidTo().after(new Date())) {
            return true;
        }
        return false;

    }
}
