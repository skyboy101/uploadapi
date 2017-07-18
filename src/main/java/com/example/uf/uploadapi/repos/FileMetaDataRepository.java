package com.example.uf.uploadapi.repos;

import com.example.uf.uploadapi.domain.FileMetaData;
import org.springframework.data.repository.CrudRepository;

public interface FileMetaDataRepository extends CrudRepository<FileMetaData,Long> {

    FileMetaData findByServerPath(String serverPath);
}
