package naveen.quizzers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    public static final String JSON_URL = "http://eduzone.net84.net/Quiz.php";
    public TextView tv1, tv2;
    public RadioButton r1, r2, r3, r4;
    public Button next, prev;
    public int qno = 0;
    public RadioGroup rb;
    public ProgressDialog pd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        r1 = (RadioButton) findViewById(R.id.radioButton);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        r3 = (RadioButton) findViewById(R.id.radioButton3);
        r4 = (RadioButton) findViewById(R.id.radioButton4);
        rb = (RadioGroup) findViewById(R.id.radiogroup);
        next = (Button) findViewById(R.id.button);
        prev = (Button) findViewById(R.id.button2);
        if (qno == 0)
            prev.setVisibility(View.GONE);
        pd = ProgressDialog.show(this, "Fetching Questions", "Please wait ..");
        sendRequest();
        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                prev.setVisibility(View.VISIBLE);
                qno++;
                displayQuestion();
                rb.clearCheck();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                qno--;
                if (qno == 0)
                    prev.setVisibility(View.GONE);
                displayQuestion();
                rb.clearCheck();
            }
        });

    }


    public void displayQuestion() {
        tv1.setText("Ques. no . " + ParseJSON.uid[qno]);
        tv2.setText(ParseJSON.question[qno]);
        r1.setText(ParseJSON.option1[qno]);
        r2.setText(ParseJSON.option2[qno]);
        r3.setText(ParseJSON.option3[qno]);
        r4.setText(ParseJSON.option4[qno]);
    }


    private void sendRequest() {

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json) {
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        displayQuestion();

    }
}