package utility;

public abstract class LightFixture {
	
	private boolean isOn;
	private double brightness;
	
	public LightFixture() {
		isOn = false;
		brightness = 0;
	}
	
	public final double getBrightness() {
		return brightness;
	}
	
	public final boolean isOn() {
		return isOn;
	}

	public final void turnOn() {
		isOn = true;
	}
	
	public final void turnOff() {
		isOn = false;
	}
	
	public void setBrightness(double brightness) {
		this.brightness = brightness;
	}
	
}