Documentation:

    public class Person {
      private String firstName;
      private String lastName;
      private String dateOfBirth;
      private String university;
      private String origin;
      private String sex;
      private String height;
      private String weight;
      private String job;
      private String race;

    public Person(String firstName, String lastName, String dateOfBirth, String university, String origin, String sex, String height, String weight, String job, String race) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.university = university;
        this.origin = origin;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.job = job;
        this.race = race;
      }
    }
The Person class represents an individual person with various attributes such as:
  * name
  * date of birth
  * university
  *  origin
  *  sex
  *  height
  *  weight
  *  job
  *  race
*  It provides a constructor for initializing these attributes and getter and setter methods for accessing and modifying them.


        public class PersonDTO {
            private Set<Person> setOfPersons;

            public PersonDTO(){
                this.setOfPersons = new HashSet<>();
            }
        
            public void createPerson(String firstName, String lastName, String dateOfBirth, String university, String origin, String sex, String height, String weight, String job, String race) {
                Person person = new Person(firstName, lastName, dateOfBirth, university, origin, sex, height, weight, job, race);
                addPersonToSet(person);
            }
        
            private void addPersonToSet(Person person) {
                this.setOfPersons.add(person);
            }
        
            public Set<Person> getSetOfPersons() {
                return this.setOfPersons;
            }
        }

The PersonDTO class serves as a Data Transfer Object (DTO) for Person instances. 
It contains a set of Person objects and provides methods to create and add new Person instances to the set. 
Use createPerson method to create and add a new Person instance.

    @Controller
    public class PersonController {
        PersonService service = new PersonService();
    
        @GetMapping("/person")
        public String getPersons() {
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

The PersonController class is a Spring MVC controller responsible for handling HTTP requests related to Person instances. 
It provides methods to serve views and generate data based on the user's input. 
Use the /person endpoint to generate data about a given quantity of persons based on provided parameters.

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
                    personHeight += "cm";
                }
    
                if (weight) {
                    personWeight = String.valueOf(faker.number().randomDouble(2, 30, 150));
                    personWeight += "kg";
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

The PersonService class is responsible for generating a set of Person instances using the Java Faker library. 
The generatePersons method accepts several parameters, including the desired quantity of persons, language, and various boolean flags to indicate which attributes should be populated. 
The method uses Faker to generate random data for each person and adds them to a PersonDTO instance.
