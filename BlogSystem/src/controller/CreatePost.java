package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BlogPost;
import beans.SummarisedPost;
import beans.User;
import dbhandler.DbOpsPost;


public class CreatePost extends HttpServlet {

	private boolean isModeValid(String mode) {
		
		if(mode == null)
			return false;
		
		if(mode.equals("create") || mode.equals("edit"))
			return true;
		
		return false;
		
	}
	
	private int isPostIdValid(String postId) {
		
		int postID = -1;
		
		try {
			postID = Integer.parseInt(postId);
		} catch(NumberFormatException e) {
			postID = -1;
		}
		
		return postID;
			
	}
	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();	
		User user = (User)session.getAttribute("user");
		
		String mode = request.getParameter("mode");
		String postId = request.getParameter("postId");
		
		DbOpsPost dops = new DbOpsPost();
		RequestDispatcher rd;	
		 
		
		if(user != null ){
			
			if(isModeValid(mode)) {
				
				if(mode.equals("create")) {
					
					rd = request.getRequestDispatcher("dynamic/pages/create_post.jsp");
					rd.forward(request, response);
					
				} 
				
				if(mode.equals("edit")) {
					
					int pid = isPostIdValid(postId);
					
					if(pid != -1 && dops.establishConnection() && dops.hasUserCreatedPost(user.getUid(), pid)) {
							
							BlogPost bp = dops.getFullPost(pid);						
							request.setAttribute("editablePost", bp);						
							rd = request.getRequestDispatcher("dynamic/pages/create_post.jsp");
							rd.forward(request, response);
							
					}
									
				} 
					
			}
				
		}
			
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String date = request.getParameter("date");
		String body = request.getParameter("body");
		String mode = request.getParameter("mode");
		String postId = request.getParameter("pid");
		
		DbOpsPost dop = new DbOpsPost();
		User user = (User)request.getSession().getAttribute("user");
		
		if(user != null && mode != null) {
			
			if(mode.equals("create")) {
			
				int uid = user.getUid();
				
				if(dop.establishConnection() && dop.createNewPost(uid, title, date, body))
						response.sendRedirect("dashboard");
				
			} else if(mode.equals("delete")) {
				
				if(dop.establishConnection() && dop.deletePost(user.getUid(), isPostIdValid(postId))) 
					response.sendRedirect("dashboard");
		
			} else if(mode.equals("update")) {
				
				if(dop.establishConnection() && dop.updatePost(user.getUid(), isPostIdValid(postId), title, body))
					response.sendRedirect("dashboard");
				
			} else {
				
				response.getWriter().print("else part.");
				
			}

		} else {		
			response.getWriter().print("Can't create post, some error occurred.");
		}
		
	}



}
