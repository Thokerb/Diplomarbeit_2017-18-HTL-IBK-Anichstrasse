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

	public Daten(int uploadid, String dateiname, String autor, String uploader, String inhalttext, String stichworttext,
			String dateityp, String status, String uploaddatum, String dokumentdatum, String deletedatum,
			byte[] blobdatei) {
		super();
		this.uploadid = uploadid;
		this.dateiname = dateiname;
		this.autor = autor;
		this.uploader = uploader;
		this.inhalttext = inhalttext;
		this.stichworttext = stichworttext;
		this.dateityp = dateityp;
		this.status = status;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
		this.deletedatum = deletedatum;
		this.blobdatei = blobdatei;
	}

	public Daten() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Daten(String dateiname, String autor, String uploader, String inhalttext, String stichworttext,
			String dateityp, String status, String uploaddatum, String dokumentdatum, String deletedatum,
			byte[] blobdatei) {
		super();
		this.dateiname = dateiname;
		this.autor = autor;
		this.uploader = uploader;
		this.inhalttext = inhalttext;
		this.stichworttext = stichworttext;
		this.dateityp = dateityp;
		this.status = status;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
		this.deletedatum = deletedatum;
		this.blobdatei = blobdatei;
	}

	public Daten(int uploadid, String dateiname, String autor, String uploader, String inhalttext,
			String stichworttext, String dateityp, String status, String uploaddatum, String dokumentdatum) {
		super();
		this.uploadid = uploadid;
		this.dateiname = dateiname;
		this.autor = autor;
		this.uploader = uploader;
		this.inhalttext = inhalttext;
		this.stichworttext = stichworttext;
		this.dateityp = dateityp;
		this.status = status;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
	}

	public Daten(int uploadid, String dateityp,String dateiname, String autor, String uploaddatum, String dokumentdatum,
			String status) {
		super();
		this.uploadid = uploadid;
		this.dateiname = dateiname;
		this.autor = autor;
		this.dateityp = dateityp;
		this.status = status;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
	}

	public Daten(int uploadid, String dateityp, String dateiname, String uploader, String autor,
			String uploaddatum, String dokumentdatum, String status) {
		super();
		this.uploadid = uploadid;
		this.dateiname = dateiname;
		this.autor = autor;
		this.dateityp = dateityp;
		this.status = status;
		this.uploaddatum = uploaddatum;
		this.dokumentdatum = dokumentdatum;
		this.uploader = uploader;
	}
	
//	public Daten(int loeschid, String dateityp, String dateiname, String uploader, String autor,
//			String uploaddatum, String dokumentdatum, String status) {
//		super();
//		this.loeschid = loeschid;
//		this.dateiname = dateiname;
//		this.autor = autor;
//		this.dateityp = dateityp;
//		this.status = status;
//		this.uploaddatum = uploaddatum;
//		this.dokumentdatum = dokumentdatum;
//		this.uploader = uploader;
//	}

	@Override
	public String toString() {
		return "Daten [uploadid=" + uploadid + ", dateiname=" + dateiname + ", autor=" + autor + ", uploader="
				+ uploader + ", inhalttext=" + inhalttext + ", stichworttext=" + stichworttext + ", dateityp="
				+ dateityp + ", status=" + status + ", uploaddatum=" + uploaddatum + ", dokumentdatum=" + dokumentdatum
				+ ", deletedatum=" + deletedatum + ", blobdatei=" + Arrays.toString(blobdatei) + "]";
	}

}
