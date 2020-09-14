package com.dj956.bookshelf.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import com.dj956.bookshelf.model.Book;
import com.dj956.bookshelf.service.BookService;
import com.dj956.bookshelf.utils.BookFactory;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class BookApiController {
	private static final String CONTENT_DISPOSITION_FORMAT = "attachment; filename=\"%s\"; filename*=UTF-8''%s";

	@Autowired
	private BookService bookService;

	@RequestMapping(path = "/api/bookinfo", method=RequestMethod.GET)
	public Book bookInfo(@RequestParam("isbn") String isbn) {
		var book = BookFactory.buildByISBN(isbn);

		return book;
	}


	@RequestMapping(path = "/api/write2json", method=RequestMethod.GET)
	public ResponseEntity<byte[]> writeJson(Model model) {
		var books = bookService.getAll();

		var mapper = new ObjectMapper();
		var writer = mapper.writer(new DefaultPrettyPrinter());

		try {
			String json = writer.writeValueAsString(books);

			String fileName = "books.json";
			String headerValue = String.format(CONTENT_DISPOSITION_FORMAT, fileName,
					UriUtils.encode(fileName, StandardCharsets.UTF_8.name()));
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);

			return new ResponseEntity<>(json.getBytes("MS932"), headers, HttpStatus.OK);

		}catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
