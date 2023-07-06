package com.example.testauth.controller;

import com.example.testauth.domains.LabourShift;
import com.example.testauth.domains.User;
import com.example.testauth.repositories.LabourShiftRepository;
import com.example.testauth.repositories.UserRepository;
import com.example.testauth.services.LabourShiftService;
import com.example.testauth.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LabourShiftService labourShiftService;

    @Autowired
    LabourShiftRepository labourShiftRepository;

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        if (authentication != null) {
            System.out.println(authentication.getName());
            System.out.println(authentication.getPrincipal());
        }

        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();

        String str = "redirect:/home/"+dayOfMonth+"/"+month+"/"+year;

        return str;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "authorization";
    }

    @GetMapping("/registr")
    public String createUserForm() {
        return "registration";
    }

    @PostMapping("/registr")
    public String saveUser(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "surname") String surname,
                           @RequestParam(value = "email") String email,
                           @RequestParam(value = "work_place") String work_place,
                           Model model, Authentication authentication) {

        userService.registerUser(username, password, name, surname,
                email, work_place);
        return "redirect:/login";
    }

    @GetMapping("/new_labour_shift")
    public String createLaborShift(Model model) {
        return "new_labour_shift";
    }

    @PostMapping("/new_labour_shift")
    public String saveLaborShift(@RequestParam(value = "date") LocalDate date,
                                    @RequestParam(value = "time_of_begin") LocalTime time_of_begin,
                                    @RequestParam(value = "time_of_end") LocalTime time_of_end,
                                    @RequestParam(value = "comment") String comment,
                                    Model model, Authentication authentication) {

        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
        }
        System.out.println(authentication.getName());

        User foundUser = (User) userRepository.findByUsername(authentication.getName());

        labourShiftService.newLabourShift(date, time_of_begin, time_of_end, comment, foundUser);

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//
//        model.addAttribute("username", user.getUsername());
//
//        User username = (User) SecurityContextHolder.getContext();
//        String name = username.getUsername();
//        model.addAttribute("name", name);

//        labourShiftService.newLabourShift(date, time_of_begin, time_of_end,
//                comment, username);
        return "redirect:/home";
    }

    @GetMapping("/home/{day}/{month}/{year}")
    public String showTask(@PathVariable(value = "day") int day,
                           @PathVariable(value = "month") int month,
                           @PathVariable(value = "year") int year,
                           Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
        }
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println(date);

        List<LabourShift> foundDates = labourShiftRepository.findAllByDate(date);
        System.out.println(foundDates);
        model.addAttribute("foundDates", foundDates);

        return "calendar";
    }

    @GetMapping("/edit_user")
    public String editUser(Model model, Authentication authentication) {


        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
        }

        String username = authentication.getName();

        User foundUser = (User) userRepository.findByUsername(username);
        model.addAttribute("foundUser", foundUser);

        return "user_profile";
    }

//    @PostMapping("/edit_user")
//    public String updateStudent(@RequestParam (value="id") long id,
//                                @RequestParam (value="name") String name,
//                                @RequestParam (value="surname") String surname,
//                                @RequestParam (value="work_place") String work_place,
//                                @RequestParam (value="email") String email,
//                                @RequestParam (value="username") String username,
//                                Model model, Authentication authentication) {
//
//        if (authentication == null) {
//            return "redirect:/home";
//        } else {
//            User user = userRepository.findById(id);
//            List<LabourShift> labourShift = labourShiftRepository.findAllByUsername(user.getUsername());
//
//            user.setName(name);
//            user.setSurname(surname);
//            user.setWork_place(work_place);
//            user.setEmail(email);
//            user.setUsername(username);
//
//            userRepository.save(user);
//        }
//
//        return "redirect:/home";
//    }

    @GetMapping("/menu")
    public String labourMenu() {
        return "menu";
    }
}