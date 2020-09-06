package com.dj956.bookshelf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dj956.bookshelf.service.BookService;
import com.dj956.bookshelf.utils.BookFactory;

@SpringBootApplication
@Controller
public class BookShelfApplication {

	@Autowired
	private BookService bookService;

	@RequestMapping("/")
	public String home(Model model) {
		var book = BookFactory.buildByISBN("");

		model.addAttribute("list", bookService.getAll());

		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(BookShelfApplication.class, args);
	}

}
