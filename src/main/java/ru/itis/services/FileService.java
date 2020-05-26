package ru.itis.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.InformationDto;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FileRepository;
import ru.itis.repositories.jpaRepo.FileInfoJpaRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileService {

    @Autowired
    private FileRepository fileRepository;


    @Autowired
    private FileInfoJpaRepository fileInfoJpaRepository;

    @Autowired
    @Qualifier("directory")
    private String storagePath;


    public void save(FileInfo fileInfo) {
        fileRepository.save(fileInfo);
    }

    public FileInfo findFile(String name) {
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
        return storagePath + "\\" + storageName;
    }


    public InformationDto getInformation(Long id) {
        return fileInfoJpaRepository.getFileInfo(id);

    }

}
