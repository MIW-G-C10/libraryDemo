package nl.miwgroningen.ch10.vincent.libraryDemo.controller;

import nl.miwgroningen.ch10.vincent.libraryDemo.model.Book;
import nl.miwgroningen.ch10.vincent.libraryDemo.repository.AuthorRepository;
import nl.miwgroningen.ch10.vincent.libraryDemo.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

/**
 * @author Vincent Velthuizen <v.r.velthuizen@pl.hanze.nl>
 * <p>
 * Geeft toegang tot alle pagina's over boeken
 */

@Controller
public class BookController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping({"/books/all", "/"})
    protected String showBookOverview(Model model) {
        model.addAttribute("allBooks", bookRepository.findAll());

        return "bookOverview";
    }

    @GetMapping("/books/details/id/{bookId}")
    protected String showBookDetails(@PathVariable("bookId") Long bookId, Model model) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            return showDetailsForBook(model, book);
        }

        return "redirect:/books/all";
    }

    private static String showDetailsForBook(Model model, Optional<Book> book) {
        model.addAttribute("bookToShowDetailsFor", book.get());
        return "bookDetails";
    }

    @GetMapping("/books/details/{title}")
    protected String showBookDetails(@PathVariable("title") String title, Model model) {
        Optional<Book> book = bookRepository.findByTitle(title);

        if (book.isPresent()) {
            return showDetailsForBook(model, book);
        }

        return "redirect:/books/all";
    }

    @GetMapping("/books/new")
    protected String showNewBookForm(Model model) {
        return showBookFormForBook(model, new Book());
    }

    @GetMapping("/books/edit/{bookId}")
    protected String showEditBookForm(@PathVariable("bookId") Long bookId, Model model) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            return showBookFormForBook(model, book.get());
        }

        return "redirect:/books/all";
    }

    private String showBookFormForBook(Model model, Book book) {
        model.addAttribute("book", book);
        model.addAttribute("allAuthors", authorRepository.findAll());
        return "bookForm";
    }

    @PostMapping("/books/new")
    protected String saveBook(@ModelAttribute("book") Book bookToBeSaved, BindingResult result) {
        if (!result.hasErrors()) {
            bookRepository.save(bookToBeSaved);
        }
        return "redirect:/books/all";
    }

    @GetMapping("/books/delete/{bookId}")
    protected String deleteBook(@PathVariable("bookId") Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            bookRepository.delete(book.get());
        }

        return "redirect:/books/all";
    }


}
