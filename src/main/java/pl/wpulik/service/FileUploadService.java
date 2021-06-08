package pl.wpulik.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
class FileUploadService {
	
	public String saveFile(MultipartFile file) {
		String filename = "src/main/resources/static/files/" + file.getOriginalFilename();
		try {
			byte[] bytes = file.getBytes();
			File fileUploaded = new File(filename);
			fileUploaded.createNewFile();
			BufferedOutputStream stream = 
					new BufferedOutputStream(new FileOutputStream(fileUploaded));
			stream.write(bytes);
			stream.close();
		}catch(IOException e) {
			System.err.println(e.getMessage());
		}	
		return filename;
	}
}
