package org.intracube.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

public class Account {

	private static boolean loggedIn=false;
	private static String memG="null";
	private static String dispNm="null";
	
	public boolean login(String username, String password){
		try {
			String url = "http://www.intracube.invisionzone.com/check.php?ips_username=" + username + "&ips_password=" + password;
			URL submit = new URL(url);
			URLConnection con = submit.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			final BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (line.contains("Login Successful")) {
					loggedIn=true;
					final BufferedReader x = new BufferedReader(new InputStreamReader(new URL("http://www.intracube.invisionzone.com/api/?user="+username).openStream()));
					String[] items = x.readLine().split(",");
					memG = items[1];
					dispNm = items[7].substring(items[7].indexOf(":")+2, items[7].length()-2);
					break;
				} else if (line.contains("WRONG_AUTH") || line.contains("NO_USER")) {
					loggedIn=false;					
					break;
				}else if (line.contains("ACCOUNT_LOCKED")){
					loggedIn=false;
					JOptionPane.showMessageDialog(null, "Too many invalid logins.  Please wait and try again later.");
					break;
				}
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loggedIn;
	}
	
	public boolean isLoggedIn(){
		return loggedIn;
	}
	
	public String getGroup(){
		if (!loggedIn){
			return "null";
		}else{
			if (memG.contains("4")){
				return "admin";
			}else if (memG.contains("5")){
				return "banned";
			}else if (memG.contains("8")){
				return "diamond";
			}else if (memG.contains("2")){
				return "guest";
			}else if (memG.contains("3")){
				return "member";
			}else if (memG.contains("6")){
				return "mod";
			}else if (memG.contains("1")){
				return "validating";
			}else if (memG.contains("7")){
				return "vip";
			}else{
				return "unknown";
			}	
		}
	}
	
	public String getRealName(){
		return dispNm;
	}
	
	public void logout(){
		loggedIn=false;
		memG="null";
		dispNm="null";
		JOptionPane.showMessageDialog(null, "Logged Out.");
	}
}
