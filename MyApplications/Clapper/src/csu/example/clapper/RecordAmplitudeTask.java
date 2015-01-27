package csu.example.clapper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class RecordAmplitudeTask extends
		AsyncTask<AmplitudeClipListener, Void, Boolean> {
	private static final String TAG = "RecordAmplitudeTask";

	private TextView status;
	private TextView log;
	private Context context;
	private String taskName;

	private static final String TEMP_AUDIO_DIR_NAME = "temp_audio";

	/**
	 * time between amplitude checks
	 */
	private static final int CLIP_TIME = 1000;

	public RecordAmplitudeTask(Context context, TextView status, TextView log,
			String taskName) {
		this.context = context;
		this.status = status;
		this.log = log;
		this.taskName = taskName;
	}

	@Override
	protected void onPreExecute() {
		// tell UI recording is starting
		status.setText(context.getResources().getString(
				R.string.audio_status_recording)
				+ " for " + taskName);
		AudioTaskUtil.appendToStartOfLog(log, "started " + taskName);
		super.onPreExecute();
	}

	/**
	 * note: only uses the first listener passed in
	 */
	@Override
	protected Boolean doInBackground(AmplitudeClipListener... listeners) {
		if (listeners.length == 0) {
			return false;
		}

		Log.d(TAG, "recording amplitude");
		// construct recorder, using only the first listener passed in
		AmplitudeClipListener listener = listeners[0];

        String appStorageLocation =
                context.getExternalFilesDir(TEMP_AUDIO_DIR_NAME).getAbsolutePath()
                        + File.separator + "audio.3gp";
        MaxAmplitudeRecorder recorder = new MaxAmplitudeRecorder(CLIP_TIME,
				appStorageLocation, listener, this);

		// set to true if the recorder successfully detected something
		// false if it was canceled or otherwise stopped
		boolean heard = false;
		try {
			// start recording
			heard = recorder.startRecording();
		} catch (IOException io) {
			Log.e(TAG, "failed to record", io);
			heard = false;
		} catch (IllegalStateException se) {
			Log.e(TAG, "failed to record, recorder not setup properly", se);
			heard = false;
		} catch (RuntimeException se) {
			Log.e(TAG, "failed to record, recorder already being used", se);
			heard = false;
		}

		return heard;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// update UI
		if (result) {
			AudioTaskUtil.appendToStartOfLog(log, "heard clap at "
					+ AudioTaskUtil.getNow());
		} else {
			AudioTaskUtil.appendToStartOfLog(log, "heard no claps");
		}
		setDoneMessage();
		super.onPostExecute(result);
	}

	@Override
	protected void onCancelled() {
		AudioTaskUtil.appendToStartOfLog(log, "cancelled " + taskName);
		setDoneMessage();
		super.onCancelled();
	}

	private void setDoneMessage() {
		status.setText(context.getResources().getString(
				R.string.audio_status_stopped));
	}
}
