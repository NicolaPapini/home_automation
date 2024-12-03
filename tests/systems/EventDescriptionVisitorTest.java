package systems;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

public class EventDescriptionVisitorTest {

	private EventDescriptionVisitor visitor;
	
	@Before
	public void init() {
		visitor = new EventDescriptionVisitor();
	}
	
	@Test
	public void visitMotionDetectedDescriptionTest() {
		MockMotionSensor sensor = new MockMotionSensor();
		MotionDetectedEvent e = new MotionDetectedEvent(sensor);
		e.accept(visitor);
		assertThat(visitor.getEventDescription())
			.isEqualTo("Motion detected");
	}
	
	@Test
	public void visitMotionNotDetectedDescriptionTest() {
		MockMotionSensor sensor = new MockMotionSensor();
		MotionNotDetectedEvent e = new MotionNotDetectedEvent(sensor);
		e.accept(visitor);
		assertThat(visitor.getEventDescription())
			.isEqualTo("No motion detected");
	}
	
	@Test
	public void visitLuminosityChangeEventDescriptionTest() {
		MockMotionSensor sensor = new MockMotionSensor();
		LuminosityChangeEvent e = new LuminosityChangeEvent(33, sensor);
		e.accept(visitor);
		assertThat(visitor.getEventDescription())
			.isEqualTo("Luminosity changed to 33.0");
	}

}
