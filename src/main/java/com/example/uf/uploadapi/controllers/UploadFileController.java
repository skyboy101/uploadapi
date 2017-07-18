package com.example.uf.uploadapi.controllers;

import com.example.uf.uploadapi.domain.FileMetaData;
import com.example.uf.uploadapi.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
public class UploadFileController {

    //private final Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    private UploadFileService uploadFileService;

    @Autowired
    public UploadFileController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @Value("${upload.destination.dir}")
    private String DESTINATION_DIR;

    @PostMapping("/api/v1/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile,
            @RequestParam(value ="code",  defaultValue = "n/a") String code,
            @RequestParam(value = "description", defaultValue = "n/a") String description)

    {
        FileMetaData fileMetaData;
        if (uploadfile.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("message", "select non-empty file"), HttpStatus.OK);
        }

        try {

            fileMetaData = uploadFileService.saveUploadeFileAndMetaData(uploadfile, code, description, DESTINATION_DIR);

            if (null == fileMetaData) {
                return new ResponseEntity<>(Collections.singletonMap("error", "unable to create file"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(fileMetaData, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("api/v1/metadata/{id}")
    public ResponseEntity<?> findFileMetaData(
            @PathVariable("id") Long id
    ) {
        FileMetaData fileMetaData = uploadFileService.findFileMetaDataById(id);
        if (fileMetaData != null) {
            return new ResponseEntity<>(fileMetaData, new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "unable to find record"), HttpStatus.NOT_FOUND);
        }

    }


}
