package com.dj956.bookshelf;

import java.util.ArrayList;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dj956.bookshelf.model.Book;
import com.dj956.bookshelf.model.Pageable;
import com.dj956.bookshelf.model.SearchForm;
import com.dj956.bookshelf.service.BookService;

@SpringBootApplication
@Validated
@Controller
public class BookShelfApplication extends SpringBootServletInitializer{

	@Autowired
	private BookService bookService;

	@Autowired
	private MessageSource messageSource;


	@RequestMapping("/")
	public String home(@RequestParam(name = "page", defaultValue = "0")int page, Model model) {
		var pageable = new Pageable(bookService.getCount(), page);
		model.addAttribute("books", bookService.getByOffset(pageable));
		model.addAttribute("page", page);
		model.addAttribute("pageable", pageable);

		return "index";
	}

	/**
	 * 本の追加画面に移動する
	 * @param model
	 * @return
	 */
	@RequestMapping("/registry_form")
	public String registryForm(Model model) {
		var genres = bookService.getAllGenre();
		model.addAttribute("genres", genres);
		model.addAttribute("book", new Book());
		return "registryForm";
	}

	/**
	 * 本の登録を行う
	 * @param book
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/registry", method=RequestMethod.POST)
	public String registry(@ModelAttribute @Valid Book book, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("error", result.getErrorCount());
			return "registryForm";
		}

		// check exists
		if(bookService.existsBook(book.getTitle(), book.getIsbn())) {
			model.addAttribute("isFailed", true);
			model.addAttribute("msg", messageSource.getMessage("alredy.exists", new String[] {book.getTitle()}, Locale.JAPAN));
			return registryForm(model);
		}

		var kana = book.getTitleKana();
		kana = kana.replace(" ", "");
		kana = kana.replace("　", "");
		kana = kana.replaceAll("\\d", "");
		book.setTitleKana(kana);

		bookService.registry(book);

		model.addAttribute("isFailed", false);
		model.addAttribute("msg", messageSource.getMessage("registry.success", new String[] {book.getTitle()}, Locale.JAPAN));

		return home(0, model);
	}


	/**
	 * 詳細情報を表示する
	 * @param bookId
	 * @param model
	 * @return
	 */
	@RequestMapping("/view")
	public String viewBook(@Valid @RequestParam("bookId") Integer bookId, Model model) {
		var book = bookService.getById(bookId);
		if(book == null) {
			model.addAttribute("isFailed", true);
			model.addAttribute("msg", messageSource.getMessage("book.notexists", new String[] {bookId.toString()}, Locale.JAPAN));
			return home(0, model);
		}

		model.addAttribute("book", book);
		model.addAttribute("genres", bookService.getAllGenre());
		return "viewBook";
	}


	/**
	 * 更新作業を行う
	 * @param book
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@Validated Book book, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("isFailed", true);
			model.addAttribute("msg", messageSource.getMessage("update.fail", new String[] {book.getTitle()}, Locale.JAPAN));
			return viewBook(book.getId(), model);
		}

		bookService.update(book);

		model.addAttribute("isFailed", false);
		model.addAttribute("msg", messageSource.getMessage("update.success", new String[] {book.getTitle()}, Locale.JAPAN));

		return home(0, model);
	}

	/**
	 * 削除を行う
	 * @param bookId
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(@Valid @RequestParam("bookId") Integer bookId, Model model) {
		var book = bookService.getById(bookId);
		if(book == null) {
			model.addAttribute("isFailed", true);
			model.addAttribute("msg", messageSource.getMessage("book.notexists", new String[] {bookId.toString()}, Locale.JAPAN));
			return home(0, model);
		}

		bookService.delete(bookId);

		model.addAttribute("isFailed", false);
		model.addAttribute("msg", messageSource.getMessage("delete.success", new String[] {book.getTitle()}, Locale.JAPAN));

		return home(0, model);
	}

	@RequestMapping("/search")
	public String searchForm(Model model) {
		var kanaList = new ArrayList<String>();
		var kanas = bookService.getTitles();
		kanas.stream().forEach(kana ->{
			kana = kana.replace(" ", "");
			kana = kana.replace("　", "");
			kana = kana.replaceAll("\\d", "");
			if(!kanaList.contains(kana)) {
				kanaList.add(kana);
			}
		});

		var authors = bookService.getAuthors();

		model.addAttribute("searchForm", new SearchForm());
		model.addAttribute("kanaList", kanaList);
		model.addAttribute("authors", authors);
		model.addAttribute("genres", bookService.getAllGenre());

		return "searchForm";
	}


	@RequestMapping("/search/result")
	public String searchResult(SearchForm form, Model model) {
		var books = bookService.search(form);

		boolean isFailed = books.size() > 0 ? false : true;

		model.addAttribute("isFailed", isFailed);
		model.addAttribute("msg", messageSource.getMessage("search.result", new String[] {String.valueOf(books.size())}, Locale.JAPAN));

		model.addAttribute("books", books);
		return "searchResult";
	}

	/**
	 * NAVBARのキーワード検索を行う
	 * @param keyword
	 * @param model
	 * @return
	 */
	@RequestMapping("/search/result/keyword")
	public String searchResult(@RequestParam("keyword") String keyword, Model model) {
		var books = bookService.searchByKeyword(keyword);

		model.addAttribute("isFailed", false);
		model.addAttribute("msg", messageSource.getMessage("search.result", new String[] {String.valueOf(books.size())}, Locale.JAPAN));

		model.addAttribute("books", books);
		return "searchResult";
	}


	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BookShelfApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(BookShelfApplication.class, args);
	}

}
