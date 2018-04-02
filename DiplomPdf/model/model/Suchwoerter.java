package model;

public class Suchwoerter {
	
	String suchwort;
	int suchwortid;
	String benutzer;

	public int getSuchwortid() {
		return suchwortid;
	}

	public void setSuchwortid(int suchwortid) {
		this.suchwortid = suchwortid;
	}

	public String getSuchwort() {
		return suchwort;
	}

	public void setSuchwort(String suchwort) {
		this.suchwort = suchwort;
	}

	public String getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(String benutzer) {
		this.benutzer = benutzer;
	}

	public Suchwoerter(String suchwort, int suchwortid,String benutzer) {
		super();
		this.suchwort = suchwort;
		this.suchwortid = suchwortid;
		this.benutzer = benutzer;
	}

	public Suchwoerter() {
		super();
		this.suchwort = " ";
		this.suchwortid =0;
		this.benutzer = " ";
	}

	@Override
	public String toString() {
		return "Suchwoerter [suchwort=" + suchwort + ", suchwortid=" + suchwortid + ", benutzer=" + benutzer + "]";
	}
	

}
