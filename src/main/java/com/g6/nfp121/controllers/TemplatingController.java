package com.g6.nfp121.controllers;

import com.g6.nfp121.models.contact.ContactCreateModel;
import com.g6.nfp121.services.ContactService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Controller
public class TemplatingController {
    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login-template";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contactPage(Model model) {
        model.addAttribute("contacts", contactService.getAllContacts());
        return "contact-template";
    }

    @GetMapping("/create-contact")
    public String createContactPage(Model model) {
        return "create-contact-template";
    }
    @PostMapping("/create-contact-backend")
    public RedirectView createContactBackend(@ModelAttribute ContactCreateModel createModel, Model model) {
        contactService.createContact(createModel);
        return new RedirectView("/contact");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexPage() {
        return "index-template";
    }
}