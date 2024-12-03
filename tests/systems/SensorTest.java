package systems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

public class SensorTest {

	@Test
	public void iDsAreIncrementalTest() {
		Sensor sensorOne = new MockMotionSensor();
		Sensor sensorTwo = new MockMotionSensor();
		assertThat(sensorOne.getSensorID())
			.isLessThan(sensorTwo.getSensorID());
		
	}
	
	@Test
	public void addObserverTest() {
		Sensor sensor = new MockMotionSensor();
		SensorObserver obs = e -> {
			//Just for testing
		};
		sensor.addObserver(obs);
		Collection<SensorObserver> observers = sensor.getObservers();
		assertThat(observers).hasSize(1);
		assertThat(observers).containsOnlyOnce(obs);
	}

	@Test
	public void addNullObserverTest() {
		Sensor sensor = new MockMotionSensor();
		assertThatThrownBy(() -> sensor.addObserver(null))
			.isInstanceOf(NullPointerException.class)
			.hasMessage("Observer cannot be null.");
	}
	
	@Test
	public void removeObserverTest() {
		Sensor sensor = new MockMotionSensor();
		SensorObserver toRemove = e -> {
			//Just for testing
		};
		SensorObserver notToRemove = e -> {
			//Just for testing
		};
		Collection<SensorObserver> observers = sensor.getObservers();
		observers.addAll(Arrays.asList(toRemove, notToRemove));
		sensor.removeObserver(toRemove);
		
		assertThat(observers).hasSize(1);
		assertThat(observers).doesNotContain(toRemove);
		assertThat(observers).containsOnlyOnce(notToRemove);
	}
	
	@Test
	public void removeNullObserverTest() {
		Sensor sensor = new MockMotionSensor();
		assertThatThrownBy(() -> sensor.removeObserver(null))
			.isInstanceOf(NullPointerException.class)
			.hasMessage("Observer cannot be null.");
	}
	
	@Test
	public void addAndRemoveObserverTest() {
		SensorObserver observer = e -> {
			//Just for testing
		};
		Sensor sensor = new MockMotionSensor();
		
		sensor.addObserver(observer);
		assertThat(sensor.getObservers())
			.containsExactly(observer);
		
		sensor.removeObserver(observer);
		assertThat(sensor.getObservers())
			.isEmpty();
	}
	
    @Test
    public void newLuminosityDetectedCorrectNotifyTest() {
    	MockLuminositySensor sensor = new MockLuminositySensor();
    	SensorObserver observer = event -> {
	    	assertThat(event).
	    		isInstanceOf(LuminosityChangeEvent.class);
	    	assertThat(((LuminosityChangeEvent) event).getLuminosityDetected())
	    		.isEqualTo(50);
	    	assertThat(sensor).isEqualTo(event.getSensor());
	    };
		sensor.addObserver(observer);
		sensor.newLuminosityDetected(50.0);
    }
    
    @Test
    public void motionDetectedCorrectNotifyTest() {
    	MockMotionSensor sensor = new MockMotionSensor();
    	SensorObserver observer = event -> {
	    	assertThat(event).isInstanceOf(MotionDetectedEvent.class);
	    	assertThat(sensor).isEqualTo(event.getSensor());
	    };
		sensor.addObserver(observer);
		sensor.motionDetected();
    }

    @Test
    public void noMotionDetectedCorrectNotifyTest() {
    	MockMotionSensor sensor = new MockMotionSensor();
    	SensorObserver observer = event -> {
    		assertThat(event).isInstanceOf(MotionNotDetectedEvent.class);
    		assertThat(sensor).isEqualTo(event.getSensor());
    	};
		sensor.addObserver(observer);
		sensor.noMotionDetected();
    }

}
