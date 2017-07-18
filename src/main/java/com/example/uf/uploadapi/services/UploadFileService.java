package com.example.uf.uploadapi.services;

import com.example.uf.uploadapi.domain.FileMetaData;
import com.example.uf.uploadapi.repos.FileMetaDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {

    private FileMetaDataRepository fileMetaDataRepository;

    public UploadFileService(FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    /**
     * @param multipartFile File that was uploaded via API
     * @param code          - General category coded via API
     * @param description   Description
     * @param destDir       Destination of file contents on the server
     * @return FileMetaData updated or newly created object
     * @throws IOException In the event of writing the file on the server fails
     */
    public FileMetaData saveUploadeFileAndMetaData(MultipartFile multipartFile, String code, String description, String destDir) throws IOException {


        //Define the serverPath where the file will reside
        String serverPath = destDir + "/" + multipartFile.getOriginalFilename();

        //Find existing
        FileMetaData fileMetaData = fileMetaDataRepository.findByServerPath(serverPath);

        // Create a new version of the FileMetaData
        if (null == fileMetaData) {
            fileMetaData = new FileMetaData();
            fileMetaData.setServerPath(serverPath);
        }

        fileMetaData.setCode(code);
        fileMetaData.setDescription(description);
        fileMetaDataRepository.save(fileMetaData);

        //If file meta data created then store the file contents on server
        if (null != fileMetaData.getId()) {
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(serverPath);
            Files.write(path, bytes);
        } else {
            //Return null to indicate a problem happened
            fileMetaData = null;
        }

        return fileMetaData;

    }

    /**
     * @param id - Database primary key (id)
     * @return FileMetaData
     */
    public FileMetaData findFileMetaDataById(Long id) {

        return fileMetaDataRepository.findOne(id);
    }
}
