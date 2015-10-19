package naveen.quizzers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.Collections;

import mehdi.sakout.fancybuttons.FancyButton;

public class Questions extends AppCompatActivity {
    //Declaration
    public static final String JSON_URL = "http://eduzone.net84.net/Quiz.php";
    public static int qno = 0, score = 0;
    public TextView tv1, tv2;
    public RadioButton r1, r2, r3, r4;
    public RadioGroup rb;
    public ProgressDialog pd = null;
    public FancyButton next, prev;
    Integer[] ques = new Integer[10];
    Integer ans[] = new Integer[20];
    // End of declaration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);

        for (int i = 0; i < ques.length; i++) {
            ques[i] = i;
        }
        Collections.shuffle(Arrays.asList(ques));

        next = (FancyButton) findViewById(R.id.next);
        prev = (FancyButton) findViewById(R.id.prev);
        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (rb.getCheckedRadioButtonId() != -1) {
                    setAnswer();
                    checkAnswers();
                }
                prev.setVisibility(View.VISIBLE);
                qno++;

                if (qno == 10) {
                    MyDialogFragment df = new MyDialogFragment();
                    df.show(getFragmentManager(), "MyDialog");
                } else {
                    if (qno == 9) {
                        next.setText("Submit");
                    }
                    displayQuestion();
                    getAnswer();
                }


            }
        });
        prev.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (rb.getCheckedRadioButtonId() != -1) {
                    setAnswer();
                }
                qno--;
                getAnswer();
                if (qno == 0)
                    prev.setVisibility(View.GONE);
                displayQuestion();

            }
        });
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        r1 = (RadioButton) findViewById(R.id.radioButton);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        r3 = (RadioButton) findViewById(R.id.radioButton3);
        r4 = (RadioButton) findViewById(R.id.radioButton4);
        rb = (RadioGroup) findViewById(R.id.radiogroup);


        pd = ProgressDialog.show(this, "Fetching Questions", "Please wait ..");
        sendRequest();

    }

    // to display the selected question
    public void displayQuestion() {
        tv1.setText("Ques. no . " + ParseJSON.uid[qno]);
        tv2.setText(ParseJSON.question[ques[qno]]);
        r1.setText(ParseJSON.option1[ques[qno]]);
        r2.setText(ParseJSON.option2[ques[qno]]);
        r3.setText(ParseJSON.option3[ques[qno]]);
        r4.setText(ParseJSON.option4[ques[qno]]);
    }

    // to send request to server to fetch questions
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
                        Toast.makeText(Questions.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // to parse JSON Array from server
    private void showJSON(String json) {
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        displayQuestion();

    }

    // to set the selected answer
    public void setAnswer() {
        int radioButtonID = rb.getCheckedRadioButtonId();
        View radioButton = rb.findViewById(radioButtonID);
        ans[ques[qno]] = rb.indexOfChild(radioButton);
        rb.clearCheck();
    }

    // to check the previously selected answer
    public void getAnswer() {
        if (ans[ques[qno]] != null) {
            RadioButton r = (RadioButton) rb.getChildAt(ans[ques[qno]]);
            r.setChecked(true);
        }
    }

    //  to evaluate the answers
    public void checkAnswers() {
        RadioButton r = (RadioButton) rb.getChildAt(ans[ques[qno]]);
        if (r.getText().equals(ParseJSON.correct_answer[ques[qno]])) {
            score++;
        }
    }
}


