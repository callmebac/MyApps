package csu.example.clapper;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClapperActivity extends Activity {

	private static final String TAG = "ClapperActivity";

	private TextView log;
	private TextView status;
	private Button startBtn;
	private Button stopBtn;
	private RecordAmplitudeTask recordAmplitudeTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clapper);

		status = (TextView) findViewById(R.id.textStatus);
		log = (TextView) findViewById(R.id.textLog);
		startBtn = (Button) findViewById(R.id.startBtn);
		stopBtn = (Button) findViewById(R.id.stopBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startAmplitudeTask();
			}
		});

		stopBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				stopAll();
			}
		});

	}

	private void startAmplitudeTask() {
		stopAll();
		recordAmplitudeTask = new RecordAmplitudeTask(ClapperActivity.this,
				status, log, "Clapper");
		SingleClapDetector detector = new SingleClapDetector(SingleClapDetector.AMPLITUDE_DIFF_HIGH);
		recordAmplitudeTask.execute(detector);
	}

	private void stopAll() {
		Log.d(TAG, "stop record amplitude");
		shutDownTaskIfNecessary(recordAmplitudeTask);
	}

	private void shutDownTaskIfNecessary(final AsyncTask task) {
		if ((task != null) && (!task.isCancelled())) {
			if ((task.getStatus().equals(AsyncTask.Status.RUNNING))
					|| (task.getStatus().equals(AsyncTask.Status.PENDING))) {
				Log.d(TAG, "CANCEL " + task.getClass().getSimpleName());
				task.cancel(true);
			} else {
				Log.d(TAG, "task not running");
			}
		}
	}

	@Override
	protected void onPause() {
		stopAll();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clapper, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
