package systems;

public class MockMotionSensor extends MotionSensor {

	@Override
	public void motionDetected() {
		SensorEvent event = new MotionDetectedEvent(this);
		notifyObservers(event);
	}
	
	// This simulates the sensor not detecting any motion for a set amount of time
	@Override
	public void noMotionDetected() {
		SensorEvent event = new MotionNotDetectedEvent(this);
		notifyObservers(event);
	}
	
}
