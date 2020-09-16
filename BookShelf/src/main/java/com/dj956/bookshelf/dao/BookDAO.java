package com.dj956.bookshelf.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dj956.bookshelf.model.Book;
import com.dj956.bookshelf.model.Pageable;
import com.dj956.bookshelf.model.SearchForm;



@Mapper
public interface BookDAO {

	List<Book> selectAll();
	int selectCount();
	List<Book> selectIndex(Pageable pageable);
	Book selectById(int id);

	List<String> selectTitleKana();
	List<String> selectAuthor();

	void registry(Book book);
	void updateBook(Book book);
	void deleteBook(int id);

	List<Book> search(SearchForm form);
	List<Book> searchByKeyword(String keyword);

	int existsBook(String title, String isbn);
}
