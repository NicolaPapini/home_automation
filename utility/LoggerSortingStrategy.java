package utility;

import java.util.Comparator;

public interface LoggerSortingStrategy {
	
	Comparator<LogEntry> getSortingStrategy();
	
}
