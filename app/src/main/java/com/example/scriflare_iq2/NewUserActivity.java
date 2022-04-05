package com.example.scriflare_iq2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity {

    private EditText etName, etEmail, etMobile, etGender;

    public static final String EXTRA_ID = "com.example.scriflare_iq2.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.scriflare_iq2.EXTRA_NAME";
    public static final String EXTRA_EMAIL = "com.example.scriflare_iq2.EXTRA_EMAIL";
    public static final String EXTRA_MOBILE = "com.example.scriflare_iq2.EXTRA_MOBILE";
    public static final String EXTRA_GENDER = "com.example.scriflare_iq2.EXTRA_GENDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        etGender = (EditText) findViewById(R.id.et_gender);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            ((TextView) findViewById(R.id.tv_title)).setText("Edit User");
            etName.setText(intent.getStringExtra(EXTRA_NAME));
            etEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
            etMobile.setText(intent.getStringExtra(EXTRA_MOBILE));
            etGender.setText(intent.getStringExtra(EXTRA_GENDER));
        } else {
            ((TextView) findViewById(R.id.tv_title)).setText("Add User");
        }

        ((Button) findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String mobile = etMobile.getText().toString();
                String gender = etGender.getText().toString();

                if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || gender.isEmpty()) {
                    Toast.makeText(NewUserActivity.this, "Please enter the user Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveUser(name, email, mobile, gender);
            }
        });
    }

    private void saveUser(String name, String email, String mobile, String gender) {
        Intent data = new Intent();

        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_MOBILE, mobile);
        data.putExtra(EXTRA_GENDER, gender);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);

        Toast.makeText(NewUserActivity.this, "User has been saved to Room Database", Toast.LENGTH_SHORT).show();
    }
}