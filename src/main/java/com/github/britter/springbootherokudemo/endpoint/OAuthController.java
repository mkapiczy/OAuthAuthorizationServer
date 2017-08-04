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
@RequestMapping("/oauth")
public class OAuthController {

    private FacebookRepository repository;
    private String appId = "1955772338033020";
    private String appSecret = "3e5e6b3fc275af2d4303391ab7a03e58";
    //    String appDomain = "http://localhost:8080";
    String appDomain = "https://fb-login-flow.herokuapp.com";

    @Autowired
    public OAuthController(FacebookRepository repository) {
        this.repository = repository;
    }


    @RequestMapping(path = "/authenticate", method = RequestMethod.GET)
    public ModelAndView authenticationView(HttpServletRequest request, HttpServletResponse response) {
        String redirectUri = request.getParameter("redirect_uri");
        request.getSession().setAttribute("redirectUri", redirectUri);
        return new ModelAndView("authenticate");
    }

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public ModelAndView authenticate(HttpServletRequest request, HttpServletResponse response) {
        String code = "123"; // generate code
        String redirectUri = (String) request.getSession().getAttribute("redirectUri");
        redirectUri += "?code=" + code;
        return new ModelAndView("redirect:" + redirectUri);
    }
}
