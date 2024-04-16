package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;
    static Consumer<Person> printPerson = System.out::println;
    static Consumer<String> printStr = System.out::println;

    /*
       1.   Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        List<Person> result = storage.findMany(filterByFirstName("Erik"));
        result.forEach(printPerson);
        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        List<Person> result = storage.findMany(filterByGender(Gender.FEMALE));
        result.forEach(printPerson);
        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        List<Person> result = storage.findMany(filterByBornAfter("1999-12-31"));
        result.forEach(printPerson);
        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        printPerson.accept(storage.findOne(filterById(123)));
        System.out.println("----------------------");
    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        printStr.accept(storage.findOneAndMapToString(filterById(456), Exercises::personToString));
        System.out.println("----------------------");
    }

    /*
      6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> filterByGender(Gender.MALE).test(person) && filterByStartsWith("E").test(person);
        List<String> result = storage.findManyAndMapEachToString((filter), Exercises::personToString);
        result.forEach(printStr);
        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        Function<Person, String> personToString = person ->
                person.getFirstName() + " " + person.getLastName() + " "
                        + calculateAge(person.getBirthDate()) + " years";
        Predicate<Person> filter = person -> calculateAge(person.getBirthDate()) < 10;
        List<String> result = storage.findManyAndMapEachToString(filter, personToString);
        System.out.println("result = " + result);
        System.out.println("----------------------");
    }

    /*
       8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        Consumer<Person> print = person -> System.out.println(person.toString());
        storage.findAndDo(filterByFirstName("Ulf"), print);
        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        Consumer<Person> print = person -> System.out.println(person.toString());
        Predicate<Person> filter = person -> person.getLastName().contains(person.getFirstName());
        storage.findAndDo(filter, print);
        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        Consumer<Person> print = person -> System.out.println(person.getFirstName() + " " + person.getLastName() + " ");
        Predicate<Person> filter = Exercises::isPalindrome;
        storage.findAndDo(filter, print);
        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        Comparator<Person> sort = Exercises::compareBirthdayAscending;
        List<Person> result = storage.findAndSort(filterByStartsWith("A"), sort);
        System.out.println("result = " + result);
        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        Comparator<Person> sort = Exercises::compareBirthdayDescending;
        List<Person> result = storage.findAndSort(filterByBornAfter("1949-12-31"), sort);
        System.out.println("result = " + result);
        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        Comparator<Person> sort = Exercises::sortByLastNameFirstNameBirthdate;
        List<Person> result = storage.findAndSort(sort);
        System.out.println("result = " + result.toString());
        System.out.println("----------------------");
    }

    private static Predicate<Person> filterById(int id) {
        return person -> person.getId() == id;
    }

    private static Predicate<Person> filterByGender(Gender gender) {
        return person -> person.getGender().equals(gender);
    }

    private static Predicate<Person> filterByFirstName(String firstName) {
        return person -> person.getFirstName().equals(firstName);
    }

    private static Predicate<Person> filterByStartsWith(String letter) {
        return person -> person.getFirstName().startsWith(letter);
    }

    private static int calculateAge(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthdate, currentDate).getYears();
    }

    private static int compareBirthdayAscending(Person person1, Person person2) {
        return person1.getBirthDate().compareTo(person2.getBirthDate());
    }

    private static int compareBirthdayDescending(Person person1, Person person2) {
        return person2.getBirthDate().compareTo(person1.getBirthDate());
    }

    private static int compareFirstNameAscending(Person person1, Person person2) {
        return person1.getFirstName().compareTo(person2.getFirstName());
    }

    private static int compareLastNameAscending(Person person1, Person person2) {
        return person1.getLastName().compareTo(person2.getLastName());
    }

    private static int sortByLastNameFirstNameBirthdate(Person person1, Person person2) {
        int compareLastName = compareLastNameAscending(person1, person2);
        if (compareLastName != 0) {
            return compareLastName;
        }

        int compareFirstName = compareFirstNameAscending(person1, person2);
        if (compareFirstName != 0) {
            return compareFirstName;
        }

        return compareBirthdayAscending(person1, person2);
    }

    private static Predicate<Person> filterByBornAfter(String birthdate) {
        return person -> person.getBirthDate().isAfter(LocalDate.parse(birthdate));
    }

    private static boolean isPalindrome(Person person) {
        String firstName = person.getFirstName().toLowerCase();
        StringBuilder reversedFirstName = new StringBuilder();
        boolean palindrome = false;

        for (int i = firstName.length() - 1; i >= 0; i--) {
            reversedFirstName.append(firstName.charAt(i));
        }

        if (reversedFirstName.toString().equals(firstName)) {
            palindrome = true;
        }

        return palindrome;
    }

    private static String personToString(Person person) {
        return "Name: " + person.getFirstName() + " " + person.getLastName() + " born " + person.getBirthDate();
    }
}