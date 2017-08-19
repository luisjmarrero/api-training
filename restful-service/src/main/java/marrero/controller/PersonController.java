package marrero.controller;

import marrero.model.Person;
import marrero.response.People;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {

    protected List<Person> personList = new ArrayList<>();

    public void populateList(){
        this.personList.add(new Person(1, "Jose", "Marrero"));
        this.personList.add(new Person(2, "Antonio", "Marrero"));
        this.personList.add(new Person(3, "Kike", "Marrero"));
        this.personList.add(new Person(4, "Mildre", "Pimentel"));
    }

    // GET
    @RequestMapping(value = "/people")
    public People getPersons(){
        if (this.personList.isEmpty()) populateList();
        return new People(this.personList);
    }

    @RequestMapping(value = "/people/{name}")
    public People getPersonByName(@PathVariable("name") String name){
        if (this.personList.isEmpty()) populateList();
        List<Person> list = new ArrayList<>();
        this.personList.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .forEach(p -> list.add(p));

        return new People(list);
    }

    // POST
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public People addPerson(@RequestBody Person person){
        this.personList.add(person);
        return new People(this.personList);
    }

}
