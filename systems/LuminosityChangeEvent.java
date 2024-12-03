package systems;

public class LuminosityChangeEvent extends SensorEvent {
	
	private double luminosityDetected;
	
	public LuminosityChangeEvent(double luminosityDetected, Sensor sensor) {
		super(sensor);
		this.luminosityDetected = luminosityDetected;
	}
	
	@Override
	public void accept(SensorEventVisitor visitor) {
		visitor.visitLuminosityChange(this);
	}

	public double getLuminosityDetected() {
		return luminosityDetected;
	}

}
