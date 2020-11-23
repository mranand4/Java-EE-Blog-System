package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.JDBCType;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.DailyQuote;
import beans.MostViewedPost;
import beans.SummarisedPost;
import dbhandler.DbOpsPost;

public class Home extends HttpServlet {
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<SummarisedPost> posts = null;
		DbOpsPost dops = new DbOpsPost();
		
		if(dops.establishConnection())
			posts = dops.getHomepagePosts();
		
		ArrayList<MostViewedPost> mvps = dops.getMostViewedPost(5);
		
		DailyQuote dq = dops.getTodaysQuote();
	
		request.setAttribute("posts", posts);		
		request.setAttribute("mvps", mvps);
		request.setAttribute("dailyQuote", dq);
		
		RequestDispatcher rd = request.getRequestDispatcher("dynamic/pages/index.jsp");
		rd.forward(request, response);	
			
	}

}
