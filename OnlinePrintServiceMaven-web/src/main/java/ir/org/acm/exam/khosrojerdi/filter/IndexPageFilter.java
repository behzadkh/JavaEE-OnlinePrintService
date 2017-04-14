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
@WebFilter(filterName = "IndexPageFilter", urlPatterns = {"/index.xhtml"})
public class IndexPageFilter implements Filter {
     
    private static final Logger logger = Logger.getLogger(IndexPageFilter.class.getName());
    
    public  IndexPageFilter (){}
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            //check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            
            if(ses != null && ses.getAttribute("username") != null){
                
                boolean isAdmin = (boolean) ses.getAttribute("isAdmin");
                if(isAdmin){
                    
                    res.sendRedirect(req.getContextPath() + "/pages/Admin/admin.xhtml");
                }
                else{
                    
                    res.sendRedirect(req.getContextPath() + "/pages/users/user.xhtml");
                }
            }
            else{
                
                 chain.doFilter(request, response);
            }
        } catch (IOException | ServletException t) {
            logger.log(Level.SEVERE,t.getMessage());
        }
    }//doFilter

    @Override
    public void destroy() {

    }
    
}
