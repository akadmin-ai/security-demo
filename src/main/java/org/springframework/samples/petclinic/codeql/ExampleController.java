package org.springframework.samples.petclinic.codeql;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class ExampleController {

	private static final Set<String> ALLOWED_FILENAMES = Set.of("file1.txt", "file2.txt", "file3.txt");

	@GetMapping(value = "/find")
	public void find(@RequestParam("filename") String filename) throws IOException {
		if (ALLOWED_FILENAMES.contains(filename)) {
			Runtime.getRuntime().exec("/usr/bin/find . -iname " + filename);
		} else {
			throw new IllegalArgumentException("Invalid filename");
		}
	}

}
