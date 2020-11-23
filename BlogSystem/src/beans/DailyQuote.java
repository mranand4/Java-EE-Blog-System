package beans;

public class DailyQuote {
	
	private String auth;
	private String quote;
	private int quoteId;
	
	public DailyQuote() {
		auth = "Anonymous";
		quote = "Everything will be all right !";
		quoteId = 1;
	}
	
	public DailyQuote(String auth, String quote, int quoteId) {
		this.auth = auth;
		this.quote = quote;
		this.quoteId = quoteId;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}
	
	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
	}
	
	public int getQuoteId() {
		return quoteId;
	}
	

}
