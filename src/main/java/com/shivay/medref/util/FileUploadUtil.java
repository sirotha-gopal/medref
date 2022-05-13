package com.shivay.medref.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	
	 public static void saveFile(String uploadDir, String fileName,
	            MultipartFile multipartFile) throws IOException {
		 	
		 	System.out.println();
		 	System.out.println();
		 	System.out.println();
		 	System.out.println();
		 	System.out.println();
		 	System.out.println();
		 	System.out.println();
		 	System.out.println();
		 	System.out.println();
		 	System.out.println();
		 	System.out.println();
		 
	        Path uploadPath = Paths.get(uploadDir);
	        
	        System.out.println("Upload Dir::"+uploadDir);
	        System.out.println("Upload Path::"+uploadPath);
	         
	        if (!Files.exists(uploadPath)) {
	            Path createDirectories = Files.createDirectories(uploadPath);
	            System.out.println("Created Directories::"+createDirectories);
	        }else {
	        	System.out.println("File Path Already Exists!");
	        }
	         
	        try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            System.out.println("File Path::"+filePath);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {        
	            throw new IOException("Could not save image file: " + fileName, ioe);
	        }      
	    }
}
