package Screens;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.maaz.olo.R;
import utils.ExceptionHanlder;


/**
 * Created by Sabih Ahmed on 5/21/2015.
 */
public class ExceptionView extends Activity {
    private TextView exceptionTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHanlder(this));
        setContentView(R.layout.screen_exception_view);

        String errorReport = getIntent().getStringExtra("errorReport");
        exceptionTextView = (TextView) findViewById(R.id.screen_exception_textView);
        exceptionTextView.setText(errorReport);

    }
}