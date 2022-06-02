package com.kye.springbootBlog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

	@GetMapping("/test/hello")
	public String hello() {
				
		
		return "성공 hello world ======= ";
	}
	
}
