package systems;

public class MotionDetectedEvent extends SensorEvent {
	
	public MotionDetectedEvent(Sensor sensor) {
		super(sensor);
	}

	@Override
	public void accept(SensorEventVisitor visitor) {
		visitor.visitMotionDetected(this);
	}

}
