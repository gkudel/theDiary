package org.gk.controllers;

import org.gk.business.data.repositories.interfaces.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({ "/", "/home"} )
public class HomeController {

    private EntryRepository entryRepository;

    @Autowired
    public HomeController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute(entryRepository.findTop7ByOrderByTimeDesc());
        return "home/home";
    }
}
