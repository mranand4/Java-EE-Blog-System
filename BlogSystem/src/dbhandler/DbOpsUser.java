package dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.User;

public class DbOpsUser {
	
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
	
	public boolean createNewUser(String fname, String lname, String email, String passwd) {
		
		String query = "INSERT INTO user (fname, lname, email, passwd) VALUES (?,?,?,?)";
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, email);
			ps.setString(4, passwd);		
			
			return ps.executeUpdate() == 1;
			
		} catch (SQLException e) {
			
			System.out.println(e.getErrorCode() + " :: " + e.toString());
		
		} finally {
			
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	
	}
	
	public User userExists(String email, String passwd) {
		
		String query = "SELECT uid, fname, lname, email FROM user WHERE email = ? AND passwd = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = conn.prepareStatement(query);
			
			ps.setString(1, email);
			ps.setString(2, passwd);
			
			rs = ps.executeQuery();
			rs.next();
			
			if(rs.getString("email").equalsIgnoreCase(email)) {
				User user = new User(rs.getInt("uid"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), passwd);
				return user;
			}
			
		} catch (SQLException e) {
			
			System.out.println(e.getErrorCode() + " :: " + e.toString());
		
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
			
			ps = conn.prepareStatement(query);
			
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, email);
			ps.setString(4, passwd);
			
			return ps.executeUpdate() == 1 ? new User(uid, fname, lname, email, passwd) : null;
						
		} catch(SQLException e) {
		
			System.out.println(e.getErrorCode() + " :: " + e.toString());
		
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
