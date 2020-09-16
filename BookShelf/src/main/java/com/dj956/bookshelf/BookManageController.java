package com.dj956.bookshelf;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dj956.bookshelf.model.Book;
import com.dj956.bookshelf.model.FileUploadForm;
import com.dj956.bookshelf.model.Pageable;
import com.dj956.bookshelf.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BookManageController {

	@Autowired
	private BookService bookService;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 管理画面に移動する
	 * @param model
	 * @return
	 */
	@RequestMapping("/manage")
	public String manage(int page, Model model) {
		var pageable = new Pageable(bookService.getCount(), page);

		var books = bookService.getByOffset(pageable);

		model.addAttribute("pageable", pageable);
		model.addAttribute("books", books);
		model.addAttribute("form", new FileUploadForm());
		return "bookManage";
	}


	/**
	 * Jsonを読み込んでDBに登録する
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/manage/json/registry", method=RequestMethod.POST)
	public String manageJsonRegistry(List<Book> books, Model model) {
		int registryCount = 0;
		for(var book : books) {
			//check exists
			if(bookService.existsBook(book.getTitle(), book.getIsbn())) {continue;}

			bookService.registry(book);
			registryCount++;
		}

		if(registryCount != 0) {
			model.addAttribute("isFailed", false);
			model.addAttribute("msg", messageSource.getMessage("registry.success", new String[] {String.valueOf(registryCount)}, Locale.JAPAN));
		}else {
			int unRegistryCnt = books.size() - registryCount;
			model.addAttribute("isFailed", true);
			model.addAttribute("msg", messageSource.getMessage("alredy.exists", new String[] {unRegistryCnt + "件"}, Locale.JAPAN));
		}

		return manage(0, model);
	}

	/**
	 * Jsonファイルを読み込んで表示する
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/file/upload4json", method=RequestMethod.POST)
	public String loadJson(FileUploadForm form, Model model) {
		var file = form.getFile();

		List<Book> books = new ArrayList<Book>();
		String fileName = file.getOriginalFilename();
		try(var in = new InputStreamReader(file.getInputStream(), "Shift-JIS")) {
			var mapper = new ObjectMapper();
			books = mapper.readValue(in, new TypeReference<List<Book>>() {});
			model.addAttribute("isFailed", false);
			model.addAttribute("msg", messageSource.getMessage("load.json.success", new String[] {fileName}, Locale.JAPAN));

			return manageJsonRegistry(books, model);
		}catch(IOException e) {
			model.addAttribute("isFailed", true);
			model.addAttribute("msg", messageSource.getMessage("load.json.fail", new String[] {fileName, e.getMessage()}, Locale.JAPAN));
			return manage(0, model);
		}
	}

}
