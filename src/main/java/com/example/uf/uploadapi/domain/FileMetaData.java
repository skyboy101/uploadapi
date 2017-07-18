package com.example.uf.uploadapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FileMetaData {


    @Id
    @GeneratedValue
    private Long id;

    //Limit the size
    @Column(length = 50)
    private String code;

    // Limit size, and allow it to be empty
    @Column(length = 2000, nullable = true)
    private String description;

    //Only allow a unique server path per file
    @Column(unique = true)
    private String serverPath;

    public String toString() {
        return String.format("code: %s, description: %s, serverPath: %s", code, description, serverPath);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }


}
