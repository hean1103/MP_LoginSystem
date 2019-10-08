package com.example.mobileprogramming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText password = (EditText) findViewById(R.id.password);
                EditText name = (EditText) findViewById(R.id.name);
                EditText addr = (EditText) findViewById(R.id.addr);
                EditText phone = (EditText) findViewById(R.id.phone);

                Editable pwd = password.getText();
                RadioButton radiobtn = (RadioButton) findViewById(R.id.btn_accept);

                phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

                if (name.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (addr.length() == 0) {
                    Toast.makeText(getApplicationContext(), "주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (phone.length() == 0) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!radiobtn.isChecked()) {
                    Toast.makeText(getApplicationContext(), "개인정사용에 동의해주세요!", Toast.LENGTH_SHORT).show();
                } else if(!check_phone(phone.getText())) {
                    Toast.makeText(getApplicationContext(), "핸드폰 번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else if(!check_password(pwd)){
                    Toast.makeText(getApplicationContext(), "비밀번호는 특수문자와 숫자가 포함되어야 합니다.", Toast.LENGTH_SHORT).show();
                } else if (check_password(pwd) && radiobtn.isChecked() && check_phone(phone.getText())) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}


