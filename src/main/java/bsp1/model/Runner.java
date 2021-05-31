package bsp1.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "runner", schema = "public")
public class Runner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender")
    private Character gender;

    @Column(name = "weight")
    private Integer weight;

    @OneToMany(mappedBy = "runner")
    public Set<Run> runs = new HashSet<>();

    public Runner(Integer id, String firstName, String lastName, LocalDate birthday, Character gender, Integer weight) {
        if(!(gender == 'M' || gender == 'W')) {
            throw new IllegalArgumentException("Falsches Geschlecht!");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.weight = weight;
    }

    public Runner(String firstName, String lastName, LocalDate birthday, Character gender, Integer weight) {

        if(!(gender == 'M' || gender == 'W')) {
            throw new IllegalArgumentException("Falsches Geschlecht!");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.weight = weight;
    }

    public Runner() {}

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Character getGender() {
        return gender;
    }

    public void setCharacter(Character gender) {
        this.gender = gender;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Runner runner = (Runner) o;
        return id.equals(runner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Runner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", weight=" + weight +
                '}';
    }
}
