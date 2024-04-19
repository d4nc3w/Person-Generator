package org.example.tpo_06.model;

import java.util.HashSet;
import java.util.Set;

public class PersonDTO {
    private Set<Person> setOfPersons;

    public PersonDTO(){
        this.setOfPersons = new HashSet<>();
    }

    public void createPerson(String firstName, String lastName, String dateOfBirth, String university,String origin, String sex, String height, String weight, String job, String race){
        Person person = new Person(firstName, lastName, dateOfBirth, university, origin, sex, height, weight, job, race);
        addPersonToSet(person);
    }

    private void addPersonToSet(Person person){
        this.setOfPersons.add(person);
    }

    public Set<Person> getSetOfPersons(){
        return this.setOfPersons;
    }
}
