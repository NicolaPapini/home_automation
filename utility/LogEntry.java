package utility;

import java.util.Objects;

public class LogEntry {
	
	private final String timeStamp;
	private final int sensorId;
	private final String description;
	
	public LogEntry(String timeStamp, int sensorId, String description) {
		this.timeStamp = timeStamp;
		this.sensorId = sensorId;
		this.description = description;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Time: " +  timeStamp + ", Sensor ID: " + sensorId + ", Event: " + description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, sensorId, timeStamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogEntry other = (LogEntry) obj;
		
		return Objects.equals(description, other.description) 
				&& sensorId == other.sensorId
				&& Objects.equals(timeStamp, other.timeStamp);
	}
}