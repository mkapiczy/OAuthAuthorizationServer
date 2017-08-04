package com.github.britter.springbootherokudemo.endpoint;

import com.github.britter.springbootherokudemo.FacebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class HomeController {

    private FacebookRepository repository;

    @Autowired
    public HomeController(FacebookRepository repository) {
        this.repository = repository;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        Long loggedIn = (Long) request.getSession().getAttribute("loggedIn");
        if (loggedIn != null) {
//            FaceBookPojo  fbp = repository.getOne(loggedIn);

            System.out.println(request.getSession().getAttribute("loggedInName"));
            ModelAndView mav = new ModelAndView("loggedIn");
            mav.addObject("fb", request.getSession().getAttribute("loggedInName"));
            return mav;
        } else {
            return new ModelAndView("home");
        }
    }
}
