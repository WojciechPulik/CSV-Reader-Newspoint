package pl.wpulik.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.model.User;
import pl.wpulik.service.UserService;

@Controller
public class HomeController {
	
	private UserService userService;
	
	@Autowired
	public HomeController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/userlist")
	public String getUsers(Model model, 
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		
		Page<User> usersPage = userService.findAllUsersSortedByDate(
				PageRequest.of(currentPage - 1, pageSize));
		model.addAttribute("usersPage", usersPage);
		int totalPages = usersPage.getTotalPages();
		if(totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("totalUsers", usersPage.getTotalElements());
		model.addAttribute("oldestWithPhone", userService.findTheOldestWithPhoneNo());
		return "userlist";
		
	}
	
	@PostMapping("/findbylastname")
	public String getByName(Model model, @RequestParam String lastName) {
		return lastNameSearchResults(model, lastName);
	}
	@GetMapping("/lastname/")
	public String lastNameSearchResults(Model model, @RequestParam String lastName) {
		model.addAttribute("resultList", userService.findByLastName(lastName));
		return "searchresult";
	}
	
	@GetMapping("/removeuser/{id}")
	public String removeUser(@PathVariable Long id) {
		userService.removeUserById(id);
		return "redirect:/userlist";
	}

}
