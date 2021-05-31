package bsp1;

import bsp1.db.RunRepository;
import bsp1.db.RunnerRepository;
import bsp1.model.Run;
import bsp1.model.Runner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BSP1Test {

    RunRepository runRepository;
    RunnerRepository runnerRepository;

    @BeforeEach
    void setUp() {
        runRepository = RunRepository.getInstance();
        runnerRepository = RunnerRepository.getInstance();
        insertTestData();
    }

    public void insertTestData() {
        Runner r1 = new Runner("Karl", "Huber", LocalDate.of(1990,6,7), 'M', 75);
        Runner r2 = new Runner("Eva", "Schmidt", LocalDate.of(1997,10,26), 'W', 55);
        Runner r3 = new Runner("Otto", "Marx", LocalDate.of(1990, 10, 7), 'M', 75);

        Run l1 = new Run(LocalDate.of(2019,6,11), 55, 10350);
        l1.setRunner(r1);
        Run l2 = new Run(LocalDate.of(2019,6,12), 172, 42195);
        l2.setRunner(r1);
        Run l3 = new Run(LocalDate.of(2019,6,13), 58, 8320);
        l3.setRunner(r2);
        Run l4 = new Run(LocalDate.of(2019,7,14), 83, 15000);
        l4.setRunner(r2);
        Run l5 = new Run(LocalDate.of(2019,7,15), 115, 21000);
        l5.setRunner(r2);

        runnerRepository.persistRunner(r1);
        runnerRepository.persistRunner(r2);
        runnerRepository.persistRunner(r3);

        runRepository.persistRun(l1);
        runRepository.persistRun(l2);
        runRepository.persistRun(l3);
        runRepository.persistRun(l4);
        runRepository.persistRun(l5);
    }

    @AfterEach
    void tearDown() {
        try {
            runRepository.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Testfall: testet DAO.longRun(25000)
    // Liefert eine Liste mit der L¨aufer Huber Karl
    @Test
    void testLongRun1() {
        List<Runner> runner = runRepository.getRunnersWithMinimumDistance(25000);
        assertEquals(1, runner.size());
        assertEquals("Huber", runner.get(0).getLastName());
    }

    // Testfall: testet DAO.longRun(10000)
    // Liefert eine Liste mit zwei L¨aufern
    @Test
    void testLongRun2() {
        List<Runner> runner = runRepository.getRunnersWithMinimumDistance(10000);
        assertEquals(4, runner.size());
    }

    // Testfall: testet DAO.longRun(50000)
    // Liefert eine leere Liste
    @Test
    void testLongRun3() {
        List<Runner> runner = runRepository.getRunnersWithMinimumDistance(50000);
        assertTrue(runner.isEmpty());
    }

    // Testfall: testet DAO.longRun(-10000)
    // Erwartetes Ergebnis: wirft eine IllegalArgumentException
    @Test
    void testLongRun4() {
        assertThrows(IllegalArgumentException.class,
                () -> runRepository.getRunnersWithMinimumDistance(-10000));
    }


    // Testfall: liefert die Gesamtstrecke des L¨aufers mit der ID 1 im Juli 2019
// Erwartetes Ergebnis: 36000
    @Test
    void testTotalDistance1() {
        Optional<Runner> r = runnerRepository.getRunnerById(2);
        System.out.println(r);
        assertEquals(r.get().getLastName(), "Schmidt");
        int distance = runRepository.totalDistanceInInterval(r.get(), LocalDate.of(2019, 7, 1),
                LocalDate.of(2019, 7, 31));
        assertEquals(36000, distance);
    }


    //Testen der Gesamstrecke von Läufer Huber im Monat Juni
    //Gesamtstrecke: 52545
    @Test
    void testTotalDistance2() {
        Optional<Runner> r = runnerRepository.getRunnerById(1);
        System.out.println(r);
        assertEquals("Huber", r.get().getLastName());
        int distance = runRepository.totalDistanceInInterval(r.get(), LocalDate.of(2019, 6, 1),
                LocalDate.of(2019, 6, 30));

        assertEquals(52545, distance);
    }

    //Testen der Gesamtstrecke von einem Läufer, der noch keinen Marathon gelaufen ist
    //Gesamtstrecke: 0

    @Test
    void testTotalDistance3() {
        Optional<Runner> r = runnerRepository.getRunnerById(3);
        System.out.println(r);
        assertEquals("Marx", r.get().getLastName());
        int distance = runRepository.totalDistanceInInterval(r.get(), LocalDate.of(2019, 6, 1),
                LocalDate.of(2019, 6, 30));

        assertEquals(0, distance);
    }
}