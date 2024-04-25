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

    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Person Data</title>
        <link rel="stylesheet" type="text/css" href="person.css">
    </head>
    <body>
    <h1>Generate Persons</h1>
    <form action="/person" method="post">
        <label for="quantity">How many?</label>
        <input type="number" id="quantity" name="quantity" required><br><br>
        <label for="language">Language</label>
        <select id="language" name="language" required>
            <option value="en">English</option>
            <option value="pl">Polish</option>
            <option value="fr">French</option>
            <option value="cs">Czech</option>
            <option value="es">Spanish</option>
            <option value="zh">Chinese</option>
            <option value="hr">Croatian</option>
            <option value="de">German</option>
            <option value="ru">Russian</option>
            <option value="ja">Japanese</option>
            <option value="be">Belarusian</option>
            <option value="it">Italian</option>
            <option value="el">Greek</option>
            <option value="sk">Slovak</option>
        </select>
        <br><br><br>
        <label>Additional columns:</label><br><br>
        <label for="university">University</label>
        <input type="checkbox" id="university" name="university" value="true"><br><br>
        <label for="origin">Origin</label>
        <input type="checkbox" id="origin" name="origin" value="true"><br><br>
        <label for="sex">Sex</label>
        <input type="checkbox" id="sex" name="sex" value="true"><br><br>
        <label for="height">Height</label>
        <input type="checkbox" id="height" name="height" value="true"><br><br>
        <label for="weight">Weight</label>
        <input type="checkbox" id="weight" name="weight" value="true"><br><br>
        <label for="job">Job</label>
        <input type="checkbox" id="job" name="job" value="true"><br><br>
        <label for="race">Race</label>
        <input type="checkbox" id="race" name="race" value="true"><br><br>
        <input type="submit" value="Generate">
    </form>
    
    <table th:if="${personSet != null}">
        <h1>Generated Persons</h1>
        <h2 th:if="${language} == 'en'">Language: English</h2>
        <h2 th:if="${language} == 'pl'">Language: Polish</h2>
        <h2 th:if="${language} == 'fr'">Language: French</h2>
        <h2 th:if="${language} == 'es'">Language: Spanish</h2>
        <h2 th:if="${language} == 'zh'">Language: Chinese</h2>
        <h2 th:if="${language} == 'de'">Language: German</h2>
        <h2 th:if="${language} == 'ru'">Language: Russian</h2>
        <h2 th:if="${language} == 'ja'">Language: Japanese</h2>
        <h2 th:if="${language} == 'it'">Language: Italian</h2>
        <h2 th:if="${language} == 'cs'">Language: Czech</h2>
        <h2 th:if="${language} == 'hr'">Language: Croatian</h2>
        <h2 th:if="${language} == 'be'">Language: Belarusian</h2>
        <h2 th:if="${language} == 'el'">Language: Greek</h2>
        <h2 th:if="${language} == 'sk'">Language: Slovak</h2>
    
        <tr th:if="${language} == 'en'">
            <th>First Name</th>
            <th>Last Name</th>
            <th>Date of Birth</th>
            <th th:if="${university}">University</th>
            <th th:if="${origin}">Origin</th>
            <th th:if="${sex}">Sex</th>
            <th th:if="${height}">Height</th>
            <th th:if="${weight}">Weight</th>
            <th th:if="${job}">Job</th>
            <th th:if="${race}">Race</th>
        </tr>
        <tr th:if="${language} == 'pl'">
            <th>Imię</th>
            <th>Nazwisko</th>
            <th>Data urodzenia</th>
            <th th:if="${university}">Uniwersytet</th>
            <th th:if="${origin}">Pochodzenie</th>
            <th th:if="${sex}">Płeć</th>
            <th th:if="${height}">Wzrost</th>
            <th th:if="${weight}">Waga</th>
            <th th:if="${job}">Zawód</th>
            <th th:if="${race}">Rasa</th>
        </tr>
        <tr th:if="${language} == 'fr'">
            <th>Prénom</th>
            <th>Nom de famille</th>
            <th>Date de naissance</th>
            <th th:if="${university}">Université</th>
            <th th:if="${origin}">Origine</th>
            <th th:if="${sex}">Genre</th>
            <th th:if="${height}">Hauteur</th>
            <th th:if="${weight}">Poids</th>
            <th th:if="${job}">Emploi</th>
            <th th:if="${race}">Race</th>
        </tr>
        <tr th:if="${language} == 'es'">
            <th>Nombre de pila</th>
            <th>Apellido</th>
            <th>Fecha de nacimiento</th>
            <th th:if="${university}">Universidad</th>
            <th th:if="${origin}">Origen</th>
            <th th:if="${sex}">Género</th>
            <th th:if="${height}">Altura</th>
            <th th:if="${weight}">Peso</th>
            <th th:if="${job}">Trabajo</th>
            <th th:if="${race}">Raza</th>
        </tr>
        <tr th:if="${language} == 'zh'">
            <th>名</th>
            <th>姓</th>
            <th>出生日期</th>
            <th th:if="${university}">大学</th>
            <th th:if="${origin}">起源</th>
            <th th:if="${sex}">性别</th>
            <th th:if="${height}">高度</th>
            <th th:if="${weight}">重量</th>
            <th th:if="${job}">工作</th>
            <th th:if="${race}">种族</th>
        </tr>
        <tr th:if="${language} == 'de'">
            <th>Vorname</th>
            <th>Nachname</th>
            <th>Geburtsdatum</th>
            <th th:if="${university}">Universität</th>
            <th th:if="${origin}">Herkunft</th>
            <th th:if="${sex}">Geschlecht</th>
            <th th:if="${height}">Höhe</th>
            <th th:if="${weight}">Gewicht</th>
            <th th:if="${job}">Beruf</th>
            <th th:if="${race}">Rasse</th>
        </tr>
        <tr th:if="${language} == 'ru'">
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Дата рождения</th>
            <th th:if="${university}">Университет</th>
            <th th:if="${origin}">Источник</th>
            <th th:if="${sex}">Пол</th>
            <th th:if="${height}">Высота</th>
            <th th:if="${weight}">Масса</th>
            <th th:if="${job}">Работа</th>
            <th th:if="${race}">Раса</th>
        </tr>
        <tr th:if="${language} == 'ja'">
            <th>ファーストネーム</th>
            <th>苗字</th>
            <th>生年月日</th>
            <th th:if="${university}">大学</th>
            <th th:if="${origin}">起源</th>
            <th th:if="${sex}">性別</th>
            <th th:if="${height}">身長</th>
            <th th:if="${weight}">重さ</th>
            <th th:if="${job}">仕事</th>
            <th th:if="${race}">人種</th>
        </tr>
        <tr th:if="${language} == 'it'">
            <th>Nome di battesimo</th>
            <th>Cognome</th>
            <th>Data di nascita</th>
            <th th:if="${university}">Università</th>
            <th th:if="${origin}">Origine</th>
            <th th:if="${sex}">Genere</th>
            <th th:if="${height}">Altezza</th>
            <th th:if="${weight}">Peso</th>
            <th th:if="${job}">Lavoro</th>
            <th th:if="${race}">Razza</th>
        </tr>
        <tr th:if="${language} == 'cs'">
            <th>Jméno</th>
            <th>Příjmení</th>
            <th>Datum narození</th>
            <th th:if="${university}">Univerzita</th>
            <th th:if="${origin}">Původ</th>
            <th th:if="${sex}">Rod</th>
            <th th:if="${height}">Výška</th>
            <th th:if="${weight}">Hmotnost</th>
            <th th:if="${job}">Práce</th>
            <th th:if="${race}">Rasa</th>
        </tr>
        <tr th:if="${language} == 'hr'">
            <th>Ime</th>
            <th>Prezime</th>
            <th>Datum rođenja</th>
            <th th:if="${university}">Sveučilište</th>
            <th th:if="${origin}">Podrijetlo</th>
            <th th:if="${sex}">Spol</th>
            <th th:if="${height}">Visina</th>
            <th th:if="${weight}">Težina</th>
            <th th:if="${job}">Posao</th>
            <th th:if="${race}">Rasa</th>
        </tr>
        <tr th:if="${language} == 'be'">
            <th>Імя</th>
            <th>Прозвішча</th>
            <th>Дата нараджэння</th>
            <th th:if="${university}">Універсітэт</th>
            <th th:if="${origin}">Паходжанне</th>
            <th th:if="${sex}">Пол</th>
            <th th:if="${height}">Вышыня</th>
            <th th:if="${weight}">Вага</th>
            <th th:if="${job}">Праца</th>
            <th th:if="${race}">чалавечая раса</th>
        </tr>
        <tr th:if="${language} == 'el'">
            <th>Ονομα</th>
            <th>Επίθετο</th>
            <th>Ημερομηνια γεννησης</th>
            <th th:if="${university}">Πανεπιστήμιο</th>
            <th th:if="${origin}">Προέλευση</th>
            <th th:if="${sex}">Γένος</th>
            <th th:if="${height}">Ύψος</th>
            <th th:if="${weight}">Βάρος</th>
            <th th:if="${job}">Δουλειά</th>
            <th th:if="${race}">Φυλή</th>
        </tr>
        <tr th:if="${language} == 'sk'">
            <th>Krstné meno</th>
            <th>Priezvisko</th>
            <th>Dátum narodenia</th>
            <th th:if="${university}">Univerzita</th>
            <th th:if="${origin}">Pôvod</th>
            <th th:if="${sex}">Rod</th>
            <th th:if="${height}">Výška</th>
            <th th:if="${weight}">Hmotnosť</th>
            <th th:if="${job}">Práca</th>
            <th th:if="${race}">rasová</th>
        </tr>
        <tbody>
        <tr th:each="person : ${personSet}">
            <td th:text="${person.firstName}"></td>
            <td th:text="${person.lastName}"></td>
            <td th:text="${person.dateOfBirth}"></td>
            <td th:if="${university} == true" th:text="${person.university}"></td>
            <td th:if="${origin} == true" th:text="${person.origin}"></td>
            <td th:if="${sex} == true" th:text="${person.sex}"></td>
            <td th:if="${height} == true" th:text="${person.height}"></td>
            <td th:if="${weight} == true" th:text="${person.weight}"></td>
            <td th:if="${job} == true" th:text="${person.job}"></td>
            <td th:if="${race} == true" th:text="${person.race}"></td>
        </tr>
        </tbody>
    </table>
    </body>
    </html>
    
This HTML file serves as a user interface for generating and displaying person data.
The form allows users to specify parameters for generating persons, including the number of persons, the language, and additional data columns such as university, origin, sex, height, weight, job, and race.
After submitting the form, data is sent to the server using the POST method at the /person endpoint.
The generated data is displayed in a table, with headers and rows corresponding to the selected language and additional columns.
The page leverages Thymeleaf syntax (e.g., th:if and th:each) to conditionally display data based on the form inputs and to iterate through the set of persons.

    body {
        background-color: lightcyan;
        text-align: center;
    }
    
    h1 {
        text-align: center;
    }
    
    form {
        width: 50%;
        margin: 20px auto;
        background-color: whitesmoke;
        padding: 20px;
        border-radius: 20px;
        box-shadow: 0 0 10px;
    }
    label {
        font-weight: bold;
        font-size: larger;
        padding: 15px;
    }
    
    input[type="number"],
    select {
        width: 60%;
        padding: 5px;
    }
    
    input[type="submit"] {
        width: 80%;
        padding: 10px;
        background-color: cornflowerblue;
        color: white;
        border-radius: 14px;
        border-color: cornflowerblue;
        font-size: 16px;
    }
    
    table {
        width: 80%;
        margin: 20px auto;
        background-color: white;
        box-shadow: 0 0 10px;
    }
    th, td {
        padding: 10px;
        text-align: left;
    }
    
    th {
        background-color: whitesmoke;
    }
    
    tr:hover {
        background-color: aliceblue;
    }
This CSS file styles the HTML page and its components for a more visually appealing and user-friendly interface.

The body style sets the background color to lightcyan and aligns text to the center.
The h1 style centers the heading.
The form style centers the form, sets its width to 50%, and applies styles for the background color, padding, border radius, and shadow.
The label style sets the font weight and size, as well as padding.
The input fields and submit button are styled for padding and width, and the submit button has additional styling for its color and border.
The table style centers the table and sets its width to 80%, background color, and shadow.
The th and td styles add padding and set alignment to the left.
The tr:hover style adds a hover effect for table rows.
