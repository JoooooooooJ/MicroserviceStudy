package com.gubee.reactorconcept.car.repository;

import com.gubee.reactorconcept.car.model.Car;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CarRepository extends ReactiveMongoRepository<Car,String> {
}
