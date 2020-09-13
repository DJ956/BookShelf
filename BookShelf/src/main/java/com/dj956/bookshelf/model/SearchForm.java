package com.dj956.bookshelf.model;

public class SearchForm {

	public SearchForm() {

	}

	private String title;
	private String author;
	private String isbn;
	private String titleOp;
	private String authorOp;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitleOp() {
		return titleOp;
	}

	public void setTitleOp(String titleOp) {
		this.titleOp = titleOp;
	}

	public String getAuthorOp() {
		return authorOp;
	}

	public void setAuthorOp(String authorOp) {
		this.authorOp = authorOp;
	}


	@Override
	public String toString() {
		return "SearchForm [title=" + title + ", author=" + author + ", isbn=" + isbn + ", titleOp=" + titleOp
				+ ", authorOp=" + authorOp + "]";
	}

}
