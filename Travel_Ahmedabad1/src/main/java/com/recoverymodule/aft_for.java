package com.recoverymodule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


public class aft_for extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		System.out.println(request.getParameter("name"));
			String que=request.getParameter("que");
			String ans=request.getParameter("ans");
			try{
				Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_ahmedabad","root","9288");
				Statement stmt=conn.createStatement();
				ResultSet rs= stmt.executeQuery("select * from userdata where email='"+name+"' or username='"+name+"';");
				while(rs.next()){
					if(rs.getString("security_answer").equals(ans)){
								request.setAttribute("name", name);
								request.getRequestDispatcher("pass.jsp").forward(request,response);
								return;
					}
				}
				request.setAttribute("err", "Entered Security Question and Answer mismatch");
				request.getRequestDispatcher("login.jsp").forward(request,response);
				return;
			}
			catch(Exception e){e.printStackTrace();}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
