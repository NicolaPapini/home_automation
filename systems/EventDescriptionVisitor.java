package systems;

public class EventDescriptionVisitor implements SensorEventVisitor {
	
	private String description = "";
	
	@Override
	public void visitMotionDetected(MotionDetectedEvent e) {
		description = "Motion detected";
	}

	@Override
	public void visitNoMotionDetected(MotionNotDetectedEvent e) {
		description = "No motion detected";
	}

	@Override
	public void visitLuminosityChange(LuminosityChangeEvent e) {
		description = "Luminosity changed to " + e.getLuminosityDetected();
	}

	public String getEventDescription() {
		return description;
	}
	
}
