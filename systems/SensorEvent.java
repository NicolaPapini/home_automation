package systems;

public abstract class SensorEvent {
	
	private Sensor sensor;
	
	public SensorEvent(Sensor sensor) {
		this.sensor = sensor;
	}
	
	public Sensor getSensor() {
		return sensor;
	}
	
	public abstract void accept(SensorEventVisitor visitor);
	
}
