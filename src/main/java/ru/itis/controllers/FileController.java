package ru.itis.controllers;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.models.FileInfo;
import ru.itis.services.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;


//    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ModelAndView getPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        System.out.println(name);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("file_upload");
        return modelAndView;
    }

    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile
//                                   @RequestParam("name") String name
    ) {
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("file_upload");

        String newFilename = fileService.createStorageName(multipartFile.getOriginalFilename());

        FileInfo fileInfo = FileInfo.builder().realName(multipartFile.getOriginalFilename())
                .size(multipartFile.getSize())
                .suffix(multipartFile.getContentType())
                .nameInStorage(newFilename)
                .storageUrl(fileService.getUrlOfFile(newFilename))
                .build();

        fileService.save(fileInfo);
        fileService.copyToStorage(multipartFile, newFilename);
        return modelAndView;
    }

    // localhost:8080/files/123809183093qsdas09df8af.jpeg

    @SneakyThrows
    @RequestMapping(value = "/files/{file-name:.+}", method = RequestMethod.GET)
    public ModelAndView getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {

        FileInfo fileInfo = fileService.findFile(fileName);

        java.io.File fil = new java.io.File(fileInfo.getStorageUrl());
        response.setContentType(fileInfo.getSuffix());
        FileInputStream fileInputStream = new FileInputStream(fil);
        IOUtils.copy(fileInputStream, response.getOutputStream());

        return new ModelAndView();
    }
}
