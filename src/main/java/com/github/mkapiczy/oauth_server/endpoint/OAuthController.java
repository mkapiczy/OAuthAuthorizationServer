package com.github.mkapiczy.oauth_server.endpoint;

import com.github.mkapiczy.oauth_server.entity.Code;
import com.github.mkapiczy.oauth_server.entity.RegisteredApp;
import com.github.mkapiczy.oauth_server.repository.CodeRepository;
import com.github.mkapiczy.oauth_server.repository.RegisteredAppRepository;
import com.github.mkapiczy.oauth_server.service.RandomCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private RegisteredAppRepository appsRepository;
    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private RandomCodeGeneratorService randomCodeGeneratorService;


    @RequestMapping(path = "/authenticate", method = RequestMethod.GET)
    public ModelAndView authentionView(HttpServletRequest request, HttpServletResponse response) {
        String redirectUri = request.getParameter("redirect_uri");
        String appId = request.getParameter("client_id");

        List<RegisteredApp> apps = appsRepository.findByAppId(appId);
        if (apps.isEmpty()) {
            throw new RuntimeException("No app for giver client_id found");
        } else {
            request.getSession().setAttribute("redirectUri", redirectUri);
            request.getSession().setAttribute("appId", appId);
            return new ModelAndView("authenticate");

        }
    }

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public ModelAndView authenticate(HttpServletRequest request, HttpServletResponse response) {
        String redirectUri = (String) request.getSession().getAttribute("redirectUri");
        String appId = (String) request.getSession().getAttribute("appId");


        List<RegisteredApp> apps = appsRepository.findByAppId(appId);
        if (apps.isEmpty()) {
            throw new RuntimeException("No app for giver client_id found");
        } else {
            RegisteredApp app = apps.get(0);
            String code = randomCodeGeneratorService.generateRandom32SignCode();
            Code authorizationCode = new Code();
            authorizationCode.setCode(code);
            authorizationCode.setGenerationDate(new Date());
            authorizationCode.setValid(true);
            app.setAuthorizationCode(authorizationCode);
            codeRepository.save(authorizationCode);
            appsRepository.save(app);
            redirectUri += "?code=" + code;
            return new ModelAndView("redirect:" + redirectUri);
        }

    }


    @RequestMapping(path = "/access", method = RequestMethod.GET)
    public void generateAccessToken(HttpServletRequest request, HttpServletResponse response) {
        request.getParameter("code");
        request.getParameter("clientId");

        // validate code and clientId

        String accessToken = randomCodeGeneratorService.generateRandom32SignCode();
        String refreshToken = randomCodeGeneratorService.generateRandom32SignCode();


        String jsonObject = ""; // generate json obejct with accessToken and refreshToken
        response.setContentType("application/json");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(jsonObject);
        out.flush();

    }


}

