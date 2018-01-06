package is;

import java.sql.Blob;

public class bilder {
	String name;
	Blob bild;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Blob getBild() {
		return bild;
	}
	
	public void setBild(Blob bild) {
		this.bild = bild;
	}

	public bilder(String name, Blob bild) {
		super();
		this.name = name;
		this.bild = bild;
	}

	public bilder() {
		super();
		this.name="";
		//this.bild;
	}

	@Override
	public String toString() {
		return "bilder [name=" + name + ", bild=" + bild + "]";
	}

	
	
}
