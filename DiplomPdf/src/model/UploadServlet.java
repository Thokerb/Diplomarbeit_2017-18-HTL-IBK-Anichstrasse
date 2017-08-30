package model;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.InputStream;


import com.google.gson.Gson;

/**
 * Servlet implementation class UploadServlet
 */
//@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());


		//		begriffe = gson.fromJson(antwort, String[].class);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Part filePart = request.getPart("pdffile"); // Retrieves <input type="file" name="file">
		InputStream fileContent = filePart.getInputStream(); 
		String dateiname = request.getParameter("dateiname");
		System.out.println(dateiname);



		int size = request.getContentLength();
		byte[] bytes = new byte[1024];

		File uploads = new File("C:/Temp");

		File file = new File(uploads, dateiname);

		Files.copy(fileContent, file.toPath());



		System.out.println("Datei fertig eingelesen");




	}

}
