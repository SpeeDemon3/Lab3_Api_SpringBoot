package com.ruiz.CarRegistry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Antonio Ruiz Benito
 *
 * Clase que representa un coche
 */

@Data // Genera los metodos get, set, toString, hashCode y equals
@AllArgsConstructor // Un constructor con todos lo argumentos
@Builder // Permite establecer solo los atributos que necesite omitiendo el resto
@NoArgsConstructor // Genera un constructor sin argumentos
public class Car {

    // Atributos
    private Integer id;
    private String brand;
    private String model;
    private Integer milleage;
    private Double price;
    private Integer year;
    private String description;
    private String colour;
    private String fuelType;
    private Integer numDoors;

}
