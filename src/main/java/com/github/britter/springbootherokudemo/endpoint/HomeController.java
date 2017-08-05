package com.github.britter.springbootherokudemo.endpoint;

import com.github.britter.springbootherokudemo.entity.RegisteredApp;
import com.github.britter.springbootherokudemo.repository.RegisteredAppRepository;
import com.github.britter.springbootherokudemo.service.RandomCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private RegisteredAppRepository repository;

    @Autowired
    private RandomCodeGeneratorService randomCodeGeneratorService;


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
            String appId = randomCodeGeneratorService.generateRandom32SignCode();
            String appSecret = randomCodeGeneratorService.generateRandom32SignCode();
            newApp.setAppId(appId);
            newApp.setAppSecret(appSecret);
            repository.save(newApp);
        }
        return home(model);
    }
}
