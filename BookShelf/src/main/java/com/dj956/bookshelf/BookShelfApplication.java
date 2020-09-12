package com.dj956.bookshelf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dj956.bookshelf.model.Book;
import com.dj956.bookshelf.service.BookService;

@SpringBootApplication
@Controller
public class BookShelfApplication {

	@Autowired
	private BookService bookService;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("list", bookService.getAll());

		return "index";
	}

	@RequestMapping("/registry_form")
	public String registryForm(Model model) {
		model.addAttribute("book", new Book());

		return "registry_form";
	}

	@RequestMapping(value="/registry", method=RequestMethod.POST)
	public String registry(@ModelAttribute @Validated Book book, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("error", result.getErrorCount());
			return "registry_form";
		}

		bookService.registry(book);
		model.addAttribute("list", bookService.getAll());

		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(BookShelfApplication.class, args);
	}

}
