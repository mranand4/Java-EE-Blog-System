package beans;

import java.io.Serializable;

public class User implements Serializable {
	
	private int uid;
	private String fname;
	private String lname;
	private String email;
	private String passwd;
	
	public User() {}

	public User(int uid, String fname, String lname, String email, String passwd) {
		this.uid = uid;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.passwd = passwd;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public String toString() {
		return "BeanUser [uid=" + uid + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", passwd="
				+ passwd + "]";
	}
	
	
	
	
	
	
}
