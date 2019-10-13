package com.example.mobileprogramming;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public class UserInfo{

        private String userID;
        private String password;

        public UserInfo(String userID, String password){
            this.userID = userID;
            this.password = password;
        }
    }

    boolean login_check = false;
    ArrayList<UserInfo> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        Button btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id = (EditText) findViewById(R.id.id);
                EditText password = (EditText) findViewById(R.id.password);
                String tmp_id = id.getText().toString();
                String tmp_pwd = password.getText().toString();

                try{
                    BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+ "userInfo.txt"));

                    String line = null;
                    while ((line = br.readLine()) != null) {
                        String lines[] = line.split(" ");
                        UserInfo user = new UserInfo(lines[0],lines[1]);
                        userList.add(user);
                    }
                    br.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(tmp_id.equals("") == true) {
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요!" ,Toast.LENGTH_SHORT).show();
                } else {
                    for (UserInfo user : userList) {
                        if (tmp_id.equals(user.userID)) {
                            if(tmp_pwd.equals(user.password)) {
                                login_check = true;
                            }
                        }
                    }
                    //로그인에 실패 시
                    if(login_check == false) {
                        Toast.makeText(getApplicationContext(), "비밀번호와 아이디를 확인해주세요!", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });
    }


}

