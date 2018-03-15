package model;

public class Uploaddaten {

	int uploadid;
	String dateiname;
	String autor;
	String uploader;
	String inhalttext;
	String stichworttext;
	String tag;
	String language;
	//blob blobdatei;
	
	
	public int getUploadid() {
		return uploadid;
	}
	
	public void setUploadid(int uploadid) {
		this.uploadid = uploadid;
	}
	
	public String getDateiname() {
		return dateiname;
	}
	
	public void setDateiname(String dateiname) {
		this.dateiname = dateiname;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	
	public String getInhalttext() {
		return inhalttext;
	}
	
	public void setInhalttext(String inhalttext) {
		this.inhalttext = inhalttext;
	}
	
	public String getStichworttext() {
		return stichworttext;
	}
	
	public void setStichworttext(String stichworttext) {
		this.stichworttext = stichworttext;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public Uploaddaten(int uploadid, String dateiname, String autor, String uploader, String inhalttext,
			String stichworttext, String tag, String language) {
		super();
		this.uploadid = uploadid;
		this.dateiname = dateiname;
		this.autor = autor;
		this.uploader = uploader;
		this.inhalttext = inhalttext;
		this.stichworttext = stichworttext;
		this.tag = tag;
		this.language = language;
	}

	public Uploaddaten() {
		super();
		//this.uploadid = " ";
		this.dateiname=" ";
		this.autor=" ";
		this.uploader=" ";
		this.inhalttext=" ";
		this.stichworttext=" ";
		this.tag=" ";
		this.language=" ";
	}

	@Override
	public String toString() {
		return "Uploaddaten [uploadid=" + uploadid + ", dateiname=" + dateiname + ", autor=" + autor + ", uploader="
				+ uploader + ", inhalttext=" + inhalttext + ", stichworttext=" + stichworttext + ", tag=" + tag
				+ ", language=" + language + "]";
	}
	
	
	
	
	

}
