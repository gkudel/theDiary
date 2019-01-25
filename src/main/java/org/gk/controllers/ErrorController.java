package org.gk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {

	@RequestMapping(value = "*", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD, RequestMethod.DELETE})
	public String getErrorPage() {
		return "error/error.html";
	}
}
