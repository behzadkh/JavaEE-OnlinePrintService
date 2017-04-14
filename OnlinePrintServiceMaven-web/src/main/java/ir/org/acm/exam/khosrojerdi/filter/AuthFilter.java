package ir.org.acm.exam.khosrojerdi.filter;

//package org.acm.khosrojerdi.exam.filters;
//
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
//public class AuthFilter implements Filter {
//
//    public AuthFilter() {
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        try {
//            //check whether session variable is set
//            HttpServletRequest req = (HttpServletRequest) request;
//            HttpServletResponse res = (HttpServletResponse) response;
//            HttpSession ses = req.getSession(false);
//
//            //allow user to proccede if url is login.xhtml or user logged in or user is accessing any page in //public folder
//            String reqURI = req.getRequestURI();
//
//            if (reqURI.indexOf("/index.xhtml") > 0
//                    || (ses != null && ses.getAttribute("username") != null)
//                    // || reqURI.indexOf("/pages/") >= 0 
//                    || reqURI.contains("javax.faces.resource")) {
//                System.out.println("session is validated");
//                Boolean isAdmin = (Boolean) ses.getAttribute("isAdmin");
//                if (isAdmin != null) {
//                    if (isAdmin) {
//                        System.out.println("session is admin");
//                        res.sendRedirect(req.getContextPath() + "/pages/Admin/admin.xhtml");
//                    } else {
//                        System.out.println("session is user");
//                        res.sendRedirect(req.getContextPath() + "/pages/users/user.xhtml");
//                    }
//                } else {
//                    res.sendRedirect(req.getContextPath() + "/index.xhtml");
//                }
//
////                chain.doFilter(request, response);
//            } //user didnot log in but asking for a page that is not 
//            //allowes so take  user to login page 
//            else {
//                //Anonymous user . redirect to login page
//                res.sendRedirect(req.getContextPath() + "/index.xhtml");
//            }
//        } catch (IOException t) {
//            System.out.println(t.getMessage());
//        }
//    }//doFilter
//
//    @Override
//    public void destroy() {
//
//    }
//
//}
