package com.tsi.bahra.arjun.vmo2Spring.services;

import com.tsi.bahra.arjun.vmo2Spring.objects.Language;
import com.tsi.bahra.arjun.vmo2Spring.repos.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepo;

    public Iterable<Language> getAllLanguages() {
        return languageRepo.findAll();
    }
}
