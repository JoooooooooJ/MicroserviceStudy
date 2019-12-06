package com.gubee.reactorconcept.car.controller;

import com.gubee.reactorconcept.car.event.CarEvents;
import com.gubee.reactorconcept.car.model.Car;
import com.gubee.reactorconcept.car.service.FluxCarService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
class CarController {

    private final FluxCarService fluxCarService;

    CarController(FluxCarService fluxCarService) {
        this.fluxCarService = fluxCarService;
    }

    @GetMapping("/cars")
    public Flux<Car> all() {
        return fluxCarService.findAll();
    }

    @GetMapping("/cars/{carId}")
    public Mono<Car> byId(@PathVariable String carId) {
        return fluxCarService.findById(carId);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/cars/{carId}/events")
    public Flux<CarEvents> eventsOfStreams(@PathVariable String carId) {
        return fluxCarService.streams(carId);
    }
}
