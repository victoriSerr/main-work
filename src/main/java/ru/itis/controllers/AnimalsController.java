package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import javax.servlet.annotation.MultipartConfig;
import java.util.List;

@Controller
@MultipartConfig
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

    @GetMapping("/animals/{animalId:.+}")
    public ModelAndView getAnimalDetails(@PathVariable("animalId") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Animal animal = animalService.find(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("animal", animal);
        modelAndView.addObject("user", name);
        modelAndView.setViewName("animal-details");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/animals/{animalId:.+}/edit")
    public ModelAndView getAnimalEdit(@PathVariable("animalId") Long id) {
        Animal animal = animalService.find(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("animal", animal);
        modelAndView.setViewName("animal-edit");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/animals/{animalId:.+}/edit")
    public String addAnimal(
            @PathVariable("animalId") Long id,
            @RequestParam("name") String name,
            @RequestParam("descr") String descr,
            @RequestParam("status") String status) {
        Animal animal = animalService.find(id);
        AnimalStatus status1;
        if (status.equals("Ищет дом")) {
            status1 = AnimalStatus.HOMELESS;
        } else status1 = AnimalStatus.IN_HOME;
        animal.setDescription(descr);
        animal.setName(name);
        animal.setStatus(status1);
        animalService.update(animal);
        return "redirect:/animals/{animalId}";
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

    @PostMapping("/animals/add")
    public String addAnimal(
            @RequestParam("name") String name,
            @RequestParam("descr") String descr,
            @RequestParam("status") String status,
            @RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,
            @RequestParam("orgId") Long id
    ) {
        Organisation organisation = organisationService.find(id);
//
        System.out.println(organisation);
        AnimalStatus status1;
        System.out.println(status);
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
//            System.out.println(fileInfo.getSourсeFile().getName());
        }

        return "redirect:/animals";
    }
}
