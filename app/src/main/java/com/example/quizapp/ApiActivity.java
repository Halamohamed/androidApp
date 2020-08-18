package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiActivity extends AppCompatActivity implements View.OnClickListener {

    private Button get_btn;
    private Button post_btn;
    private Button put_btn;
    private Button delete_btn;
    private ListView user_list;
    private TextView textResult;
    private ArrayList<UserModal> userModalArrayList;
    ArrayList<String> nameList;
    private final static String SERVER_URL = "https://reqres.in/api/";
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);


    get_btn = findViewById(R.id.get_button);
    post_btn = findViewById(R.id.post_button);
    put_btn = findViewById(R.id.put_button);
    delete_btn = findViewById(R.id.delete_button);
    user_list = findViewById(R.id.user_list);
    textResult = findViewById(R.id.textResult);

    nameList = new ArrayList<>();
    userModalArrayList = new ArrayList<>();

    updateViews();
        get_btn.setOnClickListener(this);
        post_btn.setOnClickListener(this);
        put_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);

        user_list.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(ApiActivity.this, "Email: " + userModalArrayList.get(position).getAvatar(), Toast.LENGTH_SHORT).show();
        });
         registerForContextMenu(user_list);
        //AdapterView.AdapterContextMenuInfo info = item.getMenuInfo();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        AlertDialog.Builder builderDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.user_dialog,null);

        builderDialog.setView(dialogView);
        ImageView user_avatar;
        TextView user_id;
        TextView user_name;
        TextView user_email;

        user_avatar = dialogView.findViewById(R.id.dialog_imageview);
        user_id = dialogView.findViewById(R.id.dialog_txt_id);
        user_name = dialogView.findViewById(R.id.dialog_txt_fullname);
        user_email = dialogView.findViewById(R.id.dialog_txt_email);

        Picasso.get().load(userModalArrayList.get(info.position).getAvatar()).into(user_avatar);
        user_id.setText(String.valueOf(userModalArrayList.get(info.position).getId()));
        user_name.setText(userModalArrayList.get(info.position).getFirstName()+ " " + userModalArrayList.get(info.position).getLastName());
        user_email.setText(userModalArrayList.get(info.position).getEmail());

        AlertDialog updateUserDialog = builderDialog.create();
        updateUserDialog.show();
        updateUserDialog.getWindow().setLayout(800,900);
        return super.onContextItemSelected(item);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.get_button:
                Toast.makeText(ApiActivity.this,"Get" , Toast.LENGTH_SHORT).show();

                JsonObjectRequest myGetRequest = new JsonObjectRequest(Request.Method.GET, SERVER_URL + "users", null, response -> {
                    textResult.setText(response.toString());
                    try {
                        JSONArray dataObject = response.getJSONArray("data");
                        for(int i=0; i < dataObject.length(); i++) {
                            JSONObject userObject = dataObject.getJSONObject(i);

                            UserModal user = new UserModal(userObject.getInt("id"),
                                    userObject.getString("email"),
                                    userObject.getString("first_name"),
                                    userObject.getString("last_name"),
                                    userObject.getString("avatar"));
                            userModalArrayList.add(user);
                            nameList.add(user.getFirstName()+ " " + user.getLastName());
                            //Picasso.get().load(userObject.getString("avatar")).into(image_avatar);
                            updateViews();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    Toast.makeText(ApiActivity.this,"Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                });
                VolleyNetwork.getInstance(this.getApplicationContext()).addToRequestQueue(myGetRequest);
                break;
            case R.id.post_button:
                Toast.makeText(this,"POST" , Toast.LENGTH_SHORT).show();
                JSONObject postData = new JSONObject();
                try {
                    postData.put("name", "Sham");
                    postData.put("course","Android developer");
                    postData.put("id", "123");
                    JsonObjectRequest myPostRequest = new JsonObjectRequest(Request.Method.POST, SERVER_URL + "users", postData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            textResult.setText(response.toString());
                        }
                    }, error -> error.printStackTrace());
                    VolleyNetwork.getInstance(this.getApplicationContext()).addToRequestQueue(myPostRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.put_button:
                Toast.makeText(this, "PUT", Toast.LENGTH_SHORT).show();
                JSONObject putData = new JSONObject();
                try {
                    putData.put("name","Siray");
                    putData.put("course", "Android");
                    JsonObjectRequest myPutRequest = new JsonObjectRequest(Request.Method.PUT, SERVER_URL + "users/2", putData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            textResult.setText(response.toString());
                        }
                    }, error -> error.printStackTrace());
                    VolleyNetwork.getInstance(this.getApplicationContext()).addToRequestQueue(myPutRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.delete_button:
                Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
                JsonObjectRequest myDeleteRequest = new JsonObjectRequest(Request.Method.DELETE,  "https://jsonplaceholder.typicode.com/posts/1", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textResult.setText(response.toString());
                    }
                }, error -> error.printStackTrace());
                VolleyNetwork.getInstance(this.getApplicationContext()).addToRequestQueue(myDeleteRequest);
                break;
        }
    }
    private void updateViews(){
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,nameList);
        user_list.setAdapter(arrayAdapter);
    }

    }


