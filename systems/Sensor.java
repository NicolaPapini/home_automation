package systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public abstract class Sensor {
	
	private Collection<SensorObserver> observers;
	private int sensorID;
	private static int iDGenerator = 0;
	
	public Sensor() {
		sensorID = iDGenerator++;
		observers=new ArrayList<>();
	}
	
	public void addObserver(SensorObserver observer) {
		Objects.requireNonNull(observer, "Observer cannot be null.");
		observers.add(observer);
	}
	
	public void removeObserver(SensorObserver observer) {
		Objects.requireNonNull(observer, "Observer cannot be null.");
		observers.remove(observer);
	}
	
	protected void notifyObservers(SensorEvent event) {
		observers.forEach(observer -> observer.updateState(event));
	}
	
	public int getSensorID() {
		return sensorID;
	}
	
	Collection<SensorObserver> getObservers() {
		return observers;
	}

}
