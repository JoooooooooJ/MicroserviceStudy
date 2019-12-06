package com.gubee.reactorconcept.car.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    private String id;
    private String model;

    public Car(String toString, String model) {
    }
}
