package bsp2.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "station", schema = "public")
public class Station {
    @Column(name = "station_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city")
    private String city;


    public Station() {}

    public Station(Integer id, String city) {
        this.id = id;
        this.city = city;
    }

    public Station(String city) {
        this.city = city;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id.equals(station.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }
}
