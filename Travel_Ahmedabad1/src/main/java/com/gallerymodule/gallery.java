package com.gallerymodule;

import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;

@MultipartConfig(maxFileSize = 16177215)
public class gallery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pname=request.getParameter("pname");

		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_ahmedabad","root","9288");
			PreparedStatement pt=con.prepareStatement("insert into gallery values(?,?,?)");
			pt.setString(1,request.getParameter("uname"));
			pt.setString(3,request.getParameter("pname"));
			InputStream inputStream = null;
            Part filePart = request.getPart("img");
            if (filePart != null) {
                // prints out some information for debugging
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());
                 
                // obtains input stream of the upload file
                inputStream = filePart.getInputStream();
                
            }
            if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
                pt.setBlob(2, inputStream);
            }
			pt.executeUpdate();
			response.sendRedirect("destinations.jsp?pname="+pname);
			return;
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
