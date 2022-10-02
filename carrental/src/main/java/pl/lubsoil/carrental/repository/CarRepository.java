package pl.lubsoil.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lubsoil.carrental.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer>
{

}