package model;

public class Daten {

	String dateiname;
	String autor;
	String uploader;
	String inhalttext;
	String stichworttext;
	String dateityp;
	String status;
	String tag;
	String uploaddatum;
	String dokumentdatum;
	String deletedatum;
	byte[] blobdatei;
	
	
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
	
	public String getDateityp() {
		return dateityp;
	}
	public void setDateityp(String dateityp) {
		this.dateityp = dateityp;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getUploaddatum() {
		return uploaddatum;
	}
	
	public void setUploaddatum(String uploaddatum) {
		this.uploaddatum = uploaddatum;
	}
	
	public String getDokumentdatum() {
		return dokumentdatum;
	}
	
	public void setDokumentdatum(String ddokumentdatum) {
		this.dokumentdatum = ddokumentdatum;
	}
	
	public byte[] getBlobdatei() {
		return blobdatei;
	}
	
	public void setBlobdatei(byte[] blobdatei) {
		this.blobdatei = blobdatei;
	}

	public Daten(String dateiname, String autor, String uploader, String inhalttext, String stichworttext,
			String dateityp, String status, String tag, String uploaddatum, String ddokumentdatum, byte[] blobdatei) {
		super();
		this.dateiname = dateiname;
		this.autor = autor;
		this.uploader = uploader;
		this.inhalttext = inhalttext;
		this.stichworttext = stichworttext;
		this.dateityp = dateityp;
		this.status = status;
		this.tag = tag;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = ddokumentdatum;
		this.blobdatei = blobdatei;
	}
	
	

	public Daten(String dateiname, String autor, String uploader, String inhalttext, String stichworttext,
			String dateityp, String status, String tag, String uploaddatum, String dokumentdatum, String deletedatum,
			byte[] blobdatei) {
		super();
		this.dateiname = dateiname;
		this.autor = autor;
		this.uploader = uploader;
		this.inhalttext = inhalttext;
		this.stichworttext = stichworttext;
		this.dateityp = dateityp;
		this.status = status;
		this.tag = tag;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
		this.deletedatum = deletedatum;
		this.blobdatei = blobdatei;
	}

	
	
	public Daten() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Uploaddaten [dateiname=" + dateiname + ", autor=" + autor + ", uploader=" + uploader + ", inhalttext="
				+ inhalttext + ", stichworttext=" + stichworttext + ", dateityp=" + dateityp + ", status=" + status
				+ ", tag=" + tag + ", uploaddatum=" + uploaddatum + ", ddokumentdatum=" + dokumentdatum
				+ ", blobdatei=" + blobdatei + "]";
	}
	
	
	
	

}
