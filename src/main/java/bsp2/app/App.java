package bsp2.app;

import bsp2.db.CarRepository;
import bsp2.db.CustomerRepository;
import bsp2.db.RentalRepository;
import bsp2.db.StationRepository;
import bsp2.model.Car;
import bsp2.model.Customer;
import bsp2.model.Rental;
import bsp2.model.Station;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        new App().insertTestData();
    }

    public void insertTestData() {
        CarRepository carRepository = CarRepository.getInstance();
        CustomerRepository customerRepository = CustomerRepository.getInstance();
        RentalRepository rentalRepository = RentalRepository.getInstance();
        StationRepository stationRepository = StationRepository.getInstance();
        /*------------------*Stations*------------------*/
        Station stationVienna = new Station("Vienna");
        Station stationBerlin = new Station("Berlin");
        Station stationGraz = new Station("Graz");

        /*------------------*Customers*------------------*/

        Customer customerOtto = new Customer(11111, "Marx", "Otto");
        Customer customerEva = new Customer(11112, "Schmidt", "Eva");
        Customer customerKarl = new Customer(11113, "Schmidt", "Karl");


        /*------------------*Cars*------------------*/
        Car carVW = new Car("W-123456", 2012, 4000, "Golf");
        carVW.setStation(stationVienna);

        Car carMercedes = new Car("W-654321", 2020, 0, "Mercedes EQS");
        carMercedes.setStation(stationVienna);

        Car carHyundai = new Car("W-894759", 2017, 40000, "Hyundai i30 Kombi RS");
        carHyundai.setStation(stationVienna);

        Car carAudi = new Car("G-123456", 2021, 0, "Audi RSQ7 eTron");
        carAudi.setStation(stationGraz);

        Car carAudiKombi = new Car("G-123400", 2020, 4000, "Audi RS7 Hybrid-boosted");
        carAudiKombi.setStation(stationGraz);

        Car carKia = new Car("G-949499", 2014, 50000, "Kia Stinger RS6 BASSBOOSTED");
        carKia.setStation(stationGraz);

        Car carKiaKombi = new Car("B-123456", 2020, 5000, "Kia Kombi");
        carKiaKombi.setStation(stationBerlin);

        Car carVWKombi = new Car("B-654321", 2018, 15000, "VW Golf Kombi");
        carVWKombi.setStation(stationBerlin);

        Car carMercedesKombi = new Car("B-69420", 2019, 5000, "Mercedes A Class Kombi");
        carMercedesKombi.setStation(stationBerlin);

        Car hyundaiKleinwagen = new Car("B-42069", 2015, 50000, "Hyundai i20 Extended Edition");
        hyundaiKleinwagen.setStation(stationBerlin);


        /*------------------*Rentals*------------------*/
        Rental rentalGrazNotCompleted = new Rental(4000, LocalDate.of(2021, 4, 13), carAudi, customerEva, stationGraz);
        rentalGrazNotCompleted.setStation(stationGraz);

        Rental rentalViennaNotCompleted = new Rental(3000, LocalDate.of(2021, 4, 14), carVW, customerKarl, stationVienna);
        rentalViennaNotCompleted.setStation(stationVienna);

        Rental rentalBerlinNotCompleted = new Rental(1500, LocalDate.of(2021, 4, 15), carVWKombi, customerOtto, stationBerlin);
        rentalBerlinNotCompleted.setStation(stationBerlin);

        Rental rentalViennaCompleted = new Rental(1500, LocalDate.of(2020, 9, 15), LocalDate.of(2020, 9, 18));
        rentalViennaCompleted.setStation(stationVienna);
        rentalViennaCompleted.setReturnStation(stationGraz);
        rentalViennaCompleted.setCar(carHyundai);
        rentalViennaCompleted.setCustomer(customerEva);

        Rental rentalGrazCompleted = new Rental(1000, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 4));
        rentalGrazCompleted.setStation(stationGraz);
        rentalGrazCompleted.setReturnStation(stationVienna);
        rentalGrazCompleted.setCar(carKia);
        rentalGrazCompleted.setCustomer(customerOtto);



        new ArrayList<>(Arrays.asList(stationVienna, stationBerlin, stationGraz)).forEach(stationRepository::insertStation);
        new ArrayList<>(Arrays.asList(carVW, carVWKombi, carMercedesKombi, carMercedes,
                carAudi, carAudiKombi, carKia, carKiaKombi,
                carHyundai, hyundaiKleinwagen)).forEach(carRepository::insertCar);
        new ArrayList<>(Arrays.asList(customerEva, customerKarl, customerOtto)).forEach(customerRepository::insertCustomer);
        new ArrayList<>(Arrays.asList(rentalViennaNotCompleted, rentalGrazNotCompleted, rentalBerlinNotCompleted,
                rentalViennaCompleted, rentalGrazCompleted)).forEach(rentalRepository::insertRental);
    }
}
