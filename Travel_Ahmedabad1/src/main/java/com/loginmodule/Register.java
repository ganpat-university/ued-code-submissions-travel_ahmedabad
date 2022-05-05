package com.loginmodule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	errorMsg ob=new errorMsg();
	String err;
	String fname;
	String email;
	String gender;
	String dob;
	String country;
	String s_que;
	String ans;
	String uname;
	String pass;
	String c_pass;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.fname=request.getParameter("fname");
		this.email=request.getParameter("email").toLowerCase();
		this.gender=request.getParameter("gender");
		this.dob=request.getParameter("dob");
		this.country=request.getParameter("country");
		this.s_que=request.getParameter("que");
		this.ans=request.getParameter("ans");
		this.uname=request.getParameter("uname");
		this.pass=request.getParameter("pass");
		this.c_pass=request.getParameter("c_pass");
		if(validate(ob)) {
			try{
				Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_ahmedabad","root","9288");
				PreparedStatement pstmt=conn.prepareStatement("insert into userdata values(?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1,fname);
				pstmt.setString(2,email);
				pstmt.setString(3,gender);
				pstmt.setString(4,dob);
				pstmt.setString(5,country);
				pstmt.setString(6,s_que);
				pstmt.setString(7,ans);
				pstmt.setString(8,uname);
				pstmt.setString(9,pass);
				int i=pstmt.executeUpdate();
				if(i==1){
					ob.setMsg("Registered Succesfully");
					err="Registered Succesfully";
					request.setAttribute("err", err);
					request.getRequestDispatcher("login.jsp").forward(request,response);
				}
				
			}
			catch(Exception e){}
		}		
		else
		{
			request.setAttribute("err", err);
			request.getRequestDispatcher("login.jsp").forward(request,response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected boolean validate(errorMsg ob) {
		
		if(fname.trim().length()==0)
		{
			ob.setMsg("Name cannot be Blank");
			err="Name cannot be Blank";
			return false;
		}
		else if(!primaryUsername())
		{
			ob.setMsg("Username already Exists");
			err="Username already Exists";
			return false;
		}
		else if(pass.length()<6)
		{
			ob.setMsg("Password Should be more than 6 characters");
			err="Password Should be more than 6 characters";
			return false;
		}
		else if(!pass.equals(c_pass))
		{
			ob.setMsg("Confirm Password does not match");
			err="Confirm password does not match";
			return false;
		}
		return true;
	}
	protected boolean primaryUsername()
	{
		try
		{
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_ahmedabad","root","9288");
			Statement st = conn.createStatement();
			ResultSet rs= st.executeQuery("select * from userdata where username='"+uname+"';");
			while(rs.next())
				if(rs.getString("username").equals(uname))
					return false;
					
			return true;
		}
		catch(SQLException e)
		{e.printStackTrace();}
		
		return false;
	}

}
