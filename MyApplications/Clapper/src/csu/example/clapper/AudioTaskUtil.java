package csu.example.clapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.widget.TextView;

/**
 * A small helper class to help the various classes in this package update the
 * UI
 * 
 * @author Greg Milette &#60;<a
 *         href="mailto:gregorym@gmail.com">gregorym@gmail.com</a>&#62;
 */
public class AudioTaskUtil {
	private static DateFormat WHEN_FORMATTER = new SimpleDateFormat("hh:mm:ss");

	public static String getNow() {
		Calendar now = Calendar.getInstance();
		String when = WHEN_FORMATTER.format(now.getTime());
		return when;
	}

	public static void appendToStartOfLog(TextView log, String appendThis) {
		String currentLog = log.getText().toString();
		currentLog = appendThis + "\n" + currentLog;
		log.setText(currentLog);
	}

}
