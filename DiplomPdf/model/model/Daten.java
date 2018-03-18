package model;

import java.util.Arrays;

public class Daten {

	int uploadid;
	int loeschid;
	String dateiname;
	String autor;
	String uploader;
	String inhalttext;
	String stichworttext;
	String dateityp;
	String status;
	String uploaddatum;
	String dokumentdatum;
	String deletedatum;
	byte[] blobdatei;
	boolean zustand;
	int i;

	public Daten() {
		super();
	}

	public int getUploadid() {
		return uploadid;
	}

	public void setUploadid(int uploadid) {
		this.uploadid = uploadid;
	}

	public int getLoeschid() {
		return loeschid;
	}

	public void setLoeschid(int loeschid) {
		this.loeschid = loeschid;
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

	public String getDeletedatum() {
		return deletedatum;
	}

	public void setDeletedatum(String deletedatum) {
		this.deletedatum = deletedatum;
	}

	public boolean isZustand() {
		return zustand;
	}

	public void setZustand(boolean zustand) {
		this.zustand = zustand;
	}

	public Daten(int uploadid, String dateityp, String dateiname, String autor,
			String uploaddatum, String dokumentdatum, String status,float i) {
		super();
		this.uploadid = uploadid;
		this.dateityp = dateityp;
		this.dateiname = dateiname;
		this.autor = autor;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
		this.status = status;

	}

	public Daten(int uploadid, String dateityp, String dateiname, String autor,
			String uploaddatum, String dokumentdatum, String deletedatum,double i) {
		super();
		this.uploadid = uploadid;
		this.dateityp = dateityp;
		this.dateiname = dateiname;
		this.autor = autor;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
		this.deletedatum = deletedatum;

	}

	public Daten(int uploadid, String dateityp, String dateiname, String uploader, String autor,
			String uploaddatum, String dokumentdatum, String status) {
		super();
		this.uploadid = uploadid;
		this.dateityp = dateityp;
		this.dateiname = dateiname;
		this.uploader = uploader;
		this.autor = autor;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
		this.status = status;

	}

//	new Daten(uploadid,dateityp,dateiname, autor, uploaddatum, dokumentdatum, deletedatum, status)
	public Daten(int uploadid, String dateityp, String dateiname, String autor,
			String uploaddatum, String dokumentdatum, String deletedatum, String status,float i) {
		super();
		this.uploadid = uploadid;
		this.dateityp = dateityp;
		this.dateiname = dateiname;
		this.deletedatum = deletedatum;
		this.autor = autor;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
		this.status = status;

	}

	public Daten(String dateiname, String autor, String uploader, String inhalttext, String stichworttext,
			String dateityp, String status, String uploaddatum, String dokumentdatum, String deletedatum,
			byte[] blobdatei) {
		super();
		this.dateityp = dateityp;
		this.dateiname = dateiname;
		this.uploader = uploader;
		this.autor = autor;
		this.uploader = uploader;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
		this.status = status;
		this.inhalttext = inhalttext;
		this.stichworttext = stichworttext;
		this.deletedatum = deletedatum;
	}

	//uploadid, dateityp, dateiname, autor, deletedatum, uploaddatum, dokumentdatum
	public Daten(int uploadid, String dateityp, String dateiname, String autor,
			String deletedatum, String uploaddatum, String dokumentdatum, long i) {
		super();
		this.uploadid = uploadid;
		this.dateityp = dateityp;
		this.dateiname = dateiname;
		this.deletedatum = deletedatum;
		this.autor = autor;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;

	}
	
	
	@Override
	public String toString() {
		return "Daten [uploadid=" + uploadid + ", dateiname=" + dateiname + ", autor=" + autor + ", uploader="
				+ uploader + ", inhalttext=" + inhalttext + ", stichworttext=" + stichworttext + ", dateityp="
				+ dateityp + ", status=" + status + ", uploaddatum=" + uploaddatum + ", dokumentdatum=" + dokumentdatum
				+ ", deletedatum=" + deletedatum + ", blobdatei=" + Arrays.toString(blobdatei) + "]";
	}

}
