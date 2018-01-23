package model;

public class Benutzer {
	
	String benutzername;
	String email;
	String vorname;
	String nachname;
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
	
	public String getVorname() {
		return vorname;
	}
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getNachname() {
		return nachname;
	}
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public String getPasswort() {
		return passwort;
	}
	
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Benutzer(String benutzername, String email, String vorname, String nachname, String passwort) {
		super();
		this.benutzername = benutzername;
		this.email = email;
		this.vorname = vorname;
		this.nachname = nachname;
		this.passwort = passwort;
	}

	public Benutzer() {
		super();
		this.benutzername = " ";
		this.email = " ";
		this.vorname = " ";
		this.nachname = " ";
		this.passwort = " ";
	}

	@Override
	public String toString() {
		return "Benutzer [benutzername=" + benutzername + ", email=" + email + ", vorname=" + vorname + ", nachname="
				+ nachname + ", passwort=" + passwort + "]";
	}
}
