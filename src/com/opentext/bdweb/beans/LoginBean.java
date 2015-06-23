package com.opentext.bdweb.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.opentext.bdweb.ihub.Rest;
 
 
/**
 * Simple login bean.
 *
 * @author itcuties
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = Logger.getLogger(LoginBean.class);
    
    private String username = "";
    private String password = "";
    private String authId   = "";
    private int    failedAttempts = 0; 
    private boolean loggedIn;
 
    @ManagedProperty(value="#{navigationBean}")
    private NavigationBean navigationBean;
     
    /**
     * Login operation.
     * @return
     */
    public String doLogin() {
    	Rest login = new Rest();
    	
    	if(login.authenticate(username, password)) {
    		loggedIn = true;
    		
    		LOGGER.info(username + " successfully logged in");
    		failedAttempts = 0;
    		
    		return navigationBean.redirectToWelcome();
    	}
    	
        // Set login ERROR
        FacesMessage msg = new FacesMessage("Login error!", "Incorrect username or password");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        failedAttempts++;
        LOGGER.warn(username + " failed login. Attempt #" + failedAttempts);
        
        // To to login page
        return navigationBean.toLogin();
    }
     
    /**
     * Logout operation.
     * @return
     */
    public String doLogout() {
        // Set the paremeter indicating that user is logged in to false
        loggedIn = false;
        authId   = "";
        
        // Set logout message
        FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        LOGGER.info(username + " successfully logged out");
        
        return navigationBean.toLogin();
    }
 
    // ------------------------------
    // Getters & Setters
     
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getAuthId() {
    	return authId;
    }
    
    public void setAuthId(String authId) {
    	this.authId = authId;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
 
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
 
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }
     
}