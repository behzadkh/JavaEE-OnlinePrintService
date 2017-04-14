package ir.org.acm.exam.khosrojerdi.webbean;

import java.io.Serializable;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@SessionScoped
@Named
public class NavigationUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String PATH = "/pages/";
    private static final String ADMIN_PATH = PATH+"Admin/admin";
    private static final String USER_PATH = PATH + "users/user";
    private String page = null;
    private static NavigationUtil navigationUtil= null;

    
    public NavigationUtil()
    {
        navigationUtil = this;
    }
    
    public String navigateAction() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        //System.out.println("page:" + params.get("page"));
        this.page = PATH + params.get("page") + ".xhtml";
        return null;
    }
    
    public String navigateAction(String page) {
        
        
        this.page = PATH + page + ".xhtml";
        return null;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public static NavigationUtil getNavigationUtil() {
        return navigationUtil;
    }

    public static void setNavigationUtil(NavigationUtil navigationUtil) {
        NavigationUtil.navigationUtil = navigationUtil;
    }

    public static String getPATH() {
        return PATH;
    } 

    public static String getADMIN_PATH() {
        return ADMIN_PATH;
    }

    public static String getUSER_PATH() {
        return USER_PATH;
    }
    
    
}
