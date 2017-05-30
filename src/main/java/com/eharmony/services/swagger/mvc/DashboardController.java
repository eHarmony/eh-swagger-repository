package com.eharmony.services.swagger.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class DashboardController {

    /**
     * Home page same as dashboards page.
     */
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView getHome() {
        return getDashboards();
    }

    /**
     * Renders dashboard listing page.
     */
    @RequestMapping(path = "/dashboards", method = RequestMethod.GET)
    public ModelAndView getDashboards() {
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put("title", "Dashboards");
        attributes.put("dashboardNavigation", "active");
        return new ModelAndView("dashboards", "parameters", attributes);
    }

    /**
     * Renders dashboard edit page.
     */
    @RequestMapping(path = "/dashboards/edit", method = RequestMethod.GET)
    public ModelAndView editDashboard() {
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put("title", "Edit Dashboard");
        attributes.put("dashboardNavigation", "active");
        return new ModelAndView("dashboardEdit", "parameters", attributes);
    }

    /**
     * Renders dashboard view page.
     */
    @RequestMapping(path = "/dashboards/view", method = RequestMethod.GET)
    public ModelAndView viewDashboard() {
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put("title", "View Dashboard");
        attributes.put("dashboardNavigation", "active");
        return new ModelAndView("dashboardView", "parameters", attributes);
    }

    /**
     * Renders my dashboard page.
     */
    @RequestMapping(path = "/dashboards/my", method = RequestMethod.GET)
    public ModelAndView myDashboard() {
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put("title", "View Dashboard");
        attributes.put("myDashboardNavigation", "active");
        return new ModelAndView("myDashboard", "parameters", attributes);
    }

    /**
     * Redirects old dashboards page.
     */
    @RequestMapping(path = "/dashboards.html", method = RequestMethod.GET)
    public ModelAndView redirectDashboards() {
        RedirectView redirect = new RedirectView("/dashboards");
        redirect.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return new ModelAndView(redirect);
    }

    /**
     * Redirects old home page.
     */
    @RequestMapping(path = "/index.html", method = RequestMethod.GET)
    public ModelAndView redirectHomePage() {
        RedirectView redirect = new RedirectView("/dashboards");
        redirect.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return new ModelAndView(redirect);
    }

    /**
     * Redirects old dashboard view page.
     */
    @RequestMapping(path = "/dashboardview.html", method = RequestMethod.GET)
    public ModelAndView redirectDashboardView(HttpServletRequest request) {
        RedirectView redirect = new RedirectView("/dashboards/view?dashboardId=" + request.getParameter("dashboardId"));
        redirect.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return new ModelAndView(redirect);
    }

}
