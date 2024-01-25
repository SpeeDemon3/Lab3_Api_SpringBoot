package com.ruiz.CarRegistry.service.impl;

import com.ruiz.CarRegistry.model.Car;
import com.ruiz.CarRegistry.repository.CarRepository;
import com.ruiz.CarRegistry.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j // Permite utilizar el sistema de logs
public class CarServiceImpl implements CarService {

    // Creo un objeto usando el metodo build() de @Builder de Lombok
    Car car = Car.builder()
            .id(3)
            .brand("Seat")
            .model("Leon")
            .milleage(78000)
            .price(41000.3)
            .year(2017)
            .description("Cupra 300 ST")
            .colour("white")
            .fuelType("gasoline")
            .numDoors(5)
            .build();

    // Objeto de tipo CarRepository
    @Autowired
    private CarRepository carRepository;

    // Obtengo el valor que contiene la property "author.name" y lo guardo en un String
    @Value("${author.name}")
    private String author;

    @Override
    public void getCarData() {

        // Imprimo el valor de la property en un log
        log.info("Author:  " + author);

        // Imprimo los atributos del objeto car utilizando el metodo toString()
        log.info("Car data: {}" + car.toString());

        log.error("Test -> {}" + car.getDescription());

        carRepository.getCarData();


    }

    // ArrayList para guardar los coches que reciba por POST
    List<Car> carsPost = new ArrayList<>();


    /**
     * Metodo para retornar la lista de coches y poder utilizarla desde otra
     * parte de la aplicacion
     * @return -> Lista de coches
     */
    @Override
    public List<Car> returnListCars() {
        return carsPost;
    }

    /**
     * Elimina un objeto de la lista
     * @param car -> Objeto
     */
    @Override
    public void deleteCar(Car car) {
        carsPost.remove(car);
    }


    /**
     * Metodo para agregar un coche a la lista
     * @param car -> Objeto
     */
    @Override
    public void addCar(Car car) {
        carsPost.add(car);
    }

    /**
     * Metodo para mostrar los coches que contenga la lista carsPost
     */
    @Override
    public void showCar() {
        carsPost.stream().forEach(car -> System.out.println(car));
    }

    /**
     * Metodo para motrar un coche buscandolo por su id
     * @param id -> Del objeto de tipo coche
     */
    @Override
    public Car searchCarId(Integer id) {

        return carsPost.stream()
                .filter(car -> car.getId() == id)
                // Obtiene el primer coche que cumple con el filtro, o devuelve null si no hay coincidencias
                .findFirst()
                .orElse(null);


    }



}
