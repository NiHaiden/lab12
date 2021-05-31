package bsp1.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "run", schema = "public")
public class Run implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "minutes")
    private Integer minutes;

    @Column(name = "distance")
    private Integer distance;

    @ManyToOne
    @JoinColumn(name = "run_runner_id", nullable = false)
    private Runner runner;

    public Run(LocalDate date, Integer minutes, Integer distance) {
        this.date = date;
        this.minutes = minutes;
        this.distance = distance;
    }

    public Run(LocalDate date, Integer minutes, Integer distance, Runner runner) {
        this.date = date;
        this.minutes = minutes;
        this.distance = distance;
        this.runner = runner;
    }

    public Run(Integer id, LocalDate date, Integer minutes, Integer distance, Runner runner) {
        this.id = id;
        this.date = date;
        this.minutes = minutes;
        this.distance = distance;
        this.runner = runner;
    }

    public Run() {
 
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Run run = (Run) o;
        return id.equals(run.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Run{" +
                "id=" + id +
                ", date=" + date +
                ", minutes=" + minutes +
                ", distance=" + distance +
                ", runner=" + runner +
                '}';
    }
}
