package pl.wpulik.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pl.wpulik.model.User;
import pl.wpulik.repository.UserRepository;

@Service
class UserRepoService {
	
	private UserRepository userRepository;

	@Autowired
	public UserRepoService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void saveAllUsers(List<User> input){
		for(User u : input) {
			try {
			userRepository.save(u);
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public Page<User> findAllUsersSortedByDate(Pageable pageable){
		return userRepository.findAllUsersSortedByDate(pageable);
	}
	
	public List<User> findTheOldestWithPhoneNo(){
		LocalDate birthDate = userRepository.getOldest().get();
		return userRepository.findOldestUserWithPhoneNo(birthDate);
	}
	
	public List<User> findByLastName(String lastName){
		return userRepository.findByLastName(lastName);
	}
	
	public void removeUser(Long id) {
		userRepository.deleteById(id);
		System.out.println("User with id=" + id + " has been removed.");
	}
	
	

}
