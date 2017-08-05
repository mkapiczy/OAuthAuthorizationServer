package com.github.britter.springbootherokudemo.endpoint;

import com.github.britter.springbootherokudemo.entity.RegisteredApp;
import com.github.britter.springbootherokudemo.repository.RegisteredAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private RegisteredAppRepository repository;

    private SecureRandom random = new SecureRandom();

    @Autowired
    public HomeController(RegisteredAppRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(ModelMap model) {

        List<RegisteredApp> registeredApps = repository.findAll();
        model.addAttribute("newApp", new RegisteredApp());
        model.addAttribute("registeredApps", registeredApps);
        return "home";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerApp(ModelMap model,
                             @ModelAttribute("newApp") @Valid RegisteredApp newApp,
                             BindingResult result) {
        if (!result.hasErrors()) {
            String appId = new BigInteger(130, random).toString(32);
            String appSecret = new BigInteger(130, random).toString(32);
            newApp.setAppId(appId);
            newApp.setAppSecret(appSecret);
            repository.save(newApp);
        }
        return home(model);
    }
}
