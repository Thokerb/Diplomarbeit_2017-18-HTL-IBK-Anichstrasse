package Sara;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFmanager {

	private PDFParser parser;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc ;
	private COSDocument cosDoc ;

	private String Text ;
	private String filePath;
	private File file;

	public PDFmanager() {}
	
	public String ToText() throws IOException {

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
		pdfStripper.setStartPage(1);
		pdfStripper.setEndPage(pdDoc.getNumberOfPages());
//		pdfStripper.setEndPage(2);

		Text = pdfStripper.getText(pdDoc);
		return Text;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
