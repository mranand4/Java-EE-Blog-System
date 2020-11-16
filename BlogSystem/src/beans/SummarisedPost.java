package beans;

import java.io.Serializable;

public class SummarisedPost implements Serializable {
	
	private int postId;
	private String title;
	private String date;
	private String fname;
	private String lname;
	private String summary;
	
	public SummarisedPost() {}

	public SummarisedPost(int postId, String title, String date, String fname, String lname, String summary) {
		this.postId = postId;
		this.title = title;
		this.date = date;
		this.fname = fname;
		this.lname = lname;
		this.summary = summary;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
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

	public String getSummary() {
		if(summary == null)
			return "";
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
	
	

}
