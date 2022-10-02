package pl.lubsoil.carrental.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    private int id;
    private String carCode;
    private String model;
    private int seats;
    private boolean borrowed;

    public Car() {
        this.borrowed = false;
    }

    public Car(int id, String carCode, String model, int seats) {
        this.id = id;
        this.carCode = carCode;
        this.model = model;
        this.seats = seats;
        this.borrowed = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String s){
        this.carCode = s;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String s){
        this.model = s;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int s){
        this.seats = s;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void changeBorrowed(){
        this.borrowed = !this.borrowed;
    }

    public void setBorrowed(boolean borrowed){
        this.borrowed = borrowed;
    }

}
