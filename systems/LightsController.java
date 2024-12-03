package systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import utility.LightFixture;

public class LightsController implements SensorObserver {

	private Collection<LightFixture> lights;

	public LightsController(MotionSensor motionSensor, LuminositySensor luminositySensor) {
		motionSensor.addObserver(this);
		luminositySensor.addObserver(this);
		lights = new ArrayList<>();
	}

	@Override
	public void updateState(SensorEvent event) {
		event.accept(new SensorEventVisitor() {

			@Override
			public void visitNoMotionDetected(MotionNotDetectedEvent e) {
				lights.forEach(LightFixture::turnOff);
			}

			@Override
			public void visitMotionDetected(MotionDetectedEvent e) {
				lights.forEach(LightFixture::turnOn);
			}

			@Override
			public void visitLuminosityChange(LuminosityChangeEvent e) {
				lights.forEach(light -> light.setBrightness(e.getLuminosityDetected()));
			}
		});
	}

	public void addLightFixture(LightFixture light) {
		Objects.requireNonNull(light, "Light cannot be null.");
		lights.add(light);
	}

	public void removeLightFixture(LightFixture light) {
		Objects.requireNonNull(light, "Light cannot be null.");
		lights.remove(light);
	}

	Collection<LightFixture> getLights() {
		return lights;
	}

}
