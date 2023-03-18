import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {//генерация большого числа случайных экземпляров класса Person
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        long amountOfMinorPeople = persons.stream() //ищем сколько несовершеннолетних
                .filter(people -> people.getAge() < 18)
                .count();

        System.out.println(amountOfMinorPeople);

        //собираем список призывников
        List<String> maleRecruits = persons.stream()
                .filter(people -> people.getSex() == Sex.MAN)
                .filter(man -> man.getAge() >= 18 && man.getAge() <= 27)
                .limit(5) //поставил лимиты, чтобы не перегружать консоль при выводе
                .map(Person::getFamily)
                .toList();

        System.out.println(maleRecruits);

        //список потенциально работоспособных людей
        List<String> personsAbleToWork = persons.stream()
                .filter(Main::ableToWork)
                .sorted(Comparator.naturalOrder())
                .map(Person::getFamily)
                .distinct() //избавился от повторяющихся фамилий для проверки порядка
                .toList();

        System.out.println(personsAbleToWork);
    }

    public static boolean ableToWork(Person person) { //критерии работоспособных людей
        if (person.getEducation() == Education.HIGHER) {

            if (person.getSex() == Sex.MAN && person.getAge() >= 18 && person.getAge() <= 65) {
                return true;
            } else if (person.getSex() == Sex.WOMAN && person.getAge() >= 18 && person.getAge() <= 60) {
                return true;
            }
        }
        return false;
    }
}
