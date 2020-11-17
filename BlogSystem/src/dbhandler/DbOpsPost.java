package dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import beans.SummarisedPost;
import beans.BlogPost;

public class DbOpsPost {
	
	private final String dbUserName = "root";
	private final String dbUserPass = "mypass_mysql_1";
	private final String dbURL   = "jdbc:mysql://localhost:3306/db_blog";
	private final String driverClass = "com.mysql.cj.jdbc.Driver";
	
	private Connection conn;
	
	public boolean establishConnection() {
		
		try {
			Class.forName(driverClass); 
			conn = DriverManager.getConnection(dbURL, dbUserName, dbUserPass);
		} catch (ClassNotFoundException e) {
			return false;
		} catch (SQLException e) {
			return false;
		}
		
		return true;
		
	}
	
	public void closeConenction() {
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	
	public ArrayList<SummarisedPost> getHomepagePosts() {
		
		ArrayList<SummarisedPost> posts = new ArrayList<>();
		
		String query = "SELECT post_summary.post_id, title, date, summary, "
				+ "fname, lname FROM post_summary INNER JOIN post"
				+ " ON post.post_id = post_summary.post_id INNER JOIN"
				+ " user ON user.uid = post.uid ORDER BY post.post_id DESC";
		
		Statement smt = null;
		ResultSet rs  = null;
		
		try {
			
			smt = conn.createStatement();
			rs = smt.executeQuery(query);
			
			while(rs.next()) {
				SummarisedPost sp = new SummarisedPost(rs.getInt("post_id"), 
						rs.getString("title"), 
						rs.getDate("date").toString(), 
						rs.getString("fname"), 
						rs.getString("lname"),
						rs.getString("summary"));
				
				posts.add(sp);
			}
			
		} catch (SQLException e) {
	
			System.out.println(e.getErrorCode() + " :: " + e.toString());
			
		} finally {
			
			try {
				smt.close();
				rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return posts;
			
	}
	
	public ArrayList<SummarisedPost> getUserPosts(int uid) {
		
		ArrayList<SummarisedPost> posts = new ArrayList<>();
		
		String query = "SELECT post_summary.post_id, title, date, summary, "
				+ "fname, lname FROM post_summary INNER JOIN post ON post.post_id"
				+ " = post_summary.post_id INNER JOIN user ON user.uid"
				+ " = post.uid WHERE user.uid = " + uid + " ORDER BY post.post_id DESC";
		
		Statement smt = null;
		ResultSet rs  = null;
		
		try {
			
			smt = conn.createStatement();
			rs = smt.executeQuery(query);
			
			while(rs.next()) {
				SummarisedPost sp = new SummarisedPost(rs.getInt("post_id"),
						rs.getString("title"), 
						rs.getDate("date").toString(), 
						rs.getString("fname"),
						rs.getString("lname"), 
						rs.getString("summary"));
				
				posts.add(sp);
			}
			
			rs.close();
			smt.close();
			
		} catch (SQLException e) {

			System.out.println(e.getErrorCode() + " :: " + e.toString());
			
		} finally {
			
			try {
				smt.close();
				rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return posts;
			
	}
	
	public BlogPost getFullPost(int id) {
		
		BlogPost post = null;
		String query = "SELECT user.uid, post_id, title, date, body, fname, lname FROM post INNER JOIN user ON post.uid = user.uid WHERE post_id = " + id;
		
		try {
			
			Statement smt = conn.createStatement();
			ResultSet rs = smt.executeQuery(query);
			
			while(rs.next()) {
				post = new BlogPost(rs.getInt("post_id"), rs.getInt("uid"), rs.getString("title"), rs.getDate("date").toString(), rs.getString("fname"), rs.getString("lname"), rs.getString("body"));
			}
			
			rs.close();
			smt.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return post;		
	}
	
	public boolean createNewPost(int uid, String title, String date, String body) {
		
		String query = "INSERT INTO post (uid, title, date, body) VALUES(?,?,?,?)";
		String query2 = "INSERT INTO post_summary (post_id, summary) VALUES(?,?)";
		
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try {
			
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			ps.setInt(1, uid);
			ps.setString(2, title);		
			ps.setString(3, date);
			
			if(body != null)
				ps.setString(4, body);
			
			int r = ps.executeUpdate();
			
			int post_id = -1;
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs != null && rs.next()) {
				post_id = rs.getInt(1);
			}
			
			if(r == 1) {
				
				ps2 = conn.prepareStatement(query2);
				
				ps2.setInt(1, post_id);
				
				if(body.length() > 400) {
					body = body.substring(0, 397) + "...";
				}
				
				ps2.setString(2, body);
				
				r = ps2.executeUpdate();
				
				if(r == 1)
					return true;
				
			}
			
			
			
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				ps.close();
				if(ps2 != null)
					ps2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
		return false;
		
	}
	
	
	public boolean updatePost(int uid, int postId, String title, String body) {
		
		String query = "UPDATE post SET title = ?, body = ? WHERE uid = ? AND post_id = ?";
		String query2 = "UPDATE post_summary SET summary = ? WHERE post_id = ?";
		
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			
			ps.setString(1, title);	
			ps.setInt(3, uid);
			ps.setInt(4,  postId);
			
			if(body != null)
				ps.setString(2, body);
			
			int r = ps.executeUpdate();
			
			if(r == 1) {
				
				ps2 = conn.prepareStatement(query2);
						
				if(body.length() > 400) {
					body = body.substring(0, 397) + "...";
				}
				
				ps2.setString(1, body);
				ps2.setInt(2, postId);
				
				r = ps2.executeUpdate();
				
				if(r == 1)
					return true;
				
			}
			
			
			
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				ps.close();
				if(ps2 != null)
					ps2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
		return false;
	}
	
	
	
	public boolean hasUserCreatedPost(int uid, int post_id) {
		
		String query = "SELECT COUNT(post_id) FROM post WHERE uid = ? AND post_id = ?";
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, uid);
			ps.setInt(2, post_id);
			
			ResultSet rs = ps.executeQuery();
			
			return rs.next() && rs.getInt(1) == 1;
			
		} catch(SQLException e) {
			
			System.out.println(e.getErrorCode() + " :: " + e.getMessage());	
			
		} finally {
			
			try {
				ps.close();
			} catch(SQLException e) {
				e.printStackTrace();
			
			}
		}
		
		return false;
	
	}
	
	
	public boolean deletePost(int uid, int post_id) {
		
		String query = "DELETE FROM post WHERE uid = ? AND post_id = ?";
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, uid);
			ps.setInt(2, post_id);
			
			return ps.executeUpdate() == 1;
			
		} catch(SQLException e) {
			
			System.out.println(e.getErrorCode() + " :: " + e.getMessage());			
		
		} finally {
			
			try {
				ps.close();
			} catch(SQLException e) {
				e.printStackTrace();
			
			}
		}
				
		return false;
	
	}

}
