package com.sowmyaMedabalimi.DMS.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.sowmyaMedabalimi.DMS.Models.Dog;
import com.sowmyaMedabalimi.DMS.Models.Trainer;
import com.sowmyaMedabalimi.DMS.repository.DogRepository;
import com.sowmyaMedabalimi.DMS.repository.TrainerRepository;

@Controller
public class DogController {

    ModelAndView mv = new ModelAndView();
    @Autowired
    DogRepository dogRepo;
    @Autowired
    TrainerRepository trainerRepo;

    // Map the root URL to the home page (renders "home.html")
    @RequestMapping("/")
    public String home() {
        return "home"; // Ensure you have home.html in your templates folder
    }

    @RequestMapping("add")
    public ModelAndView add() {
    	mv.setViewName("addNewDog");
        Iterable<Trainer> trainerList = trainerRepo.findAll();
        mv.addObject("trainers", trainerList);
        return mv;
    }

    @RequestMapping("addNewDog")
    public ModelAndView addNewDog(Dog dog, @RequestParam("trainerId") int id) {
        Trainer trainer = trainerRepo.findById(id).orElse(new Trainer());
        dog.setTrainer(trainer);
        dogRepo.save(dog);
        mv.setViewName("home");  // Redirect back to the home page after adding a dog
        return mv;
    }

    @RequestMapping("addTrainer")
    public ModelAndView addTrainer() {
        mv.setViewName("addNewTrainer");
        return mv;
    }

    @RequestMapping("trainerAdded")
    public ModelAndView addNewTrainer(Trainer trainer) {
        trainerRepo.save(trainer);
        mv.setViewName("home"); // Redirect back to the home page after adding a trainer
        return mv;
    }

    @RequestMapping("viewModifyDelete")
    public ModelAndView viewDogs() {
        mv.setViewName("viewDogs");
        Iterable<Dog> dogList = dogRepo.findAll();
        mv.addObject("dogs", dogList);
        return mv;
    }

    @RequestMapping("editDog")
    public ModelAndView editDog(Dog dog) {
        dogRepo.save(dog);
        mv.setViewName("editDog");
        return mv;
    }

    @RequestMapping("deleteDog")
    public ModelAndView deleteDog(Dog dog) {
        Dog d = dogRepo.findById(dog.getId()).orElse(new Dog());
        dogRepo.delete(d);
        mv.setViewName("home"); // Redirect to home page using ModelAndView
        return mv; // Return the ModelAndView
    }

    @RequestMapping("search")
    public ModelAndView searchById(int id) {
        Dog dogFound = dogRepo.findById(id).orElse(new Dog());
        mv.addObject(dogFound);
        mv.setViewName("searchResults");
        return mv;
    }
}
