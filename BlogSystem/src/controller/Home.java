package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

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
		request.setAttribute("archive", viewTree(posts));
		
		RequestDispatcher rd = request.getRequestDispatcher("dynamic/pages/index.jsp");
		rd.forward(request, response);	
			
	}
	

	
	
	private TreeMap<Integer, TreeMap<Integer, ArrayList<SummarisedPost>>> viewTree(ArrayList<SummarisedPost> posts) {

		TreeMap<Integer, TreeMap<Integer, ArrayList<SummarisedPost>>> map = new TreeMap<>(Collections.reverseOrder());
		
		for(SummarisedPost sp : posts) {
			
			int year = Integer.valueOf(sp.getDate().substring(0,4));
			int month = Integer.valueOf(sp.getDate().substring(5,7));
			
			TreeMap<Integer, ArrayList<SummarisedPost>> monthMap;
			
			if(map.containsKey(year)) {
				
				monthMap = (TreeMap<Integer, ArrayList<SummarisedPost>>)map.get(year);
				
				if(monthMap.containsKey(month)) {
					((ArrayList<SummarisedPost>)monthMap.get(month)).add(sp);
				} else {
					ArrayList<SummarisedPost> arr = new ArrayList<>();
					arr.add(sp);
					monthMap.put(month, arr);
				}
				
				
			} else {
				
				monthMap = new TreeMap<>(Collections.reverseOrder());
				
				ArrayList<SummarisedPost> arr = new ArrayList<>();
				arr.add(sp);
				
				monthMap.put(month, arr);
				
				map.put(year, monthMap);
				
			}
			
		}
		
//		Display		
//		for(Integer year : map.keySet()) {
//			
//			System.out.println("YEAR -- " + year);
//			
//			for(Integer month : map.get(year).keySet()) {
//			
//				System.out.println("\tMONTH -- " + month);
//			
//				for(SummarisedPost sp  : map.get(year).get(month))
//					System.out.println("\t\t" + sp.getTitle());
//				
//			}
//			
//			
//		}
		
		return map;
		
		
	}

}
