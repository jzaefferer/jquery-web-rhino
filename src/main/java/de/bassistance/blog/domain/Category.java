package de.bassistance.blog.domain;

public class Category implements Comparable<Category> {

	private String id;

	private String name;

	private String title;

	public Category(String id, String name, String title) {
		this.id = id;
		this.name = name;
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public int compareTo(Category o) {
		return name.compareTo(o.name);
	}

	public String getId() {
		return id;
	}

}
