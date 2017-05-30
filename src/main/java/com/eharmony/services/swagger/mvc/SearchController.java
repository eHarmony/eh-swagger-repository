package com.eharmony.services.swagger.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class SearchController {

    /**
     * Renders swagger services search page.
     */
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView getSearchListings() {
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put("title", "Search");
        attributes.put("searchNavigation", "active");
        return new ModelAndView("search", "parameters", attributes);
    }

}
