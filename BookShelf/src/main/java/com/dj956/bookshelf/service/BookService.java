package com.dj956.bookshelf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dj956.bookshelf.dao.BookDAO;
import com.dj956.bookshelf.model.Book;

@Service
public class BookService {

	@Autowired
	private BookDAO bookDAO;

	public List<Book> getAll(){
		return bookDAO.selectAll();
	}

	public void registry(Book book) {
		bookDAO.registry(book);
	}

}
