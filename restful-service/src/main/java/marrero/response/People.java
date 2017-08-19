package marrero.response;

import marrero.model.Person;

import java.util.List;

public class People {

    private List<Person> people;

    public People() {
    }

    public People(List<Person> people) {
        this.people = people;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
