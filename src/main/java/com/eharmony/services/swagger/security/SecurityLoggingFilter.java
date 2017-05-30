package com.eharmony.services.swagger.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityLoggingFilter extends GenericFilterBean {
    private static final Log SECURITY_LOG = LogFactory.getLog("SECURITY_LOG");
    private static final String LOG_FORMAT = "USER: %s; REQUESTED_URL: %s; PARAMETERS: %s";

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() != null) {
                LdapUserDetailsImpl user = (LdapUserDetailsImpl)authentication.getPrincipal();
                logAccess(user.getUsername(), (HttpServletRequest)servletRequest);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void logAccess(String username, HttpServletRequest request) {
        String url = request.getRequestURI();
        String queryString = request.getQueryString();

        SECURITY_LOG.info(String.format(LOG_FORMAT, username, url, queryString != null ? queryString : ""));
    }
}
