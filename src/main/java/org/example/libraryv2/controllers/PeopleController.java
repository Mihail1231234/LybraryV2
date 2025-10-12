package org.example.libraryv2.controllers;

import org.example.libraryv2.model.Person;
import org.example.libraryv2.services.BookService;
import org.example.libraryv2.services.PeopleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BookService bookService;

    public PeopleController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        model.addAttribute("people", peopleService.getPeople(page,size));
        model.addAttribute("currentPage", peopleService.getPeople(page,size).getNumber());
        model.addAttribute("size",size);
        return "person/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id,Model model){
        model.addAttribute("person",peopleService.findOne(id));
        model.addAttribute("book",peopleService.getBook(id));
        return "person/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person",new Person());
        return "person/new";
    }

    @PostMapping
    public String save(@ModelAttribute Person person,BindingResult bindingResult){
        if (bindingResult.hasErrors())return "person/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/update")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("person",peopleService.findOne(id));
        return "person/edit";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute Person person,BindingResult bindingResult,@PathVariable("id")int id){
        if (bindingResult.hasErrors()) return "people/edit";
        peopleService.updatePerson(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
