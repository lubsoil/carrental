package pl.lubsoil.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lubsoil.carrental.model.Car;
import pl.lubsoil.carrental.repository.CarRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> listAllCar() {
        return carRepository.findAll();
    }

    public void saveCar(Car user) {
        carRepository.save(user);
    }

    public Car getCar(Integer id) {
        return carRepository.findById(id).get();
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}