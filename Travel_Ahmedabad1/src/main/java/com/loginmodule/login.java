package com.loginmodule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;


public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loginBean log=new loginBean();//object is created  
		String pname=request.getParameter("pname");
		log.setUsername(request.getParameter("uname"));//setting value to the object
		log.setPasscode(request.getParameter("pass"));  
		try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_ahmedabad","root","9288"))
		{
			String query="select * from userdata where username=? and passcode=?";
			PreparedStatement pt=con.prepareStatement(query);
			pt.setString(1, log.getUsername());
			pt.setString(2, log.getPasscode());
			ResultSet rs=pt.executeQuery();
			while(rs.next())
			{
				if(log.getUsername().equals(rs.getString("username"))&&log.getPasscode().equals(rs.getString("passcode")))
				{
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(600);
					session.setAttribute("user",log.getUsername());
					if(pname.equals(("null")))
					{
						response.sendRedirect("index.jsp");
						return;
					}
					else if(pname.equals("allDestinations.jsp"))
					{
						response.sendRedirect("allDestinations.jsp");
						return;
					}
					else if(pname.equals("gallery"))
					{
						response.sendRedirect("gallery.jsp");
						return;
					}
					response.sendRedirect("destinations.jsp?pname="+pname);
					
					
					return;
				}
			}
			request.setAttribute("err", "Invalid Credentials");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
