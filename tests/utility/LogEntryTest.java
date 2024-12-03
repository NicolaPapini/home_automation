package utility;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class LogEntryTest {

	@Test
	public void testEquals() {
		LogEntry firstEntry = new LogEntry("2024-04-02 16:21:43", 3, "Motion detected");
		LogEntry secondEntry = new LogEntry("2024-04-02 16:21:43", 3, "Motion detected");
		LogEntry thirdEntry = new LogEntry("2024-04-02 16:22:43", 10, "No motion detected");
		
		assertThat(firstEntry).isEqualTo(secondEntry);
		assertThat(firstEntry).isNotEqualTo(thirdEntry);
	}

	@Test
	public void testHashCode() {
		LogEntry firstEntry = new LogEntry("2024-04-02 16:23:15", 5, "Motion detected");
		LogEntry secondEntry = new LogEntry("2024-04-02 16:23:15", 5, "Motion detected");
		LogEntry thirdEntry = new LogEntry("2024-05-03 16:25:33", 13, "No motion detected");
		
		assertThat(firstEntry.hashCode())
			.isEqualTo(secondEntry.hashCode());
		assertThat(firstEntry.hashCode())
			.isNotEqualTo(thirdEntry.hashCode());
	}
	
	@Test
	public void testToString() {
		assertThat("Time: 2024-04-02 16:25:33, Sensor ID: 10, Event: Motion detected")
			.isEqualTo(new LogEntry("2024-04-02 16:25:33", 10, "Motion detected").toString());
	}
	
}
