# Spring Boot - Upload File REST API - Demo Application

This simple application supports the uploading of a file, with a couple of supporting meta-data fields.
The application will store the file onto the file system, and create a File Meta Data object that stores information about the uploaded file.

Application leverages the Spring Boot framework.

## Domain Objects
### File Meta Data
- code : Generic code string that could be used for some type of classification
- description : Description provided when the file was submitted
- serverPath : String representing the server path. Could be changed to a URL if the file is going to be exposed via a static CDN. This property is marked unique, so only one file with the server path can be defined.

## Repository
Currently, the application is configured to use the H2 In-Memory database.
### FileMetaDataRepository
- Extends the CrudRepository
- Defines a method findByServerPath(String serverPath), which allows finding the FileMetaData using the unique serverPath String.
- Also, all default queries/operations of CrudRepository are available
## Services
### UploadFileService
This service provides the functionality to save the uploaded file to the server, and create the FileMetaData resource in the datastore.

- saveUploadeFileAndMetaData: takes parameters and MultipartFile, which were part of the request, and creates the FileMetaData then stores the file contents on the server. Files are store in the directory defined in Env variable upload.destination.dir.
- findFileMetaDataById: A wrapper to the repository function to find a FileMetaData object based on database id.

## Configuraton
- upload.destination.dir : String containing the directory on the server where the file will be uploaded.

Configuration can be changed either by setting the value in the *application.properties* file, or setting *upload.destination.dir*  Environment variable when the application is started


# Testing
Below are a couple of cURL examples that can be run against the URLs that are provided by the API.

## cURL Examples

### Upload a file - POST http: /api/v1/upload
```sbtshell
curl -F code="xx1" -F description="This is a test data file" -F file=@"E:\\test-data\\data.txt" http://localhost:8080/api/v1/upload
```

### FileMetaData based on database ID - GET http://localhost:8080/api/v1/metadata/{id}

```sbtshell
curl http://localhost:8080/api/v1/metadata/1
```

NOTE: id can be obtained from the result of the Upload POST


# Enjoy

