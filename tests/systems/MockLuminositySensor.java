package systems;

public class MockLuminositySensor extends LuminositySensor {

	@Override
	public void newLuminosityDetected (double detectedLuminosity) {
		SensorEvent event = new LuminosityChangeEvent(detectedLuminosity,this);
		notifyObservers(event);
	}

}
