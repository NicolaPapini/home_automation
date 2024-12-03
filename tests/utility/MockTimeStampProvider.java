package utility;

public class MockTimeStampProvider implements TimeStampProvider {
	
	private String time;
	
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
	public String now() {
		return time;
	}
	
}
