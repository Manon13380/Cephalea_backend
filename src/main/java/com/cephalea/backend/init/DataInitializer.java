package com.cephalea.backend.init;


import com.cephalea.backend.entity.SoulagementEntity;
import com.cephalea.backend.repository.SoulagementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SoulagementRepository soulagementRepository;


    public void run(String... args) throws Exception {
        if (soulagementRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON file from the resources folder
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("data/soulagements.json");

            if (inputStream == null) {
                System.err.println("❌ Fichier soulagements.json non trouvé !");
                return;
            }

            // onvert JSON into a SoulagementEntity list
            List<SoulagementEntity> soulagements = Arrays.asList(
                    objectMapper.readValue(inputStream, SoulagementEntity[].class)
            );

            soulagementRepository.saveAll(soulagements);
            System.out.println("✔ Soulagements insérés depuis le JSON !");
        } else {
            System.out.println("ℹ Soulagements déjà en base, pas d'insertion.");
        }
    }
}
