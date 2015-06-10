package com.opentext.birt.engine;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SessionHandle;

import com.ibm.icu.util.ULocale;

public class BirtEngine {
	private DesignConfig  		 config;
	private IDesignEngine 		 engine;
	private IDesignEngineFactory factory;
	private ReportDesignHandle   design;
	private SessionHandle 		 session;
	private ElementFactory 		 efactory;
	private DesignElementHandle  element;
	private InputStream          is;
	private String				 xmlSource;
	
	public BirtEngine() {
		try {
			// Design API Configuration
			config  = new DesignConfig();
			
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
		
	}
	
	public String getSource() {
		return xmlSource;
	}
	
	public void saveAs(String rptdesign) {
		try {
			design.saveAs(rptdesign);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
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
