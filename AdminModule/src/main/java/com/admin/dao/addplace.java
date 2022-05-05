package com.admin.dao;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.sql.*;


@WebServlet("/addplace")
@MultipartConfig(maxFileSize = 16177215)
public class addplace extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            final Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_ahmedabad","root","9288");
            final PreparedStatement ps = con.prepareStatement("insert into destination values(?,?,?,?,?)");
            ps.setString(1, request.getParameter("pname"));
            ps.setString(3, request.getParameter("time"));
            ps.setString(4, request.getParameter("desc"));
            ps.setString(5, request.getParameter("loc"));
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
                ps.setBlob(2, inputStream);
            }
            if(inputStream ==null)
            	System.out.println("x");
            ps.executeUpdate();
            request.getRequestDispatcher("viewplace.jsp").forward(request, response);
        }
        catch (Exception e) {
            System.out.println(e);
        }
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
