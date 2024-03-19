package com.tsi.bahra.arjun.vmo2Spring.controllers;

import com.tsi.bahra.arjun.vmo2Spring.objects.Language;
import com.tsi.bahra.arjun.vmo2Spring.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @GetMapping("/allLanguages")
    public Iterable<Language> getAllLanguages() {
        return languageService.getAllLanguages();
    }
}
