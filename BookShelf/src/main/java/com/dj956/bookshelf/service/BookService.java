package com.dj956.bookshelf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dj956.bookshelf.dao.BookDAO;
import com.dj956.bookshelf.model.Book;
import com.dj956.bookshelf.model.Pageable;
import com.dj956.bookshelf.model.SearchForm;

@Service
public class BookService {

	@Autowired
	private BookDAO bookDAO;

	public List<Book> getAll(){
		return bookDAO.selectAll();
	}

	public int getCount() {
		return bookDAO.selectCount();
	}

	public List<Book> getByOffset(Pageable pageable){
		return bookDAO.selectIndex(pageable);
	}

	public Book getById(int id) {
		return bookDAO.selectById(id);
	}

	public void registry(Book book) {
		bookDAO.registry(book);
	}

	public void update(Book book) {
		bookDAO.updateBook(book);
	}

	public void delete(int id) {
		bookDAO.deleteBook(id);
	}

	/**
	 * 重複しないタイトルを取得
	 * @return
	 */
	public List<String> getTitles(){
		return bookDAO.selectTitleKana();
	}

	/**
	 * 修復しない著者名を取得
	 * @return
	 */
	public List<String> getAuthors(){
		return bookDAO.selectAuthor();
	}

	public List<Book> search(SearchForm form){
		return bookDAO.search(form);
	}

	public List<Book> searchByKeyword(String keyword){
		return bookDAO.searchByKeyword(keyword);
	}

	public boolean existsBook(String title, String isbn) {
		return bookDAO.existsBook(title, isbn) > 0;
	}

}
