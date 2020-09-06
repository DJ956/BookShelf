package com.dj956.bookshelf.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dj956.bookshelf.model.Book;
import com.dj956.bookshelf.utils.BookFactory;

@RestController
public class BookApiController {

	@RequestMapping(path = "/api/bookinfo", method=RequestMethod.GET)
	public Book bookInfo(@RequestParam("isbn") String isbn) {
		var book = BookFactory.buildByISBN(isbn);

		return book;
	}

}
