package de.smava.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tomek Samcik
 *
 */
@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String smava(Map<String, Object> model) {
		return "index";
	}
	
}
