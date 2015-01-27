package csu.example.clapper;

public interface AmplitudeClipListener {
	/**
	 * return true if recording should stop
	 */
	public boolean heard(int maxAmplitude);
}
