package com.opentext.bdweb.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FlowEvent;

@ManagedBean
@SessionScoped
public class NewDataSetBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String query;
    private String dataSetName;
	private boolean skip;
	
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}
	
	public String getDataSetName() {
		return dataSetName;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void clearAll() {
		query = "";
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
