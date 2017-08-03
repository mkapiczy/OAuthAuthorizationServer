/*
 * Copyright 2015 Benedikt Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.britter.springbootherokudemo;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@Controller
@RequestMapping("/")
public class FacebookController {

    private FacebookRepository repository;
    private String appId = "1955772338033020";
    private String appSecret = "3e5e6b3fc275af2d4303391ab7a03e58";
//    String appDomain = "http://localhost:8080";
    String appDomain = "https://fb-login-flow.herokuapp.com";
    private String firstRedirectUri = appDomain + "/facebook/firstRedirect";
    private String fbUrl = "https://www.facebook.com/v2.10/dialog/oauth?client_id=%s&redirect_uri=%s";

    @Autowired
    public FacebookController(FacebookRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        Long loggedIn = (Long) request.getSession().getAttribute("loggedIn");
        if (loggedIn != null) {
//            FaceBookPojo fbp = repository.getOne(loggedIn);

            System.out.println(request.getSession().getAttribute("loggedInName"));
            ModelAndView mav = new ModelAndView("loggedIn");
            mav.addObject("fb", request.getSession().getAttribute("loggedInName"));
            return mav;
        } else {
            return new ModelAndView("home");
        }
    }

    @RequestMapping(path = "/facebook/login", method = RequestMethod.POST)
    public ModelAndView insertData(HttpServletRequest request, HttpServletResponse response) {
        String facebookLoginUrl = String.format(fbUrl, appId, firstRedirectUri);
        System.out.println(facebookLoginUrl);
        return new ModelAndView("redirect:" + facebookLoginUrl);
    }

    @RequestMapping(path = "/facebook/logout", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("loggedIn");
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(path = "/facebook/firstRedirect", method = RequestMethod.GET)
    public ModelAndView firstRedirect(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("First redirect");
        String code = request.getParameter("code");
        System.out.println("Code: " + code);
        try {
            URL url = new URL("https://graph.facebook.com/oauth/access_token?client_id="
                    + appId + "&redirect_uri=" + firstRedirectUri
                    + "&client_secret=" + appSecret
                    + "&code=" + code);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setRequestMethod("GET");
            String line, outputString = "";
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            System.out.println(outputString);

            // extract access token from response
            String accessToken = null;
            if (outputString.indexOf("access_token") != -1) {
                accessToken = outputString.substring(16, outputString.indexOf(","));
                System.out.println(accessToken);
            }


            accessToken = accessToken.replace("\"", "");

            // request for user info
            url = new URL("https://graph.facebook.com/me?access_token="
                    + accessToken);
            System.out.println(url);
            URLConnection conn1 = url.openConnection();
            outputString = "";
            reader = new BufferedReader(new InputStreamReader(
                    conn1.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            reader.close();
            System.out.println(outputString);

            // convert response JSON to Pojo class
            FaceBookPojo fbp = new Gson().fromJson(outputString,
                    FaceBookPojo.class);
            System.out.println(fbp);
//            fbp = repository.save(fbp);
            request.getSession().setAttribute("loggedIn", fbp.getId());
            request.getSession().setAttribute("loggedInName", fbp.getName());
            return new ModelAndView("redirect:/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/");
    }
}
