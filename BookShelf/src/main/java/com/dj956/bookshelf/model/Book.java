package com.dj956.bookshelf.model;

import java.util.regex.Pattern;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.math.NumberUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Book {

	private static final String URL_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private static Pattern urlPattern = Pattern.compile(URL_REGEX);


	public Book() {

	}

	private int id;
	@NotNull(message="必須です")
	private String title;

	@NotNull(message="必須です")
	private String titleKana;

	@NotNull(message="必須です")
	private String author;

	private int publishDate;

	private String isbn;

	/**
	 * 本のカバー画像のパス(ファイルパスの場合と、URLの場合がある)
	 */
	private String coverPath;

	/**
	 * n巻
	 */
	@Min(0)
	@Max(8)
	private int index = -1;

	public int getId() {return id;}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {return title;}
	public void setTitle(String title) {
		this.title = title;

		var charArray = title.toCharArray();
		var numStr = "";
		for(char c : charArray) {
			var str = String.valueOf(c);
			if(NumberUtils.isDigits(str)) {
				numStr += str;
			}
		}

		if(NumberUtils.isDigits(numStr) && (index == -1 || index == 0)) {
			setIndex(Integer.parseInt(numStr));
		}
	}


	public String getTitleKana() {return titleKana;}
	public void setTitleKana(String titleKana) {
		this.titleKana = titleKana;
	}

	public String getAuthor() {return author;}
	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPublishDate() {return publishDate;}
	public void setPublishDate(int publishDate) {
		this.publishDate = publishDate;
	}


	public String getIsbn() {return isbn;}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getIndex() {return index;}
	public void setIndex(int index) {
		this.index = index;
	}

	public String getCoverPath() {return coverPath;	}
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	/**
	 * 本のカバー画像のパスがURLかどうか調べる
	 * @return
	 */
	@JsonIgnore
	public boolean isCoverPathUrl() {
		var matcher = urlPattern.matcher(getCoverPath());
		while(matcher.find()) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return String.format("ID:%d タイトル:%s タイトルカナ:%s 著者名:%s 出版日:%s ISBN:%s index:%d",
				getId(), getTitle(), getTitleKana(), getAuthor(), getPublishDate(), getIsbn(), getIndex());
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}
}
