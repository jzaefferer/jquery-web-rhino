package de.bassistance.blog.domain;

import java.util.Date;

public class Comment {

	private String author;

	private String url;

	private String email;

	private String body;

	private Date date;

	public Comment(String author, String body, Date date) {
		this.author = author;
		this.body = body;
		this.date = date;
	}
	
	public Comment(String author, String url, String email, String body, Date date) {
		this(author, body, date);
		this.url = url;
		this.email = email;
	}
	
	public Comment() {
		date = new Date();
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
		return author;
	}

	public String getBody() {
		return body;
	}

	public Date getDate() {
		return date;
	}

	public String getEmail() {
		return email;
	}

	public String getUrl() {
		return url;
	}

}
