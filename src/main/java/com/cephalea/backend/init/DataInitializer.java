package com.cephalea.backend.init;


import com.cephalea.backend.entity.ActivityAffectedEntity;
import com.cephalea.backend.entity.PotentialTriggerEntity;
import com.cephalea.backend.entity.SoulagementEntity;
import com.cephalea.backend.repository.ActivityAffectedRepository;
import com.cephalea.backend.repository.PotentialTriggerRepository;
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

    @Autowired
    private ActivityAffectedRepository activityAffectedRepository;

    @Autowired
    private PotentialTriggerRepository potentialTriggerRepository;



    public void run(String... args) throws Exception {
        if (soulagementRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON file from the resources folder
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("data/Soulagements.json");

            if (inputStream == null) {
                System.err.println("❌ File soulagements.json not found !");
                return;
            }

            // convert JSON into a SoulagementEntity list
            List<SoulagementEntity> soulagements = Arrays.asList(
                    objectMapper.readValue(inputStream, SoulagementEntity[].class)
            );

            soulagementRepository.saveAll(soulagements);
            System.out.println("✔ Soulagements inserted from JSON  !");
        } else {
            System.out.println(" Soulagements already in base, no insertion..");
        }

        if (activityAffectedRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON file from the resources folder
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("data/Activities.json");

            if (inputStream == null) {
                System.err.println("❌ File activities.json not found !");
                return;
            }

            // convert JSON into a ActivityAffectedEntity list
            List<ActivityAffectedEntity> activities = Arrays.asList(
                    objectMapper.readValue(inputStream, ActivityAffectedEntity[].class)
            );

            activityAffectedRepository.saveAll(activities);
            System.out.println("✔ Activities inserted from JSON  !");
        } else {
            System.out.println(" Activities already in base, no insertion..");
        }

        if (potentialTriggerRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON file from the resources folder
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("data/Triggers.json");

            if (inputStream == null) {
                System.err.println("❌ File Triggers.json not found !");
                return;
            }

            // convert JSON into a PotentialTriggerEntity list
            List<PotentialTriggerEntity> triggers = Arrays.asList(
                    objectMapper.readValue(inputStream, PotentialTriggerEntity[].class)
            );

            potentialTriggerRepository.saveAll(triggers);
            System.out.println("✔ Triggers inserted from JSON  !");
        } else {
            System.out.println(" Triggers already in base, no insertion..");
        }
    }


}
