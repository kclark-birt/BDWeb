package com.opentext.birt.engine;

import java.io.File;
import java.io.Serializable;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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

@ManagedBean
@SessionScoped
public class BirtEngine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private DesignConfig  		 config;
	private IDesignEngine 		 engine;
	private IDesignEngineFactory factory;
	private ReportDesignHandle   design;
	private SessionHandle 		 session;
	private ElementFactory 		 efactory;
	private DesignElementHandle  element;
	private String				 xmlSource;
	
	public BirtEngine() {
		try {
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
	        OdaDataSourceHandle dataSourceHandle = efactory.newOdaDataSource("Classic Models Data Source", "org.eclipse.birt.report.data.oda.jdbc");
	        dataSourceHandle.setProperty("odaDriverClass", "org.eclipse.birt.report.data.oda.sampledb.Driver");
	        dataSourceHandle.setProperty("odaURL", "jdbc:classicmodels:sampledb");
	        dataSourceHandle.setProperty("odaUser", "ClassicModels");
	        dataSourceHandle.setProperty("odaPassword", "");        
	        design.getDataSources().add(dataSourceHandle);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void createDataSet() {
		try {
	        OdaDataSetHandle dataSetHandle = efactory.newOdaDataSet("Classic Models Data Set", "org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet");
	        dataSetHandle.setDataSource("Classic Models Data Source");
	        dataSetHandle.setQueryText("SELECT * FROM CLASSICMODELS.ORDERS");
	        design.getDataSets().add(dataSetHandle);
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
