package com.opentext.bdweb.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;

import com.opentext.birt.engine.BirtEngine;

@ManagedBean
@SessionScoped
public class XmlSource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String xmlSource = null;
	private BirtEngine birtEngine = new BirtEngine();
	private UIInput newSource = new UIInput();
	
	public XmlSource() {
		System.out.println("Constructing!!!!!!!");
		
		if(xmlSource == null) {
			birtEngine.createNewDesign("Temp.rptdesign");
			this.xmlSource = birtEngine.getSource();
		}
		
		System.out.println("END!!!!!!!!!!!!!!!!");
	}
	
	public void setNewSource(String newSource) {
		this.newSource.setValue(newSource);
	}
	
	public UIInput getNewSource() {
		return newSource;
	}
	
	public void sourceChange(AjaxBehaviorEvent event) {
		xmlSource = event.getComponent().getClientId();
	}
	
	public String getXmlSource() {		
		return xmlSource;
	}
	
	public void setXmlSource(String xmlSource) {
		
		this.xmlSource = xmlSource;
	}
}
