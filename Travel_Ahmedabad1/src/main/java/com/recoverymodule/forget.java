package com.recoverymodule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class forget extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_ahmedabad","root","9288");
			Statement st=con.createStatement();
			ResultSet rs= st.executeQuery("select email, username from userdata where email='"+name+"' or username='"+name+"';");
			while(rs.next())
			{
				if(rs.getString("email").equals(name))
					request.setAttribute("name", name);
				if(rs.getString("username").equals(name)) 
					request.setAttribute("name", name);
				request.getRequestDispatcher("Forget.jsp").forward(request, response);
				return;
			}
			request.setAttribute("err", "Username Incorrect");
			response.sendRedirect("login.jsp");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
