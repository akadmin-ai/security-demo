package org.springframework.samples.petclinic.codeql;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class ExampleController {

	@GetMapping(value = "/find")
	public void find(@RequestParam("filename") String filename) throws IOException {

		Runtime.getRuntime().exec("/usr/bin/find . -iname " + filename);
	}

}
