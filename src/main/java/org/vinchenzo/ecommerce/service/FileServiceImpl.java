package org.vinchenzo.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    public String uploadImage(String path, MultipartFile file) throws IOException {
        // Get the original file name
        String originalFilename = file.getOriginalFilename();

        // Validate the file name
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("Invalid file name: " + originalFilename);
        }

        // Generate a unique file name with the same extension
        String randomId = UUID.randomUUID().toString();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String fileName = randomId + fileExtension;

        // Construct the file path using the correct separator
        String filePath = path + File.separator + fileName;

        // Ensure the directory exists
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directories if they don't exist
        }

        // Save the file to the specified path
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
