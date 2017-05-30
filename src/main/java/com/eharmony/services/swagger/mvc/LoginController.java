package com.eharmony.services.swagger.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    /**
     * Loads login form.
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView loadLogin(HttpServletRequest request) {
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put("title", "Login");
        attributes.put("flashClass", "hidden");

        if (request.getParameter("logout") != null) {
            attributes.put("flashClass", "alert-info");
            attributes.put("flash", "You have been logged out.");
        }

        if (request.getParameter("error") != null) {
            attributes.put("flashClass", "alert-danger");
            attributes.put("flash", "Invalid username or password.");
        }

        return new ModelAndView("login", "parameters", attributes);
    }
}
