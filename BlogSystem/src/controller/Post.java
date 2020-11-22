package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BlogPost;
import dbhandler.DbOpsPost;
import utils.ErrorMan;


public class Post extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DbOpsPost dops = new DbOpsPost();
		BlogPost bp = null;
		int postId = -1;
		
		try {
			postId = Integer.parseInt(request.getParameter("postId"));
		} catch(NumberFormatException e) {
			postId = -1;
		}
		
		if(postId != -1 && dops.establishConnection())
			bp = dops.getFullPost(postId);
		
		if(bp != null) {
			if(dops.incrementPostViews(postId))
				System.out.println("counter increased !");
			
			request.setAttribute("blogPost", bp);
			RequestDispatcher rd = request.getRequestDispatcher("dynamic/pages/post.jsp");
			rd.forward(request, response);
			
		} else {
			
		}
		
	}

}
