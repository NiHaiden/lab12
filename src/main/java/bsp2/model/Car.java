package bsp2.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "car", schema = "public")
public class Car {
    @Id
    private String registrationNr;

    @Column(name = "constructionYear")
    private Integer constructionYear;

    @Column(name = "milage")
    private Integer milage;

    @Column(name = "model")
    private String model;

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private Set<Rental> rentals = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    public Car(String registrationNr, Integer constructionYear, Integer milage, String model, Set<Rental> rentals, Station station) {
        this.registrationNr = registrationNr;
        this.constructionYear = constructionYear;
        this.milage = milage;
        this.model = model;
        this.rentals = rentals;
        this.station = station;
    }

    public Car() {
    }

    public Car(String registrationNr, int constructionYear, int milage, String model) {
        this.registrationNr = registrationNr;
        this.constructionYear = constructionYear;
        this.milage = milage;
        this.model = model;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Integer getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(Integer constructionYear) {
        this.constructionYear = constructionYear;
    }

    public Integer getMilage() {
        return milage;
    }

    public void setMilage(Integer milage) {
        this.milage = milage;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRegistrationNr(String registrationNr) {
        this.registrationNr = registrationNr;
    }

    public String getRegistrationNr() {
        return registrationNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return registrationNr.equals(car.registrationNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNr);
    }

    @Override
    public String toString() {
        return "Car{" +
                "registrationNr='" + registrationNr + '\'' +
                ", constructionYear='" + constructionYear + '\'' +
                ", milage=" + milage +
                ", model='" + model + '\'' +
                ", rental=" + rentals +
                '}';
    }
}
