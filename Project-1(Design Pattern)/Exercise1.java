// Exercise1Patterns.java
import java.util.*;

// 1. Behavioral Pattern - Strategy
interface PaymentStrategy {
    void pay(int amount);
}
class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " using Credit Card."); }
}
class UPIPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " using UPI."); }
}
class PaymentContext {
    private PaymentStrategy strategy;
    public void setStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
    public void payAmount(int amount) { strategy.pay(amount); }
}

// 2. Behavioral Pattern - Observer
interface Observer { void update(String msg); }
class Astronaut implements Observer {
    private String name;
    public Astronaut(String name) { this.name = name; }
    public void update(String msg) { System.out.println(name + " received update: " + msg); }
}
class MissionControl {
    private List<Observer> observers = new ArrayList<>();
    public void addObserver(Observer o) { observers.add(o); }
    public void notifyAllObservers(String msg) {
        for(Observer o: observers) o.update(msg);
    }
}

// 3. Creational Pattern - Singleton
class Logger {
    private static Logger instance;
    private Logger() {}
    public static Logger getInstance() {
        if(instance == null) instance = new Logger();
        return instance;
    }
    public void log(String msg) { System.out.println("[LOG] " + msg); }
}

// 4. Creational Pattern - Factory
abstract class Vehicle { abstract void drive(); }
class Car extends Vehicle { void drive(){ System.out.println("Driving a Car"); } }
class Bike extends Vehicle { void drive(){ System.out.println("Riding a Bike"); } }
class VehicleFactory {
    public static Vehicle getVehicle(String type){
        if(type.equalsIgnoreCase("car")) return new Car();
        else if(type.equalsIgnoreCase("bike")) return new Bike();
        return null;
    }
}

// 5. Structural Pattern - Adapter
interface MediaPlayer { void play(String filename); }
class MP3Player implements MediaPlayer {
    public void play(String filename) { System.out.println("Playing MP3 file: " + filename); }
}
class MP4Player {
    public void playMP4(String filename) { System.out.println("Playing MP4 file: " + filename); }
}
class MediaAdapter implements MediaPlayer {
    private MP4Player mp4Player;
    public MediaAdapter(MP4Player mp4Player) { this.mp4Player = mp4Player; }
    public void play(String filename) { mp4Player.playMP4(filename); }
}

// 6. Structural Pattern - Decorator
interface Coffee { String getDescription(); double cost(); }
class SimpleCoffee implements Coffee {
    public String getDescription() { return "Simple Coffee"; }
    public double cost() { return 5.0; }
}
class MilkDecorator implements Coffee {
    private Coffee coffee;
    public MilkDecorator(Coffee coffee){ this.coffee = coffee; }
    public String getDescription() { return coffee.getDescription() + ", Milk"; }
    public double cost() { return coffee.cost() + 2.0; }
}

public class Exercise1{
    public static void main(String[] args) {
        // Strategy Pattern
        PaymentContext pc = new PaymentContext();
        pc.setStrategy(new CreditCardPayment()); pc.payAmount(100);
        pc.setStrategy(new UPIPayment()); pc.payAmount(200);

        // Observer Pattern
        MissionControl mc = new MissionControl();
        mc.addObserver(new Astronaut("Neil"));
        mc.addObserver(new Astronaut("Buzz"));
        mc.notifyAllObservers("Mission Launch Successful!");

        // Singleton
        Logger.getInstance().log("Singleton Logger Working!");

        // Factory
        Vehicle v1 = VehicleFactory.getVehicle("car"); v1.drive();
        Vehicle v2 = VehicleFactory.getVehicle("bike"); v2.drive();

        // Adapter
        MediaPlayer mp3 = new MP3Player(); mp3.play("song.mp3");
        MediaPlayer mp4 = new MediaAdapter(new MP4Player()); mp4.play("movie.mp4");

        // Decorator
        Coffee coffee = new MilkDecorator(new SimpleCoffee());
        System.out.println(coffee.getDescription() + " costs $" + coffee.cost());
    }
}
