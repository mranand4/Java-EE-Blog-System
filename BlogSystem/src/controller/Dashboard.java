package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import beans.SummarisedPost;
import dbhandler.DbOpsPost;
import dbhandler.DbOpsUser;

public class Dashboard extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();	
		User user = (User)session.getAttribute("user");
		
		if(user == null) {
			response.getWriter().print("An error occurred : USER NOT FOUND :[");
		} else {
			
			DbOpsPost dop = new DbOpsPost();
			ArrayList<SummarisedPost> posts = null;
			
			if(dop.establishConnection())
				posts = dop.getUserPosts(user.getUid());
			
			request.setAttribute("posts", posts);
			
			RequestDispatcher rd = request.getRequestDispatcher("dynamic/pages/dashboard.jsp");		
			rd.forward(request, response);
		
		}

		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//new values
		String nfname = request.getParameter("first_name");
		String nlname = request.getParameter("last_name");
		String nemail = request.getParameter("email");
		String npasswd = request.getParameter("password");
		
		HttpSession session = request.getSession();	
		
		//user with old values;
		User user = (User)session.getAttribute("user");
		
		DbOpsUser ops = new DbOpsUser();
		
		if(ops.establishConnection()) {
			
			User newUser = ops.updateUser(user.getUid(), nfname, nlname, nemail, npasswd);
			
			if(newUser != null) {
				session.setAttribute("user", newUser);
				doGet(request, response);
			} else {
				response.getWriter().print("cant update your profile rn");
			}
			
		}
		
		
		
	}

}
