/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsp1.app;


import bsp1.db.RunRepository;
import bsp1.db.RunnerRepository;
import bsp1.model.Run;
import bsp1.model.Runner;

import java.time.LocalDate;

/**
 *
 * @author 20160609
 */
public class App {
    public static void main(String[] args) throws Exception {
        insertTestData();
        System.out.println(RunRepository.getInstance().findAll());
        System.out.println(RunRepository.getInstance().getRunnersWithMinimumDistance(25000));
        System.out.println(RunRepository.getInstance().getRunnersWithMinimumDistance(0));
    }

    public static void insertTestData() throws Exception {
        Runner r1 = new Runner("Karl", "Huber", LocalDate.of(1990,6,7), 'M', 75);
        Runner r2 = new Runner("Eva", "Schmidt", LocalDate.of(1997,10,26), 'W', 55);

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

        RunnerRepository repository = RunnerRepository.getInstance();
            repository.persistRunner(r1);
            repository.persistRunner(r2);


        RunRepository repository2 = RunRepository.getInstance();
            repository2.persistRun(l1);
            repository2.persistRun(l2);
            repository2.persistRun(l3);
            repository2.persistRun(l4);
            repository2.persistRun(l5);
    }
}
