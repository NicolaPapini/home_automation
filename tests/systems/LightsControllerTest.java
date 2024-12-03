package systems;

import org.junit.Test;

import utility.LightFixture;
import utility.MockSmartBulb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;

public class LightsControllerTest {
	
	private LightsController lightsController;
	private MotionSensor motionSensor;
	private LuminositySensor luminositySensor;
	
	@Before
	public void init() {
		motionSensor = new MockMotionSensor();
		luminositySensor = new MockLuminositySensor();
		lightsController = new LightsController(motionSensor, luminositySensor);
	}
	
	@Test
	public void addLightFixtureTest() {
		MockSmartBulb bulb = new MockSmartBulb();
		lightsController.addLightFixture(bulb);
		Collection<LightFixture> lights = lightsController.getLights();
		
		assertThat(lights).hasSize(1);
		assertThat(lights).containsOnlyOnce(bulb);
	}

	@Test
	public void addNullLightFixtureTest() {
		assertThatThrownBy(() -> lightsController.addLightFixture(null))
			.isInstanceOf(NullPointerException.class)
			.hasMessage("Light cannot be null.");
	}
	
	@Test
	public void removeLightFixtureTest() {
		MockSmartBulb toRemove = new MockSmartBulb();
		MockSmartBulb notToRemove = new MockSmartBulb();
		Collection<LightFixture> lights = lightsController.getLights();
		lights.addAll(Arrays.asList(toRemove, notToRemove));
		lightsController.removeLightFixture(toRemove);
		
		assertThat(lights).hasSize(1);
		assertThat(lights).doesNotContain(toRemove);
		assertThat(lights).containsOnlyOnce(notToRemove);
	}
	
	@Test
	public void removeNullLightFixtureTest() {
		assertThatThrownBy(() -> lightsController.removeLightFixture(null))
			.isInstanceOf(NullPointerException.class)
			.hasMessage("Light cannot be null.");
	}
	
	@Test
	public void addAndRemoveLightFixtureTest() {
		MockSmartBulb bulb = new MockSmartBulb();
		lightsController.addLightFixture(bulb);
		assertThat(lightsController.getLights())
			.containsExactly(bulb);
		lightsController.removeLightFixture(bulb);
		assertThat(lightsController.getLights())
			.isEmpty();
	}
	
	@Test
	public void motionDetectedEventTest() {
		lightsController.getLights()
			.addAll(Arrays.asList(new MockSmartBulb(),new MockSmartBulb()));
		
		assertThat(lightsController.getLights())
			.allMatch(lamp -> !lamp.isOn());
		motionSensor.motionDetected();
		assertThat(lightsController.getLights())
			.allMatch(lamp -> lamp.isOn());
	}
	
	@Test
	public void motionNotDetectedEventTest() {
		lightsController.getLights()
			.addAll(Arrays.asList(new MockSmartBulb(),new MockSmartBulb()));
		
		motionSensor.motionDetected();
		assertThat(lightsController.getLights())
			.allMatch(lamp -> lamp.isOn());
		
		motionSensor.noMotionDetected();
		assertThat(lightsController.getLights())
			.allMatch(lamp -> !lamp.isOn());
		
	}
	
	@Test
	public void luminosityChangeEventTest() {
		lightsController.getLights()
			.addAll(Arrays.asList(new MockSmartBulb(),new MockSmartBulb()));
		
		luminositySensor.newLuminosityDetected(15);
		assertThat(lightsController.getLights())
			.allMatch(lamp -> lamp.getBrightness() == 15);
		
		luminositySensor.newLuminosityDetected(35);
		assertThat(lightsController.getLights())
			.allMatch(lamp -> lamp.getBrightness() == 35);
	}	
}
