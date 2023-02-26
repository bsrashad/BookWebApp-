package com.proj.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.exceptions.RSAException;
import com.mysql.cj.protocol.Resultset;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {
private static final String query ="SELECT ID,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA";
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//get printwriter
		PrintWriter pWriter=resp.getWriter();
		// set content type
		resp.setContentType("text/html");
		
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
			
		ResultSet resultSet=pStatement.executeQuery();
		
		pWriter.println("<table border='1' align='center'>");
		pWriter.println("<tr>");
		pWriter.println("<th>BOOKID</th>");
		pWriter.println("<th>BOOKNAME</th>");
		pWriter.println("<th>BOOKEDITION</th>");
		pWriter.println("<th>BOOKPRICE</th>");
		pWriter.println("<th>Edit</th>");
		pWriter.println("<th>Delete</th>");
		
		pWriter.println("</tr>");
		
		
		while(resultSet.next()) {
			
			pWriter.println("<tr>");
			pWriter.println("<td>"+resultSet.getInt(1)+"</td>");
			pWriter.println("<td>"+resultSet.getString(2)+"</td>");
			pWriter.println("<td>"+resultSet.getString(3)+"</td>");
			pWriter.println("<td>"+resultSet.getFloat(4)+"</td>");
			pWriter.println("<td><a href='editscreen?id="+resultSet.getInt(1)+"'>Edit</td>");
			pWriter.println("<td><a href='deleteurl?id="+resultSet.getInt(1)+"'>Delete</td>");
			
			pWriter.println("</tr>");
			
		}
		pWriter.println("</table>");
			
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
