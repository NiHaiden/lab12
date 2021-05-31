package bsp1;

import bsp2.db.*;
import bsp2.model.Car;
import bsp2.model.Customer;
import bsp2.model.Rental;
import bsp2.model.Station;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BSP2Test {

    CarRepository carRepository;
    CustomerRepository customerRepository;
    RentalRepository rentalRepository;
    StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        carRepository = CarRepository.getInstance();
        customerRepository = CustomerRepository.getInstance();
        rentalRepository = RentalRepository.getInstance();
        stationRepository = StationRepository.getInstance();
        insertTestData();
    }

    public void insertTestData() {
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

    @Test
    void testDataInsertedCorrectly() {
        System.out.println("Customer Size");
        assertEquals(3, customerRepository.findAll().size());
        System.out.println("Station Size");
        assertEquals(3, stationRepository.findAll().size());
        System.out.println("Car-Size");
        assertEquals(10, carRepository.findAll().size());
        System.out.println("Rental-Size");
        assertEquals(5, rentalRepository.findAll().size());
    }


    @Test
    void testInsertStation() {
        Station station = new Station("Linz");
        stationRepository.insertStation(station);
        assertEquals(4, stationRepository.findAll().size());
    }

    @Test
    void testInsertCar() {
        Car car = new Car("B-494949", 2018, 15000, "VW Golf RS");
        car.setStation(stationRepository.getStationById(1).get());
        carRepository.insertCar(car);
        assertEquals(11, carRepository.findAll().size());
    }

    @Test
    void testInsertCustomer() {
        Customer customerOtto = new Customer(11114, "Reichel", "Otto");
        customerRepository.insertCustomer(customerOtto);
        assertEquals(4, customerRepository.findAll().size());
    }

    @Test
    void testInsertCustomerWithWrongIDTooHigh() {
        Customer customerOtto = new Customer(66667, "Reichel", "Otto");
        customerRepository.insertCustomer(customerOtto);
        assertEquals(3, customerRepository.findAll().size());
    }

    @Test
    void testInsertCustomerWithWrongIDTooLow() {
        Customer customerOtto = new Customer(999, "Reichel", "Otto");
        customerRepository.insertCustomer(customerOtto);
        assertEquals(3, customerRepository.findAll().size());
    }

    @Test
    void testInsertCustomerWithSameID() {
        Customer customerOtto = new Customer(11111, "Reichel", "Otto");
        customerRepository.insertCustomer(customerOtto);
        assertEquals(3, customerRepository.findAll().size());
    }

    @Test
    void testFindCarsByStation() {
        Station station = stationRepository.getStationById(1).get();
        List<Car> expectedCars = new ArrayList<>(Arrays.asList(carRepository.getCarByRegistrationNr("W-123456").get(),
                carRepository.getCarByRegistrationNr("W-654321").get(), carRepository.getCarByRegistrationNr("W-894759").get()));

        List<Car> actualCars = carRepository.findCarsByStation(station);
        assertEquals(expectedCars, actualCars);
    }

    @Test
    void testFindCarsByStationDoesntExist() {
        Station station = new Station("Linz");
        List<Car> expectedCars = new ArrayList<>();

        List<Car> actualCars = carRepository.findCarsByStation(station);
        assertEquals(expectedCars, actualCars);
    }

    @Test
    void testFindRentalsByCustomer() {
        Customer customerOtto = new Customer(11111, "Marx", "Otto");
        List<Rental> expectedRentals = new ArrayList<>(Arrays.asList(rentalRepository.getRentalById(3).get(), rentalRepository.getRentalById(5).get()));
        List<Rental> actualRentals = rentalRepository.findRentalsByCustomer(customerOtto);

        assertEquals(expectedRentals, actualRentals);
    }

    @Test
    void testFindRentalsByCustomerNonExistent() {
        Customer customerOtto = new Customer(11114, "Reichel", "Otto");
        List<Rental> expectedRentals = new ArrayList<>();
        List<Rental> actualRentals = rentalRepository.findRentalsByCustomer(customerOtto);

        assertEquals(expectedRentals, actualRentals);
    }

    @Test
    void testreturnCar() {
        Rental toBeReturned = rentalRepository.getRentalById(1).get();
        Station stationVienna = stationRepository.getStationById(1).get();
        rentalRepository.returnCar(toBeReturned, stationVienna, LocalDate.of(2021, 4, 19), 3000);

        assertEquals(7000, carRepository.getCarByRegistrationNr("W-123456").get().getMilage());
    }
    @AfterEach
    void tearDown() {
        try {
            JPAUtil.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}