package com.opentext.bdweb.beans;

import java.io.Serializable;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FlowEvent;


@ManagedBean
@SessionScoped
public class NewDataSourceBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String driverClass = "";
    private String driverName  = "";
    private String username    = "";
    private String password    = "";
    private String url         = "";
    
    private boolean skip;
    
    public void clearAll() {
    	driverClass = "";
    	driverName  = "";
    	username    = "";
    	password    = "";
    	url         = "";
    }
    
    public void setDriverClass(String driverClass) {
    	this.driverClass = driverClass;
    }
    
    public String getDriverClass() {
    	return this.driverClass;
    }
    
    public void setDriverName(String driverName) {
    	this.driverName = driverName;
    }
    
    public String getDriverName() {
    	return driverName;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public void setUrl(String url) {
    	this.url = url;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public String getUrl() {
    	return url;
    }
    
    public boolean isSkip() {
        return skip;
    }
 
    public void setSkip(boolean skip) {
        this.skip = skip;
    }
    
    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }
}
