package org.example.tpo_06.controller;

import org.example.tpo_06.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {
    PersonService service = new PersonService();

    @GetMapping("/person")
    public String getPersons(){
        return "person";
    }

    @RequestMapping("/person")
    public String generateData(@RequestParam("quantity") int quantity,
                               @RequestParam("language") String lang,
                               @RequestParam(defaultValue = "false") boolean university,
                               @RequestParam(defaultValue = "false") boolean origin,
                               @RequestParam(defaultValue = "false") boolean sex,
                               @RequestParam(defaultValue = "false") boolean height,
                               @RequestParam(defaultValue = "false") boolean weight,
                               @RequestParam(defaultValue = "false") boolean job,
                               @RequestParam(defaultValue = "false") boolean race,
                               Model model) {

        model.addAttribute("language", lang);
        model.addAttribute("university", university);
        model.addAttribute("origin", origin);
        model.addAttribute("sex", sex);
        model.addAttribute("height", height);
        model.addAttribute("weight", weight);
        model.addAttribute("job", job);
        model.addAttribute("race", race);
        model.addAttribute("personSet", service.generatePersons(quantity, lang, university, origin, sex, height, weight, job, race));
        return "/person";
    }
}
