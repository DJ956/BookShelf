package com.dj956.bookshelf.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dj956.bookshelf.model.Book;


@Mapper
public interface BookDAO {

	List<Book> selectAll();
	void registry(Book book);

}
