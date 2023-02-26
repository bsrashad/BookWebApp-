package com.proj.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/editurl")
public class EditingServlet extends HttpServlet {

private static final String query ="UPDATE BOOKDATA SET BOOKNAME=?,BOOKEDITION=?,BOOKPRICE=? WHERE ID=?";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//get printwriter
		PrintWriter pWriter=resp.getWriter();
		// set content type
		resp.setContentType("text/html");
		
		//get the id of record
		int id=Integer.parseInt(req.getParameter("id"));
		//get the edited data
		
		String bookname=req.getParameter("bookname");
		String bookedition=req.getParameter("bookedition");   
		Float bookprice=Float.parseFloat( req.getParameter("bookprice")) ;
		
		//load the jdbc driver
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
			// TODO: handle exception
		}
		//generate connection
		try (Connection connection = DriverManager.getConnection("jdbc:mysql:///book","root","rashad@123");
				PreparedStatement pStatement=connection.prepareStatement(query);){
			pStatement.setString(1, bookname);
			pStatement.setString(2, bookedition);
			pStatement.setFloat(3, bookprice);
			pStatement.setInt(4, id);
			int count=pStatement.executeUpdate();
		
			if(count==1) {
				pWriter.println("<h2>record is edited successfullly</h2>");
			}else {
				pWriter.println("<h2>record is not edited</h2>");
			}
			
			
		} catch (SQLException se) {
			// TODO: handle exception
			se.printStackTrace();
			pWriter.println("<h1>"+se.getMessage()+"</h1>");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			pWriter.println("<h1>"+e.getMessage()+"</h1>");
		}
		
		pWriter.println("<a href='home.html'>Home</a>");
		pWriter.print("<br>");
		pWriter.print("<br>");
		pWriter.println("<a href='booklist'>BookList</a>");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doGet(req, resp);
	}
}
