package pl.wpulik.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.User;

@Service
class UserCsvParser {

	private static final String[] HEADERS = { "first_name", "last_name", "birth_date", "phone_no" };
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d");

	private Validator validator;

	@Autowired
	public UserCsvParser(Validator validator) {
		this.validator = validator;
	}

	public List<User> csvRead(File file) throws IOException, FileNotFoundException {
		List<User> csvUsers = new ArrayList<>();
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';')
				.withHeader(HEADERS)
				.withFirstRecordAsHeader().parse(in);	
		
		for (CSVRecord record : records) {
			String firstName = setProperName(record.get("first_name"));
			String lastName = setProperName(record.get("last_name"));
			LocalDate birthDate = parseToLocalDate(record.get("birth_date"));
			Integer phoneNumber = parseToInteger(record.get("phone_no"));
			User user = new User(firstName, lastName, birthDate, phoneNumber);
			if (verifyUserInput(user))
				csvUsers.add(new User(firstName, lastName, birthDate, phoneNumber));
		}
		return csvUsers;
	}

	private boolean verifyUserInput(User user) {
		Set<ConstraintViolation<User>> errors = validator.validate(user);
		if (!errors.isEmpty()) {
			errors.forEach(error -> System.err.println(error.getMessage()));
			return false;
		} else {
			return true;
		}
	}

	private Integer parseToInteger(String input) {
		Integer result = null;
		try {
			result = Integer.parseInt(input);
		} catch (Exception e) {
			System.err.println("Input is not a number. "
					+ "User has been persisted in database without phone number. " 
					+ e.getMessage());
			return null;
		}
		return result;
	}
	
	private String setProperName(String input) {
		String result = null;
		if(!input.isBlank()) {
			result = input.trim();
			result = result.toLowerCase();
			Character firstChar = result.charAt(0);
			StringBuilder sb = new StringBuilder(result);
			sb.setCharAt(0, Character.toUpperCase(firstChar));
			result = sb.toString();
		}
		return result;
	}
	
	private LocalDate parseToLocalDate(String input) {
		if(input.isBlank())
			return null;
		return LocalDate.parse(input, formatter);
	}

}
