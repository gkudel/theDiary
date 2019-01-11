package org.gk.controllers;

import com.google.common.base.MoreObjects;
import org.gk.business.data.model.Entry;
import org.gk.business.data.repositories.interfaces.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping({ "/", "/home"} )
@Transactional()
public class HomeController {

    private EntryRepository entryRepository;

    @Autowired
    public HomeController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute(MoreObjects.firstNonNull(entryRepository.findTop7ByOrderByTimeDesc(), new ArrayList<Entry>()));
        //model.addAttribute(MoreObjects.firstNonNull(entryRepository.findAll(EntrySpecification.hasTitle("Title9")), new ArrayList<Entry>()));
        //model.addAttribute(MoreObjects.firstNonNull(entryRepository.findAll(QEntry.entry.), new ArrayList<Entry>()));
        return "home/home";
    }
}
