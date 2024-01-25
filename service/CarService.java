package com.ruiz.CarRegistry.service;

import com.ruiz.CarRegistry.model.Car;

import java.util.List;

public interface CarService {

    void getCarData();

    void addCar(Car car);

    void showCar();

    Car searchCarId(Integer id);

    List<Car> returnListCars();

    void deleteCar(Car car);

}
