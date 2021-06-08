package pl.wpulik.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.wpulik.model.User;

@Service
public class UserService {
	 
	private UserCsvParser userCsvParser;
	private FileUploadService fileUploadService;
	private UserRepoService userRepoService;
	
	@Autowired
	public UserService(UserCsvParser userCsvParser, FileUploadService fileUploadService,
			UserRepoService userRepoService) {
		this.userCsvParser = userCsvParser;
		this.fileUploadService = fileUploadService;
		this.userRepoService = userRepoService;
	}
	
	public void uploadAndSave(MultipartFile file){
		String filename = fileUploadService.saveFile(file);
		File csvFile = new File(filename);
		List<User> usersToPersist = new ArrayList<>();
		try {
			usersToPersist = userCsvParser.csvRead(csvFile);
		}catch (FileNotFoundException e1) {
			System.err.println(e1.getMessage());
		}catch (IOException e2) {
			System.err.println(e2.getMessage());
		}
		userRepoService.saveAllUsers(usersToPersist);
	}
	
	public Page<User> findAllUsersSortedByDate(Pageable pageable){
		return userRepoService.findAllUsersSortedByDate(pageable);
	}
	
	public List<User> findTheOldestWithPhoneNo(){
		return userRepoService.findTheOldestWithPhoneNo();
	}
	
	public List<User> findByLastName(String lastName){
		return userRepoService.findByLastName(lastName);
	}
	
	public void removeUserById(Long id) {
		userRepoService.removeUser(id);
	}
	
	
}
