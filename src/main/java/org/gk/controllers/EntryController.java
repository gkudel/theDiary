package org.gk.controllers;


import org.gk.business.data.model.Entry;
import org.gk.business.data.model.QEntry;
import org.gk.business.data.repositories.interfaces.EntryRepository;
import org.gk.exceptions.ResourcesNotFound;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Optional;

@Controller
@RequestMapping("entry")
@Transactional
public class EntryController {

	private final EntryRepository entryRepository;

	public EntryController(EntryRepository entryRepository) {
		this.entryRepository = entryRepository;
	}

	@RequestMapping(value = "/get/{id:[\\d]+}", method = RequestMethod.GET)
	public String getEntry(@PathVariable long id, Model model) {
		Optional<Entry> entry =  entryRepository.findOne(QEntry.entry.id.eq(id));
		if(entry.isPresent()) {
			model.addAttribute(entry.get());
			return "entry/details";
		}
		throw new ResourcesNotFound();
	}
}
