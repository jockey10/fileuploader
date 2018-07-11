package com.rock.fileuploader.api;

import com.rock.fileuploader.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    FileStorageService storageService;

    @CrossOrigin
    @PostMapping(value="/upload")
    public String uploadFile(@RequestParam("uploadfile") MultipartFile file) {
        storageService.storeFile(file);
        return file.getOriginalFilename();
    }
}
