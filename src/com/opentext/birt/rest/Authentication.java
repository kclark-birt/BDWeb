package com.opentext.birt.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.primefaces.json.JSONObject;

import com.opentext.ihub.IHubProperties;

public class Authentication {
	private IHubProperties iProperties = new IHubProperties();
	private HashMap credentials;
	
	public Authentication() {
		
	}
	
	private String sendPost(String endpoint, String data) throws Exception {		
		URL url = new URL(endpoint);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		
		OutputStream os = conn.getOutputStream();
		os.write(data.getBytes());
		os.flush();
 
		System.out.println(conn.getResponseCode());
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
 
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
 
		conn.disconnect();
		
		if (conn.getResponseCode() != 200) {
			return "Error";
		}
		
		return "RESPONSE";
	}
	
	public boolean authenticate() {
		String loginEndpoint = iProperties.getRestURL() + iProperties.getProperty("login");
		String loginCredentials = new JSONObject(credentials).toString();
		
		try {
			String response = sendPost(loginEndpoint, loginCredentials);
			
			if(response.contains("Error")) {
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void setCredentials(HashMap credentials) {
		this.credentials = credentials;
	}
}
