package org.example.tpo_06.service;

import com.github.javafaker.Faker;
import org.example.tpo_06.model.Person;
import org.example.tpo_06.model.PersonDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {

    public Set<Person> generatePersons(int quantity, String lang, boolean university, boolean origin, boolean sex, boolean height, boolean weight, boolean job, boolean race) {
        Set<Person> persons;
        PersonDTO dto = new PersonDTO();
        Faker faker = new Faker(new Locale(lang));

        for (int i = 0; i < quantity; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String dateOfBirth = faker.date().birthday().toString();
            String universityName = null;
            String originCountry = null;
            String personSex = null;
            String personHeight = null;
            String personWeight = null;
            String personJob = null;
            String personRace = null;
            if (university) {
                universityName = faker.university().name();
            }
            if (origin) {
                originCountry = faker.address().country();
            }
            if (sex) {
                personSex = faker.demographic().sex();
            }
            if (height) {
                personHeight = String.valueOf(faker.number().randomDouble(2, 50, 250));
                personHeight = personHeight + "cm";
            }
            if (weight) {
                personWeight = String.valueOf(faker.number().randomDouble(2, 30, 150));
                personWeight = personWeight + "kg";
            }
            if (job) {
                personJob = faker.job().position();
            }
            if (race) {
                personRace = faker.demographic().race();
            }
            dto.createPerson(firstName, lastName, dateOfBirth, universityName, originCountry, personSex, personHeight, personWeight, personJob, personRace);
        }
        persons = dto.getSetOfPersons();
        return persons;
    }
}
