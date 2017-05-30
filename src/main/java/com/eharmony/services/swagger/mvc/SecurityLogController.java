package com.eharmony.services.swagger.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityLogController {

    @RequestMapping(path = "/accessed", method = RequestMethod.GET)
    public ModelAndView loadEmptyPage() {
        return new ModelAndView("accessed");
    }
}
