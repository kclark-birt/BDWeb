package com.opentext.bdweb.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.opentext.birt.engine.BirtEngine;

@ManagedBean
@SessionScoped
public class XmlSource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String xmlSource = null;
	private BirtEngine birtEngine = new BirtEngine();
	
	public XmlSource() {
	}
	
	public String getXmlSource() {
		if(xmlSource == null) {
			System.out.println("RUNNING");
			birtEngine.createNewDesign("Temp.rptdesign");
			this.xmlSource = birtEngine.getSource();
		}
		System.out.println(this.xmlSource);
		return xmlSource;
	}
	
	public void setXmlSource(String xmlSource) {
		
		this.xmlSource = xmlSource;
	}
}
