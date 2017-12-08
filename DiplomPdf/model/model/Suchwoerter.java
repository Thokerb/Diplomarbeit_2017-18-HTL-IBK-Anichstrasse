package model;

public class Suchwoerter {
	
	String suchwort;

	public String getSuchwort() {
		return suchwort;
	}

	public void setSuchwort(String suchwort) {
		this.suchwort = suchwort;
	}

	public Suchwoerter(String suchwort) {
		super();
		this.suchwort = suchwort;
	}

	public Suchwoerter() {
		super();
		this.suchwort = " ";
	}

	@Override
	public String toString() {
		return "Suchwoerter [suchwort=" + suchwort + "]";
	}
	
	

}
