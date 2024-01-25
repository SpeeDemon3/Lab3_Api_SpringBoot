package com.ruiz.CarRegistry.controller;

import com.ruiz.CarRegistry.model.Car;
import com.ruiz.CarRegistry.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api") // Endpoint raiz de toda la api
public class CarController {

    @Autowired
    CarService carService;

    // Obtengo la lista para poder trabajar con ella desde esta parte del codigo
    List<Car> carList = new ArrayList<>();

    @GetMapping("/car")
    public void getCarData() {

        log.info("Application in operation!!!");

        carService.getCarData();

    }

    /**
     * Endpoint para mostrar el listado de coches
     * @return Respuesta 200(ok) || 500 (internal server error)
     */
    @GetMapping("/showCars")
    public ResponseEntity<?> showCars() {

        if (carService.returnListCars().size() >0) {
            // Si hay coches en la lista se muestran
            log.info("\tList Car:");
            carService.showCar();
            // Devuelve una respuesta HTTP 200 (OK) indicando que se encontro algun  coche
            return ResponseEntity.ok().build();
        } else {
            log.error("\tServer Error....");
            // Devuelve una respuesta HTTP 500 (internalServerError) indicando que no se encontraron coches
            return ResponseEntity.internalServerError().build();
        }

    }

    /**
     * Endpoint de tipo POST para añadir un coche
     * @param carRequest
     * @return
     */
    @PostMapping("/addCar")
    public ResponseEntity<?> addCar(@RequestBody Car carRequest) {
        // Imprime información sobre el coches recibido en la solicitud
        log.info("Add Car -> {}" + carRequest);

        // Llama al servicio para agregar el coche a la lista
        carService.addCar(carRequest);

        // Imprime la lista de coches después de agregar uno nuevo
        log.info("Car list:");
        carService.showCar();

        try {
            // Intenta manejar la operación exitosa y devuelve una respuesta 200 OK
            log.info("Car added successfully.");
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            // En caso de un error, se registra un mensaje de error
            // y devuelve un código de error interno del servidor
            log.error("Server error!!!");
            return ResponseEntity.internalServerError().build();
        }

    }


    /**
     * Endpoint de tipo GET para encontrar un coche por un id especifico
     * @param id -> ID del coche a buscar
     * @return -> Respuesta 200(Con exito) o 404(Sin exito)
     */
    @GetMapping("/showCar/{id}")
    public ResponseEntity<?> showCar(@PathVariable Integer id){

        // Llamada al servicio para buscar el coche por ID
        Car car = carService.searchCarId(id);

        // Verifica si se encontró un coche con el ID especificado
        if (car != null) {
            // Muestra el coche encontrado en log
            log.info("Found Car -> " + car.toString());
            // Devuelve una respuesta HTTP 200 (OK) indicando que se encontró el coche
            return ResponseEntity.ok().build();
        } else {
            // Si no se encuentra el coche, registra un mensaje de error en los logs
            log.error("The ID [" + id + "] does not exist.");
            // Devuelve una respuesta HTTP 404 (Not Found) indicando que no se encontró el coche
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Enpoint para modificar un objteo buscandolo por el ID y modificandolo dentro de la lista,
     * controlando si no encuentra el id o si surgiese otro tipo de error
     * @param id
     * @param carRequest
     * @return
     */
    @PutMapping("/alterCar/{id}")
    public ResponseEntity<?> alterCar(@PathVariable Integer id, @RequestBody Car carRequest) {

        try {
            // Busca el coche por ID
            Car car = carService.searchCarId(id);

            if (car != null) {

                carList = carService.returnListCars();

                // Obtengo el indice del coche a modificar en la lista
                int indexCar = carList.indexOf(car);

                // Sustituyo el objeto antiguo por el nuevo
                carList.set(indexCar, carRequest);

                log.info("Modified car data: " + carRequest.toString());

                log.info("List Cars:");
                // Muestro la lista actualizada
                carService.showCar();

                // Retorno un 200
                return ResponseEntity.ok().build();

            } else {
                // Retorna 404 Not Found si no se encuentra el coche con el ID dado
                log.error("Car with ID [" + id + "] not found");
                return ResponseEntity.notFound().build();
            }


        } catch (Exception e){
            // Retorna un error interno del servidor en caso de cualquier excepción
            log.error("Error during car modification.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Integer id) {

        // Obtengo el coche por el id
        Car car = carService.searchCarId(id);

        if (car != null) {
            log.info("Car remove -> " + car);
            // Si existe lo elimino de la lista
            carService.deleteCar(car);
            // Muestro los coches restantes
            carService.showCar();
            // Retorno un 200
            return ResponseEntity.ok().build();
        } else if (car == null) {
            log.warn("Car not found!!!");
            // Retorna 404 Not Found si no se encuentra el coche con el ID dado
            return ResponseEntity.notFound().build();
        } else {
            log.error("Server Error");
            // Para cualquier otro tipo de error
            return ResponseEntity.internalServerError().build();
        }

    }


}
