package com.opentext.bdweb.beans;

import java.io.Serializable;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.opentext.birt.engine.BirtEngine;
import com.opentext.birt.rest.Authentication;

@ManagedBean
@SessionScoped
public class XmlSource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String xmlSource = null;
	private BirtEngine birtEngine = new BirtEngine();
	
	public XmlSource() {
		System.out.println("Constructing!!!!!!!");
		
		if(xmlSource == null) {
			birtEngine.createNewDesign("Temp.rptdesign");
			this.xmlSource = birtEngine.getSource();
		}
		
		System.out.println("END!!!!!!!!!!!!!!!!");
	}
	
	public String getXmlSource() {		
		return xmlSource;
	}
	
	public void setXmlSource(String xmlSource) {
		
		this.xmlSource = xmlSource;
	}
}
