package pl.wpulik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pl.wpulik.service.UserService;



@Controller
public class FileUploadController {
	
	
	private UserService userService;
	
	@Autowired
	public FileUploadController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/uploadcsvfile")
	public String fileUpload(@RequestParam MultipartFile file) {
		try {
			userService.uploadAndSave(file);
			return "redirect:/userlist";
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return "redirect:/";
		}
	}

}
