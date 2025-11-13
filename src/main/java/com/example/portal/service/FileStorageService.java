// package com.example.portal.service;

// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;
// import java.io.IOException;
// // import io.jsonwebtoken.io.IOException;

// @Service
// public class FileStorageService {

//     @Value("${upload.dir}")
//     private String uploadDir;

//     public String store(MultipartFile file) throws java.io.IOException {
//         try {
//             Path dirPath = Paths.get(uploadDir);
//             if (!Files.exists(dirPath)) {
//                 Files.createDirectories(dirPath);
//             }

//             String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//             Path filePath = dirPath.resolve(filename);
//             Files.copy(file.getInputStream(), filePath);

//             return "/uploads/foto/" + filename;
//         } catch (IOException e) {
//             throw new RuntimeException("Gagal menyimpan file: " + e.getMessage());
//         }
//     }
// }

package com.example.portal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    @Value("${upload.dir}")
    private String uploadDir;

    public String store(MultipartFile file) {
        try {
            Path dirPath = Paths.get(uploadDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/foto/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file: " + e.getMessage());
        }
    }

    public boolean delete(String relativeUrl) {
        try {
            if (relativeUrl == null || relativeUrl.isBlank())
                return false;

            // Convert "/uploads/foto/xxx.jpg" → absolute path
            Path filePath = Paths.get(System.getProperty("user.dir") + relativeUrl);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Gagal hapus file: " + e.getMessage());
            return false;
        }
    }
}