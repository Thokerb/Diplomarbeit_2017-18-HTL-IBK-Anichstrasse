package model;

public class Benutzer {
	
	String benutzername;
	String email;
	String passwort;
	
	public String getBenutzername() {
		return benutzername;
	}
	
	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPasswort() {
		return passwort;
	}
	
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Benutzer(String benutzername, String email, String passwort) {
		super();
		this.benutzername = benutzername;
		this.email = email;
		this.passwort = passwort;
	}

	public Benutzer() {
		super();
		this.benutzername = " ";
		this.email = " ";
		this.passwort = " ";
	}

	@Override
	public String toString() {
		return "Benutzer [benutzername=" + benutzername + ", email=" + email + ", passwort=" + passwort + "]";
	}


}
