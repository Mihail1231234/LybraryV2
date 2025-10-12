package org.example.libraryv2.controllers;

import org.example.libraryv2.model.Book;
import org.example.libraryv2.model.Person;
import org.example.libraryv2.repositories.BookRepository;
import org.example.libraryv2.services.BookService;
import org.example.libraryv2.services.PeopleService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;
    private final BookRepository bookRepository;

    public BookController(BookService bookService, PeopleService peopleService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.peopleService = peopleService;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public String  getBooks(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(required = false) String sort){

        Page<Book> booksPage = bookService.getBooksPage(page, size, sort);
        model.addAttribute("book", booksPage);
        model.addAttribute("currentPage",booksPage.getNumber());
        model.addAttribute("size", size);
        model.addAttribute("sort",sort);
        return "book/index";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String search){
        model.addAttribute("books",bookService.findBookByBookTitle(search));
        return "book/search";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model){
        model.addAttribute("person",new Person());
        model.addAttribute("owner",bookService.findPerson(id));
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("bookCheck", bookService.isBookOverdue(bookService.findOne(id)));
        model.addAttribute("peop",peopleService.findAll());
        return "book/show";
    }

    @PostMapping("/{id}/save")
    public String savePerson(@ModelAttribute("person")Person person,BindingResult bindingResult,@PathVariable int id){
        /*Book book=bookService.findOne(id);
        book.setOwner(bookService.findPerson(person.getId()));
        bookService.update(id,book);*/
        bookService.assignOwnerToBook(id,person.getId());

        return "redirect:/book/{id}";
    }

    @PostMapping("/{id}/release")
    public String deletePer(@PathVariable int id){
        bookService.delPer(id);
        return "redirect:/book/{id}";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book",new Book());
        return "book/new";
    }

    @PostMapping()
    public String save(@ModelAttribute("book") Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "book/new";
        bookService.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id")int id){
        model.addAttribute("book",bookService.findOne(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book")Book bookUpdate,@PathVariable("id") int id,BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "book/edit";
        bookService.update(id,bookUpdate);
        return "redirect:/book/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        bookService.delete(id);
        return "redirect:/book";
    }
}
