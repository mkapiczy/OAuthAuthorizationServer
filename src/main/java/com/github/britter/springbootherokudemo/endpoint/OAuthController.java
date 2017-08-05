package com.github.britter.springbootherokudemo.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/oauth")
public class OAuthController {


    private String appId = "1955772338033020";
    private String appSecret = "3e5e6b3fc275af2d4303391ab7a03e58";
    //    String appDomain = "http://localhost:8080";
    String appDomain = "https://fb-login-flow.herokuapp.com";


    @RequestMapping(path = "/authenticate", method = RequestMethod.GET)
    public ModelAndView authenticationView(HttpServletRequest request, HttpServletResponse response) {
        String redirectUri = request.getParameter("redirect_uri");
        String clientId = request.getParameter("client_id");

        request.getSession().setAttribute("redirectUri", redirectUri);

        return new ModelAndView("authenticate");
    }

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public ModelAndView authenticate(HttpServletRequest request, HttpServletResponse response) {
        String redirectUri = (String) request.getSession().getAttribute("redirectUri");
        String clientId = (String) request.getSession().getAttribute("clientId");

        String code = "123"; // generate code

        // remember code clientId association


        redirectUri += "?code=" + code;
        return new ModelAndView("redirect:" + redirectUri);
    }


    @RequestMapping(path = "/access", method = RequestMethod.GET)
    public void generateAccessToken(HttpServletRequest request, HttpServletResponse response) {
        request.getParameter("code");
        request.getParameter("clientId");

        // validate code and clientId

        String accessToken = "123"; // generate accessToken
        String refreshToken = "123"; // generate refresh token


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

