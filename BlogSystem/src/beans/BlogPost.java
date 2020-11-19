package beans;

import java.io.Serializable;

public class BlogPost implements Serializable {
	
	private int postId;
	private int uid;
	private String title;
	private String date;
	private String odate;
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

	private String getOrdinalSuffixOf(int i) {
		
	    int j = i % 10;
	    int k = i % 100;
	    
	    if (j == 1 && k != 11) {
	        return i + "st";
	    }
	    if (j == 2 && k != 12) {
	        return i + "nd";
	    }
	    if (j == 3 && k != 13) {
	        return i + "rd";
	    }
	    
	    return i + "th";
	}

	public String getOdate() {
		
		String d = date;
		
		String yy = d.substring(0, d.indexOf('-'));
		String mm = d.substring(d.indexOf('-') + 1, d.lastIndexOf('-'));
		String dd = d.substring(d.lastIndexOf('-') + 1);
		
		String months[] = new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		
		String rd = getOrdinalSuffixOf(Integer.parseInt(dd)) + " " + months[Integer.parseInt(mm) - 1] + ", " + yy;

		return rd;
		
		
	}
	
	
	

}
