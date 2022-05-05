package com.reviewmodule;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
public class review extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pname=request.getParameter("pname");
		try
		{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_ahmedabad","root","9288");
			Statement st=con.createStatement();
			ResultSet rs= st.executeQuery("select * from reviews where username='"+request.getParameter("uname")+"' and place_name='"+request.getParameter("pname")+"';");
			while(rs.next())
			{
				PreparedStatement pt=con.prepareStatement("update reviews set description=? where username=? and place_name=?");
				pt.setString(1,request.getParameter("desc"));
				pt.setString(2,request.getParameter("uname"));
				pt.setString(3,request.getParameter("pname"));
				pt.executeUpdate();
				response.sendRedirect("destinations.jsp?pname="+pname);
				return;
			}
			PreparedStatement pt=con.prepareStatement("insert into reviews values(?,?,?)");
			pt.setString(1,request.getParameter("uname"));
			pt.setString(2,request.getParameter("desc"));
			pt.setString(3,request.getParameter("pname"));
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
