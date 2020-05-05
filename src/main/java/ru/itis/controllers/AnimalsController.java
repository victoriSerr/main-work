package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.models.Animal;
import ru.itis.models.AnimalStatus;
import ru.itis.models.FileInfo;
import ru.itis.models.Organisation;
import ru.itis.services.AnimalService;
import ru.itis.services.FileService;
import ru.itis.services.OrganisationService;

import java.util.List;
@Controller
public class AnimalsController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private FileService fileService;

    @GetMapping("/animals")
    public ModelAndView getAnimals() {
        List<Animal> animalList = animalService.findAll();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("animals", animalList);
        modelAndView.setViewName("animals");
        return modelAndView;
    }

    @GetMapping("/organisations/{orgId:.+}/animals")
    public ModelAndView getOrganisationAnimals(@PathVariable("orgId") Long id) {
        Organisation organisation = organisationService.find(id);
        List<Animal> animals = organisation.getAnimals();

        System.out.println();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("animals", animals);
        modelAndView.setViewName("animals");
        return modelAndView;
    }

    @GetMapping("/animals/add")
    public ModelAndView getAddPage(@RequestParam("orgId") Long orgId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orgId", orgId);
        modelAndView.setViewName("add-animal");
        return modelAndView;
    }

    @PostMapping("/add")
    public String addAnimal(
            @RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,
//            @RequestParam("file") MultipartFile multipartFile
            @RequestParam("name") String name,
                            @RequestParam("descr") String descr,
                            @RequestParam("status") String status,
                            @RequestParam("orgId") Long id
    ) {


//
//
        Organisation organisation = organisationService.find(id);
//
        AnimalStatus status1;
        if (status.equals("Ищет дом")) {
            status1 = AnimalStatus.HOMELESS;
        } else status1 = AnimalStatus.IN_HOME;

        Animal animal = Animal.builder()
                .name(name)
                .description(descr)
                .status(status1)
                .organisation(organisation)
                .build();
        animalService.save(animal);

        for (MultipartFile multipartFile : uploadingFiles) {

            String newFilename = fileService.createStorageName(multipartFile.getOriginalFilename());
        FileInfo fileInfo = FileInfo.builder().realName(multipartFile.getOriginalFilename())
                .size(multipartFile.getSize())
                .suffix(multipartFile.getContentType())
                .nameInStorage(newFilename)
                .storageUrl(fileService.getUrlOfFile(newFilename))
                .animalA(animal)
                .build();
        fileService.save(fileInfo);
        fileService.copyToStorage(multipartFile, newFilename);
        }

//
//

        return "redirect:/animals";
    }
}
