package naveen.quizzers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Naveen on 10/5/2015.
 */

public class ParseJSON {
    public static final String JSON_ARRAY = "result";
    public static String[] uid;
    public static String[] question;
    public static String[] option1;
    public static String[] option2;
    public static String[] option3;
    public static String[] option4;
    public static String[] correct_answer;
    private JSONArray users = null;

    private String json;

    public ParseJSON(String json) {
        this.json = json;
    }

    protected void parseJSON() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            uid = new String[users.length()];
            question = new String[users.length()];
            option1 = new String[users.length()];
            option2 = new String[users.length()];
            option3 = new String[users.length()];
            option4 = new String[users.length()];
            correct_answer = new String[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                uid[i] = jo.getString("uid");
                question[i] = jo.getString("Question");
                option1[i] = jo.getString("Option1");
                option2[i] = jo.getString("Option2");
                option3[i] = jo.getString("Option3");
                option4[i] = jo.getString("Option4");
                correct_answer[i] = jo.getString("Correct_answer");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}