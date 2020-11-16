package dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.User;

public class DbOpsUser {
	
	private String uname = "root";
	private String upass = "mypass_mysql_1";
	private String url   = "jdbc:mysql://localhost:3306/db_blog";
	
	private Connection conn;
	
	public boolean establishConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(url, uname, upass);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean createNewUser(String fname, String lname, String email, String passwd) {
		
		String query = "INSERT INTO user (fname, lname, email, passwd) VALUES (?,?,?,?)";
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, fname);
			ps.setString(3, email);
			ps.setString(4, passwd);
			if(lname != null)
				ps.setString(2, lname);
			
			int r = ps.executeUpdate();
			
			if(r == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println(e.getErrorCode() + " : : " + e.toString());
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("wont get print ever");
		return false;
	
	}
	
	public User userExists(String email, String passwd) {
		
		String query = "SELECT uid, fname, lname, email FROM user WHERE email = ? AND passwd = ?";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, passwd);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			if(rs.getString("email").equalsIgnoreCase(email)) {
				User user = new User(rs.getInt("uid"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), passwd);
				return user;
			}
			
		} catch (SQLException e) {
			System.out.println(e.getErrorCode() + " : : " + e.toString());
			return null;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return null;
	}
	
	public User updateUser(int uid, String fname, String lname, String email, String passwd) {
		
		String query = "UPDATE user SET fname = ?, lname = ?, email = ?, passwd = ? WHERE uid = " + uid;
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, email);
			ps.setString(4, passwd);
			
			int r = ps.executeUpdate();
			
			if(r == 1) {
				return new User(uid, fname, lname, email, passwd);
			}
					
			
		} catch(SQLException s) {
			return null;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}		
		
		return null;
	}
}
