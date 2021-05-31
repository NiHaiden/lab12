package bsp2.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rental", schema = "public")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "km")
    private Integer km;

    @Column(name = "rentalDate")
    private LocalDate rentalDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "rental_car_id", nullable = false)
    private Car car;

    @ManyToOne(optional = false)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "stat_id")
    private Station returnStation;

    @ManyToOne
    @JoinColumn(name = "cust_id")
    private Customer customer;

    public Rental(Integer km, LocalDate rentalDate, Car car, Customer customer, Station station) {
        this.km = km;
        this.rentalDate = rentalDate;
        this.car = car;
        this.customer = customer;
        this.station = station;
    }

    public Rental(Integer km, LocalDate rentalDate, LocalDate returnDate) {
        this.km = km;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Rental(Integer id, Integer km, LocalDate rentalDate, LocalDate returnDate) {
        this.km = km;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }


    public Rental(Integer km, LocalDate rentalDate, LocalDate returnDate, Car car) {
        this.km = km;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.car = car;
    }

    public Rental(Car car, Customer customer, LocalDate rentalDate) {
        setCar(car);
        this.customer = customer;
        this.rentalDate = rentalDate;
    }


    public Rental() {
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Station getStation() {

        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Station getReturnStation() {
        return returnStation;
    }

    public void setReturnStation(Station returnStation) {
        this.returnStation = returnStation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return id.equals(rental.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", km=" + km +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", car=" + car +
                '}';
    }
}
