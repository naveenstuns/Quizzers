package naveen.quizzers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class Login extends AppCompatActivity {

    public static final String JSON_URL = "http://eduzone.net84.net/Login.php";
    public ProgressDialog pd = null;
    public EditText email, pass;
    public TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        register = (TextView) findViewById(R.id.textView4);

        FancyButton b = (FancyButton) findViewById(R.id.lbutton);
        b.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (!(email.getText().toString().trim().isEmpty() && pass.getText().toString().trim().isEmpty())) {
                    Bundle b = new Bundle();
                    MyDialogFragment df = new MyDialogFragment();
                    b.putString("title", "Error");
                    b.putString("message", "Fill all the fields");
                    b.putString("context", "login_empty");
                    df.setArguments(b);
                    df.show(getFragmentManager(), "Loginempty");
                }
                pd = ProgressDialog.show(Login.this, "Logging in..", "Please wait....");
                sendRequest();

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void sendRequest() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        JSONObject jObj = null;
                        try {
                            jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {
                                Bundle b = new Bundle();
                                MyDialogFragment df = new MyDialogFragment();
                                b.putString("title", "Successful");
                                b.putString("message", "Login Successsful");
                                b.putString("context", "login");
                                df.setArguments(b);
                                df.show(getFragmentManager(), "RegisterDialog");
                            } else {
                                Bundle b = new Bundle();
                                String errorMsg = jObj.getString("error_msg");
                                MyDialogFragment df = new MyDialogFragment();
                                b.putString("context", "login_error");
                                b.putString("title", "ERROR");
                                b.putString("message", errorMsg);
                                df.setArguments(b);
                                df.show(getFragmentManager(), "RegisterDialog");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString().trim());
                params.put("password", pass.getText().toString().trim());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
