package com.github.mkapiczy.oauth_server.endpoint;

import com.github.mkapiczy.oauth_server.entity.db.Code;
import com.github.mkapiczy.oauth_server.entity.db.RegisteredApp;
import com.github.mkapiczy.oauth_server.entity.dto.ResourceResponse;
import com.github.mkapiczy.oauth_server.entity.dto.TokenResponse;
import com.github.mkapiczy.oauth_server.repository.CodeRepository;
import com.github.mkapiczy.oauth_server.repository.RegisteredAppRepository;
import com.github.mkapiczy.oauth_server.service.CodeService;
import com.github.mkapiczy.oauth_server.service.RandomCodeGeneratorService;
import com.github.mkapiczy.oauth_server.service.RegisteredAppService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private RegisteredAppRepository appsRepository;

    @Autowired
    private RegisteredAppService appService;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CodeService codeService;

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

        String requestLogin = request.getParameter("login");
        String requestPassword = request.getParameter("password");

        String redirectUri = (String) request.getSession().getAttribute("redirectUri");
        String appId = (String) request.getSession().getAttribute("appId");

        RegisteredApp app = appService.findAppByAppId(appId);

        if (app.getLogin().equals(requestLogin) && app.getPassword().equals(requestPassword)) {
            Code authorizationCode = codeService.createNewAuthorizationCode();
            app.setAuthorizationCode(authorizationCode);
            appsRepository.save(app);
            redirectUri += "?code=" + authorizationCode.getCode();
            return new ModelAndView("redirect:" + redirectUri);
        } else {
            ModelAndView modelAndView = new ModelAndView("authenticate");
            modelAndView.getModelMap().addAttribute("error", "Incorrect login data!");
            return modelAndView;
        }

    }

    @RequestMapping(path = "/access_token", method = RequestMethod.GET)
    public void generateAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String requestAuthorizationCode = request.getParameter("code");
        String requestAppId = request.getParameter("client_id");

        RegisteredApp app = appService.findAppByAppId(requestAppId);

        if (codeService.isAuthorizationCodeValid(app.getAuthorizationCode(), requestAuthorizationCode)) {
            Code accessToken = codeService.createNewAccessToken();
            Code refreshToken = codeService.createNewRefreshToken();

            app.setAccessToken(accessToken);
            app.setRefreshToken(refreshToken);
            appsRepository.save(app);
            Gson gson = new Gson();
            String jsonObject = gson.toJson(new TokenResponse(accessToken.getCode(), refreshToken.getCode()));
            response.setContentType("application/json");
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.print(jsonObject);
            out.flush();
        } else {
            throw new RuntimeException("Authorization code not valid!");
        }

    }

    @RequestMapping(path = "/resource", method = RequestMethod.GET)
    public void handleResourceRequest(HttpServletRequest request, HttpServletResponse response) {
        String requestAccessToken = request.getParameter("access_token");
        List<Code> accessTokens = codeRepository.findByCode(requestAccessToken);

        if (accessTokens != null && !accessTokens.isEmpty()) {
            Code accessToken = accessTokens.get(0);
            List<RegisteredApp> registeredApps = appsRepository.findByAccessToken(accessToken);
            if (registeredApps != null && !registeredApps.isEmpty()) {
                RegisteredApp app = registeredApps.get(0);
                Gson gson = new Gson();
                String jsonObject = gson.toJson(new ResourceResponse(app.getAppOwner()));
                response.setContentType("application/json");
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.print(jsonObject);
                out.flush();
            } else {
                throw new RuntimeException("No app for this accessToken");
            }
        } else {
            throw new RuntimeException("No code for request accessToken found");
        }
    }


}

