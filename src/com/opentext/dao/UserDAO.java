package com.opentext.dao;

import java.util.HashMap;

import com.opentext.birt.rest.Authentication;

public class UserDAO {   
	public UserDAO() {
		
	}
	
    public static boolean login(String user, String password) {
    	System.out.println("AUTH....****IMPLEMENT****");
    	//Authentication authenticator = new Authentication();
    	
    	//HashMap creds = new HashMap();
    	//creds.put("Username", user);
    	//creds.put("Password", password);
    	
    	//authenticator.setCredentials(creds);
    	//System.out.println(authenticator.authenticate());
    	return true;
    }
}