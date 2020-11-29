package dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

import beans.SummarisedPost;
import beans.BlogPost;
import beans.DailyQuote;
import beans.MostViewedPost;

public class DbOpsPost {
	
	// messed up but working, rename date column to something else, I don't know why it's working cause mysql has a date datatype
	
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
		
		return posts.size() > 0 ? posts : null;
	
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
		

		return posts.size() > 0 ? posts : null;
			
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
			ps.setString(4, body);
				
			if(ps.executeUpdate() == 1) {
				
				int post_id = -1;
				
				ResultSet rs = ps.getGeneratedKeys();
				
				if(rs != null && rs.next()) {
					post_id = rs.getInt(1);
				}
				
				ps2 = conn.prepareStatement(query2);
				
				ps2.setInt(1, post_id);
				
				System.out.println("original body was : \n" + body);
				
				if(body != null && body.length() > 400) {
					body = body.substring(0, 397) + "...";
					System.out.println("\n\nTrimmed\n\n");
				}
				
				System.out.println("new body is : \n" + body);
				
				ps2.setString(2, body);
				
				return ps2.executeUpdate() == 1 && initViewCounter(post_id, title);
				
			}
			
			
			
		} catch(SQLException e) {
			
			System.out.println(e.getErrorCode() + " :: " + e.getMessage());	

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
			ps.setString(2, body);
			ps.setInt(3, uid);
			ps.setInt(4,  postId);
			
			if(ps.executeUpdate() == 1) {
				
				ps2 = conn.prepareStatement(query2);
						
				System.out.println("original body was : \n" + body);
				
				if(body != null && body.length() > 400) {
					body = body.substring(0, 397) + "...";
					System.out.println("\n\nTrimmed\n\n");
				}
				
				System.out.println("new body is : \n" + body);
				
				ps2.setString(1, body);
				ps2.setInt(2, postId);
				
				return ps2.executeUpdate() == 1 && updatePostViews(postId, title);		
				
			}
			
		} catch(SQLException e) {
			
			System.out.println(e.getErrorCode() + " :: " + e.getMessage());
			
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
			
			return ps.executeUpdate() == 1 && deletePostSummary(post_id) && deletePostViews(post_id);
			
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
	
	public boolean incrementPostViews(int post_id) {
		
		String query = "UPDATE post_views SET view_count = view_count + 1 WHERE post_id = ?";
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, post_id);
			
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
	
	public ArrayList<MostViewedPost> getMostViewedPost(int limit) {
		
		ArrayList<MostViewedPost> mvps = new ArrayList<MostViewedPost>();
		
		String query = "SELECT * FROM post_views ORDER BY view_count DESC LIMIT ?" ;
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, limit);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				MostViewedPost mvp = new MostViewedPost(rs.getInt("post_id"),rs.getInt("view_count"), rs.getString("title"));
				
				mvps.add(mvp);
				
			}
			
		} catch(SQLException e) {
			
			System.out.println(e.getErrorCode() + " :: " + e.getMessage());			
		
		} finally {
			
			try {
				ps.close();
			} catch(SQLException e) {
				e.printStackTrace();
			
			}
		}
		
		return mvps;
		
	}
	
	//not really related to posts but putting it here cause we make a connection using this class in Home Servlet where we need the quote, so only 1 connection will be there !
	public DailyQuote getTodaysQuote() {
		
		DailyQuote dq = new DailyQuote();
		LocalDate currDate = LocalDate.now();
		LocalDate yearStart = LocalDate.parse(currDate.getYear() + "-01-01");
		
		Period period = Period.between(yearStart, currDate);
		
		int diff = (int)ChronoUnit.DAYS.between(yearStart, currDate);
		
		String query = "SELECT quote_id, quote, author FROM daily_quotes WHERE quote_id = ?%(SELECT COUNT(quote_id) FROM daily_quotes) + 1";
		
		PreparedStatement ps = null;
		
		System.out.println("Days passed : " + diff);
		
		try {
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, diff);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				dq.setAuth(rs.getString("author"));
				dq.setQuote(rs.getString("quote"));
				dq.setQuoteId(rs.getInt("quote_id"));
			}
			
			rs.close();
			
		} catch(SQLException e) {
		
			System.out.println(e.getErrorCode() + " :: " + e.toString());
		
		} finally {
		
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		
		}		
		
		return dq;
		
		
		
	}
	
	
	private boolean deletePostSummary(int post_id) {
		
		String query = "DELETE FROM post_summary WHERE post_id = ?";
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, post_id);
			
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
	
	private boolean deletePostViews(int post_id) {
		
		String query = "DELETE FROM post_views WHERE post_id = ?";
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, post_id);
			
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
	
	private boolean updatePostViews(int post_id, String title) {
		
		String query = "UPDATE post_views SET title = ? WHERE post_id = ?";
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			ps.setString(1, title);
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
	
	private boolean initViewCounter(int post_id, String title) {
		
		String query = "INSERT INTO post_views (post_id, title) VALUES (?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, post_id);
			ps.setString(2, title);
			
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
