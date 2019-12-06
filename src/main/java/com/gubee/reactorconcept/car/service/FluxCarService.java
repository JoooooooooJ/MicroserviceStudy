package com.gubee.reactorconcept.car.service;

import com.gubee.reactorconcept.car.event.CarEvents;
import com.gubee.reactorconcept.car.model.Car;
import com.gubee.reactorconcept.car.repository.CarRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@Service
public class FluxCarService {

    private final CarRepository carRepository;

    public FluxCarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Flux<Car> findAll(){
        return carRepository.findAll();
    }

    public Mono<Car> findById(String carId){
        return carRepository.findById(carId);
    }

    public Flux<CarEvents> streams(String carId){
        return findById(carId).flatMapMany(car -> {
            Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
            Flux<CarEvents> events = Flux.fromStream(
                    Stream.generate(()-> new CarEvents(car, new Date())));
            return Flux.zip(interval, events).map(Tuple2::getT2);
        });
    }
}
