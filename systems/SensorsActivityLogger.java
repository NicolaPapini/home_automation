package systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

import utility.LogEntry;
import utility.LoggerSortingStrategy;
import utility.LoggerStrategies;
import utility.TimeStampProvider;

public class SensorsActivityLogger implements SensorObserver{
	
	private LoggerSortingStrategy strategy;
	private Collection<LogEntry> activityLog;
	private Collection<Sensor> sensors;
	private TimeStampProvider clock;
	
	public SensorsActivityLogger(TimeStampProvider clock) {
		this.clock = clock;
		strategy = LoggerStrategies.sortByTime();
		activityLog = new ArrayList<>();
		sensors = new ArrayList<>();
	}
	
	public void addSensor(Sensor sensor) {
		Objects.requireNonNull(sensor,"Sensor cannot be null.");
		sensors.add(sensor);
		sensor.addObserver(this);
	}
	
	public void removeSensor(Sensor sensor) {
		Objects.requireNonNull(sensor,"Sensor cannot be null.");
		sensors.remove(sensor);
		sensor.removeObserver(this);
	}
	
	@Override
	public void updateState(SensorEvent event) {
		Sensor eventSensor = event.getSensor();
		EventDescriptionVisitor descriptionVisitor = new EventDescriptionVisitor();
		event.accept(descriptionVisitor);
		
		event.accept(new SensorEventVisitor() {
			@Override
			public void visitNoMotionDetected(MotionNotDetectedEvent e) {
				activityLog.add(
					new LogEntry(
						clock.now(), 
						eventSensor.getSensorID(), 
						descriptionVisitor.getEventDescription()));
			}
			
			@Override
			public void visitMotionDetected(MotionDetectedEvent e) {
				activityLog.add(
					new LogEntry(
						clock.now(), 
						eventSensor.getSensorID(),
						descriptionVisitor.getEventDescription()));
			}
			
			@Override
			public void visitLuminosityChange(LuminosityChangeEvent e) {
				activityLog.add(
					new LogEntry(
						clock.now(), 
						eventSensor.getSensorID(), 
						descriptionVisitor.getEventDescription()));
			}
		});
	}

	public void setSortingStrategy(LoggerSortingStrategy sortingStrategy) {
		strategy = sortingStrategy;
	}
	
	public Iterator<LogEntry> logIterator(){
		return activityLog.iterator();
	}
	
	public Iterator<Sensor> sensorsIterator(){
		return sensors.iterator();
	}
	
	public String getLog() {
		return activityLog
				.stream()
				.sorted(strategy.getSortingStrategy())
				.map(LogEntry::toString)
				.collect(Collectors.joining(System.lineSeparator()));
	}
	
	public void resetLog() {
		sensors.forEach(sensor -> sensor.removeObserver(this));
		sensors.clear();
	}
	
	Collection<LogEntry> getActivityLog() {
		return activityLog;
	}

	Collection<Sensor> getSensors() {
		return sensors;
	}
	
}
