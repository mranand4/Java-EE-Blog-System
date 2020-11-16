package beans;

import java.io.Serializable;

public class BlogPost implements Serializable {
	
	private int postId;
	private int uid;
	private String title;
	private String date;
	private String fname;
	private String lname;
	private String body;
	
	public BlogPost() {}
	
	public BlogPost(int postId, int uid, String title, String date, String fname, String lname, String body) {
		this.postId = postId;
		this.uid = uid;
		this.title = title;
		this.date = date;
		this.fname = fname;
		this.lname = lname;
		this.body = body;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		if(lname == null)
			return "";
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getBody() {
		if(body == null)
			return body;
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	
	
	

}
