package systems;

public class MotionNotDetectedEvent extends SensorEvent {
	
	public MotionNotDetectedEvent(Sensor sensor) {
		super(sensor);
	}

	@Override
	public void accept(SensorEventVisitor visitor) {
		visitor.visitNoMotionDetected(this);
	}

}
