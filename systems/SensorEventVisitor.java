package systems;

public interface SensorEventVisitor {
	
	void visitMotionDetected(MotionDetectedEvent e);
	void visitNoMotionDetected(MotionNotDetectedEvent e);
	void visitLuminosityChange(LuminosityChangeEvent e);

}
