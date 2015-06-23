package com.opentext.birt;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SessionHandle;

import com.ibm.icu.util.ULocale;
import com.opentext.bdweb.beans.NewDataSetBean;
import com.opentext.bdweb.beans.NewDataSourceBean;
import com.opentext.bdweb.beans.XmlSource;

@ManagedBean
@SessionScoped
public class BirtEngine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private DesignConfig  		  config;
	private IDesignEngine 		  engine;
	private IDesignEngineFactory  factory;
	private ReportDesignHandle    design;
	private SessionHandle 		  session;
	private ElementFactory 		  efactory;
	private DesignElementHandle   element;
	private String				  xmlSource;
	private Map<String,Object>    availableDataSources = new HashMap<String, Object>();
	private Map<String,Object>    availableDataSets    = new HashMap<String, Object>();
	private String                selectedDataSource;
	private String				  selectedDataSet;
	
	public BirtEngine() {
		try {
			// Init selectedDataSource
			availableDataSources.put("Please create a data source", "Please create a data source");
			availableDataSets.put("Please create a data set", "Please create a data set");
			
			// Design API Configuration
			config  = new DesignConfig();
			
			// Set BIRT's Home
			
			// Start the BIRT Engine
			Platform.startup(config);
			
			// Initialize the factory
			factory = (IDesignEngineFactory) Platform.createFactoryObject(IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY);
			
			// Create the design engine
			engine = factory.createDesignEngine(config);
			
			// Create a design session
			session = engine.newSessionHandle(ULocale.ENGLISH);
			design = session.createDesign( );
			efactory = design.getElementFactory( );
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void createNewDesign(String rptdesign) {
		createMasterPage("Master Page");
		saveAs(rptdesign);
		open(rptdesign);
	}
	
	public void save() {
		try {
			design.save();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String getSource() {
		return xmlSource;
	}
	
	public void createDataSource() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			XmlSource  xmlSource  = (XmlSource)  facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "xmlSource");
			NewDataSourceBean   dataSourceBean   = (NewDataSourceBean)  facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "newDataSourceBean");
	        OdaDataSourceHandle dataSourceHandle = efactory.newOdaDataSource(dataSourceBean.getDriverName(), dataSourceBean.getDriverClass());

	        dataSourceHandle.setProperty("odaDriverClass", dataSourceBean.getDriverClass());
	        dataSourceHandle.setProperty("odaURL", 			dataSourceBean.getUrl());
	        dataSourceHandle.setProperty("odaUser", dataSourceBean.getUsername());
	        dataSourceHandle.setProperty("odaPassword", dataSourceBean.getPassword());        
	        design.getDataSources().add(dataSourceHandle);

	        saveAs("Temp.rptdesign");
	        open("Temp.rptdesign");
	        xmlSource.setXmlSource(this.xmlSource);
	        
	        // Clear the bean
	        dataSourceBean.clearAll();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public Map<String, Object> getAvailableDataSources() {
		availableDataSources.clear();
		
		for(int i=0; i<design.getDataSources().getCount(); i++) {
			availableDataSources.put(design.getDataSources().get(i).getName(), design.getDataSources().get(i).getName());
		}
		
		return availableDataSources;
	}

	public Map<String, Object> getAvailableDataSets() {
		availableDataSets.clear();
		
		for(int i=0; i<design.getDataSets().getCount(); i++) {
			availableDataSets.put(design.getDataSets().get(i).getName(), design.getDataSets().get(i).getName());
		}
		
		return availableDataSets;
	}
	
	public void setSelectedDataSource(String selectedDataSource) {
		this.selectedDataSource = selectedDataSource;
	}
	
	public void setSelectedDataSet(String selectedDataSet) {
		this.selectedDataSet = selectedDataSet;
	}
	
	public String getSelectedDataSource() {
		return selectedDataSource;
	}
	
	public String getSelectedDataSet() {
		return selectedDataSet;
	}
	
	public String getXmlSource() {
		return xmlSource;
	}
	
	public void strDiff(String hear, String dear){
	    String[] hr = dear.split("\n");
	    for (String h : hr) {
	        if (!hear.contains(h)) {
	            System.err.println(h);
	        }
	    }
	}
	
	public void deleteDataSource() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			XmlSource  xmlSource  = (XmlSource)  facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "xmlSource");
			
			for(int i=0; i<design.getDataSources().getCount(); i++) {				
				if(selectedDataSource.equals(design.getDataSources().get(i).getName())) {
					design.getDataSources().get(i).drop();
				}
			}
			saveAs("Temp.rptdesign");
	        open("Temp.rptdesign");
	        xmlSource.setXmlSource(this.xmlSource);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void deleteDataSet() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			XmlSource  xmlSource  = (XmlSource)  facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "xmlSource");
			
			for(int i=0; i<design.getDataSets().getCount(); i++) {				
				if(selectedDataSet.equals(design.getDataSets().get(i).getName())) {
					design.getDataSets().get(i).drop();
				}
			}
			saveAs("Temp.rptdesign");
	        open("Temp.rptdesign");
	        xmlSource.setXmlSource(this.xmlSource);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void createDataSet() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			XmlSource       xmlSource  = (XmlSource)       facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "xmlSource");
			NewDataSetBean  dataSet    = (NewDataSetBean)  facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "newDataSetBean");
			
	        OdaDataSetHandle dataSetHandle = efactory.newOdaDataSet(dataSet.getDataSetName(), "org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet");
	        dataSetHandle.setDataSource(selectedDataSource);
	        dataSetHandle.setQueryText(dataSet.getQuery());
	        design.getDataSets().add(dataSetHandle);
	        saveAs("Temp.rptdesign");
	        open("Temp.rptdesign");
	        xmlSource.setXmlSource(this.xmlSource);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void saveAs(String rptdesign) {
		try {
			design.saveAs(rptdesign);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void open(String rptdesign) {
		try {
			String content = new Scanner(new File(rptdesign)).useDelimiter("\\Z").next();
			this.xmlSource = content;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void createMasterPage(String masterPage) {
		try {
			element = efactory.newSimpleMasterPage(masterPage);
			design.getMasterPages( ).add( element );
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}