package beans;

import java.io.Serializable;

public class MostViewedPost implements Serializable {
	
	private int postId;
	private int viewCount;
	private String title;
	
	public MostViewedPost() {}

	public MostViewedPost(int postId, int viewCount, String title) {
		this.postId = postId;
		this.viewCount = viewCount;
		this.title = title;
	}
	
	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "MostViewedPost [postId=" + postId + ", viewCount=" + viewCount + ", title=" + title + "]";
	}
	
	

}
