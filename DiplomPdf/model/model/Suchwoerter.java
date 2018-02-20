package model;

public class Suchwoerter {
	
	String suchwort;
	int suchwortid;

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

	public Suchwoerter(String suchwort, int suchwortid) {
		super();
		this.suchwort = suchwort;
		this.suchwortid = suchwortid;
	}

	public Suchwoerter() {
		super();
		this.suchwort = " ";
		this.suchwortid =0;
	}

	@Override
	public String toString() {
		return "Suchwoerter [suchwort=" + suchwort + ", suchwortid=" + suchwortid + "]";
	}
	
	

}
