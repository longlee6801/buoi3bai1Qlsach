package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import com.example.demo.services.BookService;
import com.example.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllCategories(Model model){
        List<Category> categories = categoryService.getAllCategories();
        categories  = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model){
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, Model model){
        // Truong hop co loi rang buoc thi tra lai view add

        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editBookFrom(@PathVariable("id") Long id, Model model){
        Category find = categoryService.getCategoryById(id);
        if(find != null){
            model.addAttribute("category",find);
            return "category/edit";
        }else{
            return "not-found";
        }
    }

    @PostMapping("/edit")
    public String editBook(@Valid Category updateCategory, Errors errors, Model model){
        if (errors != null && errors.getErrorCount() > 0) {
            return "category/edit";
        }
        else
            categoryService.updateCategory(updateCategory);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooks(@PathVariable("id") Long id){
        Category find = categoryService.getCategoryById(id);
        if(find!=null){
            categoryService.deleteCategory(id);
            return "redirect:/categories";
        }
        return "not-found";
    }

//    @GetMapping("/edit/{id}")
//    public String editBook(@PathVariable("id") Long id, Model model) {
//        Book book = bookService.getBookById(id);
//        if (book != null){
//            model.addAttribute("book", book);
//            model.addAttribute("categories", categoryService.getAllCategories());
//            return "book/edit";
//        }else {
//            return "not-found";
//        }
//    }
//    @PostMapping("/edit")
//    public String editBook(@ModelAttribute("book") Book book){
//        bookService.updateBook(book);
//        return "redirect:/books";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteBook(@PathVariable("id") Long id){
//        Book book = bookService.getBookById(id);
//        bookService.deleteBook(id);
//        return "redirect:/books";
//    }


}
