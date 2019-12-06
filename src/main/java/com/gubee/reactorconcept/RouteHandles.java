package com.gubee.reactorconcept;

import com.gubee.reactorconcept.car.event.CarEvents;
import com.gubee.reactorconcept.car.model.Car;
import com.gubee.reactorconcept.car.service.FluxCarService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
public class RouteHandles {

    private final FluxCarService fluxCarService;

    public RouteHandles(FluxCarService fluxCarService) {
        this.fluxCarService = fluxCarService;
    }

    public Mono<ServerResponse> allCars(ServerRequest serverRequest){
        return ServerResponse.ok()
                .body(fluxCarService.findAll(), Car.class)
                .doOnError(throwable -> new IllegalStateException("KK DEU ERRO"));
    }

    public Mono<ServerResponse> byId(ServerRequest serverRequest){
        String carId = serverRequest.pathVariable("carId");
        return ServerResponse.ok()
                .body(fluxCarService.findById(carId), Car.class)
                .doOnError(throwable -> new IllegalStateException("KK DEU ERRO DENOVO KK"));
    }

    public Mono<ServerResponse> events(ServerRequest serverRequest){
        String carId = serverRequest.pathVariable("carId");
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(fluxCarService.streams(carId), CarEvents.class)
                .doOnError(throwable -> new IllegalStateException("KK DEU ERRO DENOVO KK CANSEI"));
    }

    @Bean
    RouterFunction<?> routes(RouteHandles routeHandles){
        return RouterFunctions.route(
                RequestPredicates.GET("/cars"),routeHandles::allCars)
                .andRoute(RequestPredicates.GET("/cars/{carId}"),routeHandles::byId)
                .andRoute(RequestPredicates.GET("cars/{carId}/events"),routeHandles::events);
    }
}
