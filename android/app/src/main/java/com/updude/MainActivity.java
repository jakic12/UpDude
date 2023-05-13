import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Pedometer pedometer;
    private TextView stepCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCountTextView = findViewById(R.id.step_count_text_view);

        pedometer = new Pedometer(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pedometer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pedometer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStepCount();
    }

    private void updateStepCount() {
        int stepCount = pedometer.getStepCount();
        String stepCountString = getString(R.string.step_count, stepCount);
        stepCountTextView.setText(stepCountString);
    }
}