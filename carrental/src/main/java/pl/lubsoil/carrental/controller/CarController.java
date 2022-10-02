package pl.lubsoil.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lubsoil.carrental.model.Car;
import pl.lubsoil.carrental.service.CarService;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping("")
    public List<Car> list() {
        return carService.listAllCar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> get(@PathVariable Integer id) {
        try {
            Car car = carService.getCar(id);
            return new ResponseEntity<Car>(car, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Car>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody Car car) {
        car.setBorrowed(false);
        carService.saveCar(car);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rent/{id}")
    public ResponseEntity<?> rentCar(@PathVariable Integer id) {
        try {
            Car car = carService.getCar(id);
            if(car.isBorrowed() == false){
                car.changeBorrowed();
                carService.saveCar(car);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/unrent/{id}")
    public ResponseEntity<?> unrentCar(@PathVariable Integer id) {
        try {
            Car car = carService.getCar(id);
            if(car.isBorrowed() == true){
                car.changeBorrowed();
                carService.saveCar(car);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Car car, @PathVariable Integer id) {
        try {
            Car existCar = carService.getCar(id);
            car.setId(id);
            if(existCar.isBorrowed() != car.isBorrowed()){
                car.changeBorrowed();
            }
            carService.saveCar(car);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        carService.deleteCar(id);
    }
}