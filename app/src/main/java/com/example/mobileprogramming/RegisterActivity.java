package com.example.mobileprogramming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    //비밀번호 유효성 검사(특수문자 , 숫자, 영어 포함 8~16자리)
    boolean check_password(Editable password) {
        String val_symbol = "^(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,16}$";

        Pattern pattern = Pattern.compile(val_symbol);
        Matcher matcher = pattern.matcher(password);

        if(matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
    //핸드폰 번호 유효성 검사
    boolean check_phone(Editable phone) {
        String val_number = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";

        Pattern pattern = Pattern.compile(val_number);
        Matcher matcher = pattern.matcher(phone);

        if(matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public class UserInfo{

        private String userID;
        private String password;
        private String name;
        private String phone;
        private String addr;

        public UserInfo(String userID, String password, String name, String phone, String addr){
            this.userID = userID;
            this.password = password;
            this.name = name;
            this.phone = phone;
            this.addr = addr;
        }

        public String info() {
            return userID + " " + password + " " + name + " " + phone + " " + addr + "\n";
        }
    }

    String tmp_id;
    String tmp_pwd;
    String tmp_name;
    String tmp_phone;
    String tmp_addr;

    EditText id;
    EditText password;
    EditText phone;
    boolean idCheck = true;
    boolean id_avail = false;
    EditText addr;
    EditText name;

    public ArrayList<UserInfo> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        addr = (EditText) findViewById(R.id.addr);
        phone = (EditText) findViewById(R.id.phone);
        id  = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);


        Button register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp_id = id.getText().toString();
                tmp_pwd = password.getText().toString();
                tmp_name = name.getText().toString();
                tmp_phone = phone.getText().toString();
                tmp_addr = addr.getText().toString();

                RadioButton radiobtn = (RadioButton) findViewById(R.id.btn_accept);
                RadioButton radiobtn_no = (RadioButton) findViewById(R.id.btn_decline);


                if (name.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (addr.length() == 0) {
                    Toast.makeText(getApplicationContext(), "주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (phone.length() == 0) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!radiobtn.isChecked() || radiobtn_no.isChecked()) {
                    Toast.makeText(getApplicationContext(), "개인정보사용에 동의해주세요!", Toast.LENGTH_SHORT).show();
                } else if(!check_phone(phone.getText())) {
                    Toast.makeText(getApplicationContext(), "핸드폰 번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else if(!check_password(password.getText())){
                    Toast.makeText(getApplicationContext(), "비밀번호는 특수문자와 숫자가 포함되어야 합니다.", Toast.LENGTH_SHORT).show();
                } else if(!idCheck) {
                    Toast.makeText(getApplicationContext(), "아이디 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show();
                } else if (check_password(password.getText()) && radiobtn.isChecked() && check_phone(phone.getText())) {
                    //확인이 끝난 후 유저 정보 저장
                    UserInfo userinfo = new UserInfo(tmp_id,tmp_pwd,tmp_name,tmp_phone,tmp_addr);
                    userList.add(userinfo);
                    try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "userInfo.txt", true));
                        bw.write(userinfo.info());
                        bw.close();

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button check = (Button)findViewById(R.id.btn_check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp_id = id.getText().toString();
                tmp_pwd = password.getText().toString();
                if(tmp_id.equals("") == true) {
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
                //아이디 중복검사
                try{
                    BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+ "userInfo.txt"));

                    String line = null;
                    while ((line = br.readLine()) != null) {
                        String lines[] = line.split(" ");
                        UserInfo user = new UserInfo(lines[0],lines[1], lines[2], lines[3], lines[4]);
                        userList.add(user);
                    }
                    br.close();
                    for (UserInfo user : userList) {
                        if (tmp_id.equals(user.userID)) {
                            id_avail = true;
                            idCheck = false;
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(id_avail){
                    Toast.makeText(getApplicationContext(),"사용할 수 없는 아이디입니다.", Toast.LENGTH_SHORT).show();
                    id_avail = false;
                } else {
                    Toast.makeText(getApplicationContext(),"사용할 수 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


