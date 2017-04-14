/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author behzad
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/pages/Admin/*"})
public class AdminFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AdminFilter.class.getName());
    
    public AdminFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            //check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            
            if (ses != null && ses.getAttribute("username") != null) {
                
                Boolean isAdmin = (Boolean) ses.getAttribute("isAdmin");
                if (isAdmin != null) {
                    if (isAdmin) {
                        
                        chain.doFilter(request, response);
                    } else {
                        
                        res.sendRedirect(req.getContextPath() + "/pages/users/user.xhtml");
                    }
                } else {
                    
                    res.sendRedirect(req.getContextPath() + "/");
                }

            } else {
                
                res.sendRedirect(req.getContextPath() + "/");
            }
        } catch (IOException t) {
            logger.log(Level.SEVERE,t.getMessage());
        }
    }

    @Override
    public void destroy() {
    }

}
