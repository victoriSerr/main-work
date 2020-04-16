package ru.itis.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.models.File;
import ru.itis.repositories.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    @Qualifier("directory")
    private String storagePath;


    public void save(File file) {
        fileRepository.save(file);
    }

    public File findFile(String name) {
        return fileRepository.findFile(name).get();
    }

    public void copyToStorage(MultipartFile file, String storageFileName) {
        try {
            Files.copy(file.getInputStream(), Paths.get(storagePath, storageFileName));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String createStorageName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        String newFileName = UUID.randomUUID().toString();
        return newFileName + "." + extension;

    }

    public String getUrlOfFile(String storageName) {
        return storagePath + "/" + storageName;
    }


}
