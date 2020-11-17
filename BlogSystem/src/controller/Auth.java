package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dbhandler.DbOpsUser;

public class Auth extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int code = -1;
		Object o = request.getAttribute("code");
		
		if(o != null)
			code = Integer.parseInt(o.toString());

			
		if(code == 2) {
			response.sendRedirect("dashboard");
		} else {	
			request.setAttribute("code", code);		
			RequestDispatcher rd = request.getRequestDispatcher("dynamic/pages/auth.jsp");
			rd.forward(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String authType = request.getParameter("auth_type");
		String fname = request.getParameter("first_name");
		String lname = request.getParameter("last_name");
		String email = request.getParameter("email");
		String passwd = request.getParameter("password");
		
		DbOpsUser dous = new DbOpsUser();
		
		int code = -1;
		User user = null;
		
		if(authType.equals("sign_up") && dous.establishConnection()) {
			if(dous.createNewUser(fname, lname, email, passwd))
				code = 0;
			else
				code = 1;
		} else if(authType.equals("sign_in") && dous.establishConnection()){
			user = dous.userExists(email, passwd);
			if(user != null)
				code = 2;
			else 			
				code = 3;		
		} else {
			response.getWriter().print("error");
		}
		
		if(user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}
		
		request.setAttribute("code", String.valueOf(code));
		
		doGet(request, response);
		
		
	}



}
