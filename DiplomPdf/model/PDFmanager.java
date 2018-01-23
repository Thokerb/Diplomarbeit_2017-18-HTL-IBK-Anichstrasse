import java.io.File;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;



public class PDFmanager {

 private PDFParser parser;
 private PDFTextStripper pdfStripper;
 private PDDocument pdDoc ;
 private COSDocument cosDoc ;

 private String Text ;
 private static String autor ;
 private static String date ;
 private static String d ;
 private String filePath;
 private File file;
 String info;

 public PDFmanager() {}

 private String convDatum(Calendar cal){

  SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
  String pD = d.format(cal.getTime());
  return pD;
 }

 public String pdfToText() throws IOException {

  this.pdfStripper = null;
  this.pdDoc = null;
  this.cosDoc = null;

  file = new File(filePath);
  parser = new PDFParser(new RandomAccessFile(file,"r")); // update for PDFBox V 2.0
  parser.parse();
  cosDoc = parser.getDocument();
  pdfStripper = new PDFTextStripper();
  pdDoc = new PDDocument(cosDoc);
  pdDoc.getNumberOfPages();

  PDDocumentInformation info = pdDoc.getDocumentInformation();
  
  autor = info.getAuthor();
  date =  convDatum(info.getCreationDate());
  System.out.println(date);

  pdfStripper.setStartPage(1);
  pdfStripper.setEndPage(pdDoc.getNumberOfPages());

  Text = pdfStripper.getText(pdDoc);
  
  pdDoc.close();
  
  System.out.println(Text);
  
  return Text;
 }

 public void setFilePath(String filePath) {
  this.filePath = filePath;
 }

 public static void getInfoPDF(){
  
//  SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//   DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//      d = formatter.format(date);
      
//  d = format1.format(date);
  
  System.out.println(" Autor: "+ autor);
  System.out.println(" Erstelldatum: "+ date);
  
 }
 
 public static String getAutor(){ 
  return autor; 
 }
 
 public static String getDatum(){ 
  return date; 
 }

}