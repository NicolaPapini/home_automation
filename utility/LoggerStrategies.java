package utility;

import java.util.Comparator;

public class LoggerStrategies {
	
	private LoggerStrategies() {}
	
	public static LoggerSortingStrategy sortByTime() {
		return () -> Comparator.comparing(LogEntry::getTimeStamp);
	}
	
	public static LoggerSortingStrategy sortByDescription() {
		return () -> Comparator.comparing(LogEntry::getDescription);
	}

}
