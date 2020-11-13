package com.urcodebin.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomRequestCache extends HttpSessionRequestCache {

    /*
    * Cache the original page that was being routed to, so that the user is
    * routed to that page after authentication is compelted.
     */
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if(!SecurityUtils.isFrameworkInternalRequest(request)) {
            super.saveRequest(request, response);
        }
    }
}
