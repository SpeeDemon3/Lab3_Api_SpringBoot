package com.ruiz.CarRegistry.repository.impl;

import com.ruiz.CarRegistry.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class CarRepositoryImpl implements CarRepository {

    @Override
    public void getCarData() {

      log.info("I'm repository");

    }
}
