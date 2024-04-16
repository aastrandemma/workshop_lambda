package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.   Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        /*List<Person> result = storage.findMany(filterByFirstName("Erik"));
        result.forEach(printPerson);*/

        storage.findMany(p -> p.getFirstName().equals("Erik")).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        /*List<Person> result = storage.findMany(filterByGender(Gender.FEMALE));
        result.forEach(printPerson);*/

        storage.findMany(person -> person.getGender().equals(Gender.FEMALE)).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        /*List<Person> result = storage.findMany(filterByBornAfter("1999-12-31"));
        result.forEach(printPerson);*/

        storage.findMany(person -> person.getBirthDate().isAfter(LocalDate.parse("1999-12-31"))).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        // printPerson.accept(storage.findOne(filterById(123)));

        System.out.println(storage.findOne(p -> p.getId() == 123));

        System.out.println("----------------------");
    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        // printStr.accept(storage.findOneAndMapToString(filterById(456), Exercises::personToString));

        System.out.println(
                storage.findOneAndMapToString(
                        person -> person.getId() == 456,
                        person -> "Name: " + person.getFirstName() + " " + person.getLastName() + " born " + person.getBirthDate()
                )
        );

        System.out.println("----------------------");
    }

    /*
      6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        /*Predicate<Person> filter = person -> filterByGender(Gender.MALE).test(person) && filterByStartsWith("E").test(person);
        List<String> result = storage.findManyAndMapEachToString((filter), Exercises::personToString);
        result.forEach(printStr);*/

        storage.findManyAndMapEachToString(
                person -> person.getGender() == Gender.MALE && person.getFirstName().startsWith("E"),
                Person::toString
        ).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        /*Function<Person, String> personToString = person ->
                person.getFirstName() + " " + person.getLastName() + " "
                        + calculateAge(person.getBirthDate()) + " years";
        Predicate<Person> filter = person -> calculateAge(person.getBirthDate()) < 10;
        List<String> result = storage.findManyAndMapEachToString(filter, personToString);
        result.forEach(printStr);*/

        storage.findManyAndMapEachToString(
                person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() < 10,
                person -> person.getFirstName() + " " + person.getLastName() + " " + Period.between(person.getBirthDate(), LocalDate.now()).getYears() + " years"
        ).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
       8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        /*Consumer<Person> print = person -> System.out.println(person.toString());
        storage.findAndDo(filterByFirstName("Ulf"), print);*/

        storage.findAndDo(
                person -> person.getFirstName().equals("Ulf"),
                System.out::println
        );

        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        /*Consumer<Person> print = person -> System.out.println(person.toString());
        Predicate<Person> filter = person -> person.getLastName().contains(person.getFirstName());
        storage.findAndDo(filter, print);*/

        storage.findAndDo(
                person -> person.getLastName().toLowerCase().contains(person.getFirstName().toLowerCase()),
                System.out::println
        );

        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        /*Consumer<Person> print = person -> System.out.println(person.getFirstName() + " " + person.getLastName() + " ");
        Predicate<Person> filter = Exercises::isPalindrome;
        storage.findAndDo(filter, print);*/

        storage.findAndDo(
                person -> person.getFirstName().equalsIgnoreCase(new StringBuilder(person.getFirstName()).reverse().toString()),
                person -> System.out.println(person.getFirstName() + " " + person.getLastName())
        );

        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        /*Comparator<Person> sort = Exercises::compareBirthdayAscending;
        List<Person> result = storage.findAndSort(filterByStartsWith("A"), sort);
        result.forEach(printPerson);*/

        storage.findAndSort(
                person -> person.getFirstName().startsWith("A"),
                Comparator.comparing(Person::getBirthDate)
        ).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        /*Comparator<Person> sort = Exercises::compareBirthdayDescending;
        List<Person> result = storage.findAndSort(filterByBornAfter("1949-12-31"), sort);
        System.out.println("result = " + result);*/

        storage.findAndSort(
                person -> person.getBirthDate().getYear() < 1950,
                Comparator.comparing(Person::getBirthDate).reversed()
        ).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        /*Comparator<Person> sort = Exercises::sortByLastNameFirstNameBirthdate;
        List<Person> result = storage.findAndSort(sort);
        result.forEach(printPerson);*/

        Comparator<Person> compareLastName = Comparator.comparing(Person::getLastName);
        Comparator<Person> compareFirstName = Comparator.comparing(Person::getFirstName);
        Comparator<Person> compareBirthDate = Comparator.comparing(Person::getBirthDate);
        Comparator<Person> all = compareLastName.thenComparing(compareFirstName).thenComparing(compareBirthDate);
        storage.findAndSort(all).forEach(System.out::println);

        System.out.println("----------------------");
    }
}