package com.dj956.bookshelf;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dj956.bookshelf.service.BookService;

@Controller
@Validated
public class BookViewController {

	@Autowired
	private BookService bookService;

	@RequestMapping("/view")
	public String viewBook(@Valid @RequestParam("bookId") Integer bookId, Model model) {
		var book = bookService.getById(bookId);
		if(book == null) {
			return "index";
		}

		System.out.println(book);

		model.addAttribute("book", book);
		return "viewBook";
	}
}
