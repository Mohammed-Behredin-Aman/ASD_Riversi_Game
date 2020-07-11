package main.java.edu.miu.cs.cs525.reversi.network;

import com.google.gson.Gson;
import main.java.edu.miu.cs.cs525.reversi.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AdapteeJson {
    private  final Gson gson = new Gson();
    Utils util = new Utils();
    public boolean isJson(String jsonInString){

        try {
            new JSONObject(jsonInString);
        } catch (JSONException ex) {

            try {
                new JSONArray(jsonInString);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;


    }
    public String JsonString(String str){
        JsonData data = new Gson().fromJson(str, JsonData.class);

        String result = util.intToString(data.getX())+(data.getY()+1);
        return  result;

    }




}