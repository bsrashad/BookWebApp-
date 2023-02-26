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
@WebServlet("/editscreen")
public class EditScreenServlet extends HttpServlet {
private static final String query ="SELECT BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA WHERE ID=?";
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//get printwriter
		PrintWriter pWriter=resp.getWriter();
		// set content type
		resp.setContentType("text/html");
		
		//get the id of record
		int id=Integer.parseInt(req.getParameter("id"));
		
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
			pStatement.setInt(1,id);
		ResultSet resultSet=pStatement.executeQuery();
		resultSet.next();
		pWriter.println("<form action='editurl?id="+id+"' method='post'>");
		pWriter.println("<table  align='center'>");
		pWriter.println("<tr>");
		pWriter.println("<td>BOOKNAME</td>");
		pWriter.println("<td><input type='text' name='bookname' value='"+resultSet.getString(1)+"'</td>");
		pWriter.println("<tr/>");
		
		pWriter.println("<tr>");
		pWriter.println("<td>BOOKEDITION</td>");
		pWriter.println("<td><input type='text' name='bookedition' value='"+resultSet.getInt(2)+"'</td>");
		pWriter.println("<tr/>");
   
		pWriter.println("<tr>");
		pWriter.println("<td>BOOOKPRICE</td>");
		pWriter.println("<td><input type='text' name='bookprice' value='"+resultSet.getFloat(3)+"'</td>");
		pWriter.println("<tr/>");
		
		pWriter.println("<tr>");
		pWriter.println("<td><input type='submit' value='edit'></td>");
		pWriter.println("<td><input type='reset' value='cancel'></td>");
		pWriter.println("<tr/>");
	
		
		pWriter.println("</table>");

		pWriter.println("</form>");
			
		} catch (SQLException se) {
			// TODO: handle exception
			se.printStackTrace();
			pWriter.println("<h1>"+se.getMessage()+"</h1>");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			pWriter.println("<h1>"+e.getMessage()+"</h1>");
		}
		
		pWriter.print("<br>");
		pWriter.print("<br>");
		pWriter.println("<a href='home.html'>Home</a>");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doGet(req, resp);
	}
	
}
