package org.opentext.bdweb.ihub;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.json.JSONObject;

import com.opentext.bdweb.beans.LoginBean;

public class Rest {
	private final static Logger LOGGER = Logger.getLogger(Rest.class);
	
	public Rest() {
		
	}
	
	private String sendPost(String endpoint) {		
		try{
			URL url = new URL(endpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	 
			String output;
			
			StringBuilder sb = new StringBuilder();
			
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
	 
			conn.disconnect();
			
			if (conn.getResponseCode() != 200) {
				return "Error";
			}
			
			return sb.toString();
		}catch(Exception ex){
			LOGGER.error(ex);
		}
		
		return "";
	}
	
	public boolean authenticate(String username, String password) {		
		try {
			String endpoint = "http://poc.actuate.com:5000/ihub/v1/login?username=" + username + "&password=" + password;
			String response = sendPost(endpoint);
			
			JSONObject jsonResponse = new JSONObject(response);
			
			if(response.contains("Error")) {
				return false;
			}else{
		        // Get the loginBean from session attribute
				FacesContext facesContext = FacesContext.getCurrentInstance();
				LoginBean loginBean = (LoginBean)facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "loginBean");
				loginBean.setAuthId(jsonResponse.getString("AuthId"));
				return true;
			}
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		
		return false;
	}
}