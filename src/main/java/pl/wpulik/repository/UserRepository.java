package pl.wpulik.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u ORDER BY u.birthDate ASC")
	Page<User> findAllUsersSortedByDate(Pageable pageable);
	
	@Query("SELECT MIN(u.birthDate) FROM User u WHERE u.phoneNumber !=null")
	Optional<LocalDate> getOldest();
	
	@Query(value = "SELECT u.* FROM User u WHERE BIRTH_DATE=:bdate", nativeQuery=true)
	List<User> findOldestUserWithPhoneNo(@Param("bdate") LocalDate bdate);
	
	List<User> findByLastName(String lastName);

}
