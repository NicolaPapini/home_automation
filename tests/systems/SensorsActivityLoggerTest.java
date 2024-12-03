package systems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import utility.LogEntry;
import utility.LoggerStrategies;
import utility.MockTimeStampProvider;

public class SensorsActivityLoggerTest {
	
	MockTimeStampProvider timeProvider;
	SensorsActivityLogger logger;
	
	@Before 
	public void init() {
		timeProvider = new MockTimeStampProvider();
		logger = new SensorsActivityLogger(timeProvider);
	}
	
	@Test
	public void addSensorTest() {
		MockMotionSensor sensor = new MockMotionSensor();
		logger.addSensor(sensor);
		
		assertThat(sensor.getObservers()).hasSize(1);
		assertThat(sensor.getObservers())
			.containsExactly(logger);
		assertThat(logger.getSensors()).hasSize(1);
		assertThat(logger.getSensors())
			.containsExactly(sensor);
	}

	@Test
	public void addNullSensorTest() {
		assertThatThrownBy(() -> logger.addSensor(null))
			.isInstanceOf(NullPointerException.class)
			.hasMessage("Sensor cannot be null.");
	}
	
	@Test
	public void removeSensorTest() {
		MockLuminositySensor toRemove = new MockLuminositySensor();
		MockMotionSensor notToRemove = new MockMotionSensor();
		Collection<Sensor> sensors = logger.getSensors();
		sensors.addAll(Arrays.asList(toRemove, notToRemove));
		logger.removeSensor(toRemove);
		
		assertThat(sensors).hasSize(1);
		assertThat(sensors).doesNotContain(toRemove);
		assertThat(sensors).containsExactly(notToRemove);
	}
	
	@Test
	public void removeNullSensorTest() {
		assertThatThrownBy(() -> logger.removeSensor(null))
			.isInstanceOf(NullPointerException.class)
			.hasMessage("Sensor cannot be null.");
	}
	
	@Test
	public void motionDetectedLogTest() {
		MockMotionSensor motionSensor = new MockMotionSensor();
		logger.addSensor(motionSensor);
		
		timeProvider.setTime("2024-04-02 13:57:50");
		LogEntry entry = new LogEntry(
				"2024-04-02 13:57:50",
				motionSensor.getSensorID(),
				"Motion detected"
		);
		motionSensor.motionDetected();
		
		assertThat(logger.getActivityLog()).hasSize(1);
		assertThat(logger.getActivityLog())
			.containsExactly(entry);
	}
	
	@Test
	public void motionNotDetectedLogTest() {
		MockMotionSensor motionSensor = new MockMotionSensor();
		logger.addSensor(motionSensor);
		
		timeProvider.setTime("2024-04-02 14:09:05");
		LogEntry entry = new LogEntry(
				"2024-04-02 14:09:05", 
				motionSensor.getSensorID(),
				"No motion detected"
		);
		motionSensor.noMotionDetected();
		
		assertThat(logger.getActivityLog()).hasSize(1);
		assertThat(logger.getActivityLog())
			.containsExactly(entry);
	}
	
	@Test
	public void luminosityChangeLogTest() {
		MockLuminositySensor luminositySensor = new MockLuminositySensor();
		logger.addSensor(luminositySensor);
		
		timeProvider.setTime("2024-04-02 14:11:15");
		LogEntry entry = new LogEntry(
				"2024-04-02 14:11:15",
				luminositySensor.getSensorID(), 
				"Luminosity changed to 20.0"
		);
		luminositySensor.newLuminosityDetected(20);
		
		assertThat(logger.getActivityLog()).hasSize(1);
		assertThat(logger.getActivityLog())
			.containsExactly(entry);
	}
	
	@Test
	public void multipleSensorsAndEventsLogTest() {
		MockLuminositySensor luminositySensorOne = new MockLuminositySensor();
		MockLuminositySensor luminositySensorTwo = new MockLuminositySensor();
		MockMotionSensor motionSensor = new MockMotionSensor();
		logger.addSensor(luminositySensorOne);
		logger.addSensor(luminositySensorTwo);
		logger.addSensor(motionSensor);
		
		timeProvider.setTime("2024-04-02 14:38:43");
		LogEntry motionDetectedEntry = new LogEntry(
				"2024-04-02 14:38:43", 
				motionSensor.getSensorID(), 
				"Motion detected"
		);
		motionSensor.motionDetected();
		
		timeProvider.setTime("2024-04-02 14:39:43");
		LogEntry motionNotDetectedEntry = new LogEntry(
				"2024-04-02 14:39:43", 
				motionSensor.getSensorID(), 
				"No motion detected"
		);
		motionSensor.noMotionDetected();
		
		timeProvider.setTime("2024-01-04 15:40:25");
		LogEntry luminosityOneChangeEntry = new LogEntry(
				"2024-01-04 15:40:25", 
				luminositySensorOne.getSensorID(), 
				"Luminosity changed to 30.0"
		);
		luminositySensorOne.newLuminosityDetected(30);
		
		timeProvider.setTime("2024-01-04 15:42:54");
		LogEntry luminosityTwoChangeEntry = new LogEntry(
				"2024-01-04 15:42:54", 
				luminositySensorTwo.getSensorID(), 
				"Luminosity changed to 45.0"
		);
		luminositySensorTwo.newLuminosityDetected(45);

		assertThat(logger.getActivityLog()).hasSize(4);
		assertThat(logger.getActivityLog())
			.contains(
					motionDetectedEntry,
					luminosityOneChangeEntry,
					luminosityTwoChangeEntry,
					motionNotDetectedEntry
					);
	}
	
	@Test
	public void getLogSortedByTimeTest() {
		MockLuminositySensor luminositySensorOne = new MockLuminositySensor();
		MockLuminositySensor luminositySensorTwo = new MockLuminositySensor();
		MockMotionSensor motionSensor = new MockMotionSensor();
		logger.addSensor(luminositySensorOne);
		logger.addSensor(luminositySensorTwo);
		logger.addSensor(motionSensor);
		
		timeProvider.setTime("2024-04-02 14:38:43");
		motionSensor.motionDetected();
		
		timeProvider.setTime("2024-01-03 15:42:54");
		luminositySensorTwo.newLuminosityDetected(45);
		
		timeProvider.setTime("2024-01-04 15:40:25");
		luminositySensorOne.newLuminosityDetected(30);
		
		assertThat(logger.getLog())
			.isEqualTo(
					String.join(System.lineSeparator(), 
					new LogEntry(
							"2024-01-03 15:42:54", 
							luminositySensorTwo.getSensorID(), 
							"Luminosity changed to 45.0").toString(),
					new LogEntry(
							"2024-01-04 15:40:25", 
							luminositySensorOne.getSensorID(), 
							"Luminosity changed to 30.0").toString(),
					new LogEntry(
							"2024-04-02 14:38:43", 
							motionSensor.getSensorID(), 
							"Motion detected").toString())
					);
	}
	
	@Test
	public void getLogSortedByEventTest() {
		MockLuminositySensor luminositySensorOne = new MockLuminositySensor();
		MockLuminositySensor luminositySensorTwo = new MockLuminositySensor();
		MockMotionSensor motionSensor = new MockMotionSensor();
		logger.addSensor(luminositySensorOne);
		logger.addSensor(luminositySensorTwo);
		logger.addSensor(motionSensor);
		logger.setSortingStrategy(LoggerStrategies.sortByDescription());

		timeProvider.setTime("2024-04-02 14:38:43");
		motionSensor.motionDetected();
		
		timeProvider.setTime("2024-04-02 14:39:21");
		motionSensor.noMotionDetected();
		
		timeProvider.setTime("2024-04-02 14:39:23");
		motionSensor.motionDetected();
		
		timeProvider.setTime("2024-01-04 15:42:54");
		luminositySensorTwo.newLuminosityDetected(45);
		
		timeProvider.setTime("2024-01-04 15:45:25");
		luminositySensorOne.newLuminosityDetected(30);
		
		assertThat(logger.getLog())
			.isEqualTo(
					String.join(System.lineSeparator(), 
					new LogEntry(
							"2024-01-04 15:45:25",
							luminositySensorOne.getSensorID(), 
							"Luminosity changed to 30.0").toString(),
					new LogEntry(
							"2024-01-04 15:42:54",
							luminositySensorTwo.getSensorID(), 
							"Luminosity changed to 45.0").toString(),
					new LogEntry(
							"2024-04-02 14:38:43",
							motionSensor.getSensorID(), 
							"Motion detected").toString(),
					new LogEntry(
							"2024-04-02 14:39:23",
							motionSensor.getSensorID(), 
							"Motion detected").toString(),
					new LogEntry(
							"2024-04-02 14:39:21", 
							motionSensor.getSensorID(), 
							"No motion detected").toString())
					);
	}
	
	@Test
	public void resetLogTest() {
		MockLuminositySensor sensorOne = new MockLuminositySensor();
		MockLuminositySensor sensorTwo = new MockLuminositySensor();
		MockLuminositySensor sensorThree = new MockLuminositySensor();
		logger.addSensor(sensorOne);
		logger.addSensor(sensorTwo);
		logger.addSensor(sensorThree);
		
		logger.resetLog();
		assertThat(logger.getActivityLog()).isEmpty();
		assertThat(sensorOne.getObservers())
			.doesNotContain(logger);
		assertThat(sensorTwo.getObservers())
			.doesNotContain(logger);
		assertThat(sensorThree.getObservers())
			.doesNotContain(logger);
	}
	
}
