package com.dj956.bookshelf.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.math.NumberUtils;

import com.dj956.bookshelf.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BookFactory {

	private static final String API_URL = "https://api.openbd.jp/v1/get?isbn=%s&pretty";

	/**
	 * Openbdからjsonデータを取得
	 * @param isbn
	 * @return
	 */
	private static String loadJson(String isbn) {
		isbn = isbn.trim();
		String urlStr = String.format(API_URL, isbn);
		var builder = new StringBuilder();

		try {
			var url = new URL(urlStr);
			var con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.connect();

			int statusCode = con.getResponseCode();
			if(statusCode != HttpURLConnection.HTTP_OK) {
				throw new IOException("connection failed");
			}

			try(var reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String line;
				while((line = reader.readLine()) != null) {
					builder.append(line);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}

		}catch(IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}


	private static JsonNode getNode(String isbn) {
		String jsonStr = loadJson(isbn);
		JsonNode node = null;
		try {
			var mapper = new ObjectMapper();
			node = mapper.readTree(jsonStr);
			if(node.size() < 1) {
				return null;
			}

			node = node.get(0);
			if(!node.has("summary") || !node.has("onix")) {
				return null;
			}

		}catch(JsonProcessingException  e) {
			e.printStackTrace();
		}

		return node;
	}

	/**
	 * ISBN番号からBookインスタンスを生成する
	 * @param isbn
	 * @return
	 */
	public static Book buildByISBN(String isbn) {
		Book book = null;

		var node = getNode(isbn);
		if(node == null) {
			return book;
		}

		var summary = node.get("summary");

		var title = summary.get("title").asText();
		var author = summary.get("author").asText();

		var pubdateStr = summary.get("pubdate").asText();
		var pubdate = NumberUtils.isDigits(pubdateStr) ? summary.get("pubdate").asInt() : 0;
		var cover = summary.get("cover").asText();

		var details = node.get("onix").get("DescriptiveDetail");
		var titleDetail = details.get("TitleDetail").get("TitleElement").get("TitleText");
		var titleKana = titleDetail.get("collationkey").asText().trim();


		book = new Book();
		book.setTitle(title);
		book.setTitleKana(titleKana);
		book.setAuthor(author);
		book.setPublishDate(pubdate);
		book.setIsbn(isbn);
		book.setCoverPath(cover);

		return book;
	}


}
