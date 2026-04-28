package com.example.studentlab;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText txtFullname, txtPassword, txtEmail, txtAddress;
    private Spinner spinnerJurusan;
    private RadioGroup rgSemester;
    private CheckBox cbAgreement;
    private Button btnRegister;
    private LinearLayout layoutResult;
    private TextView resNama, resEmail, resAddress, resJurusan, resSemester;
    private TextView tvErrorJurusan, tvErrorSemester, tvErrorAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize views
        txtFullname = findViewById(R.id.txt_fullname);
        txtPassword = findViewById(R.id.txt_password);
        txtEmail = findViewById(R.id.txt_email);
        txtAddress = findViewById(R.id.txt_address);
        spinnerJurusan = findViewById(R.id.spinner_jurusan);
        rgSemester = findViewById(R.id.rg_semester);
        cbAgreement = findViewById(R.id.cb_agreement);
        btnRegister = findViewById(R.id.btn_register);
        
        // Initialize result views
        layoutResult = findViewById(R.id.layout_result);
        resNama = findViewById(R.id.res_nama);
        resEmail = findViewById(R.id.res_email);
        resAddress = findViewById(R.id.res_address);
        resJurusan = findViewById(R.id.res_jurusan);
        resSemester = findViewById(R.id.res_semester);
        
        // Initialize error views
        tvErrorJurusan = findViewById(R.id.tv_error_jurusan);
        tvErrorSemester = findViewById(R.id.tv_error_semester);
        tvErrorAgreement = findViewById(R.id.tv_error_agreement);

        // Setup Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jurusan_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJurusan.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset error visibility & result layout
                tvErrorJurusan.setVisibility(View.GONE);
                tvErrorSemester.setVisibility(View.GONE);
                tvErrorAgreement.setVisibility(View.GONE);
                layoutResult.setVisibility(View.GONE);

                String fullname = txtFullname.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String address = txtAddress.getText().toString().trim();
                String jurusan = spinnerJurusan.getSelectedItem().toString();
                
                int selectedSemesterId = rgSemester.getCheckedRadioButtonId();
                boolean isAgreed = cbAgreement.isChecked();

                boolean isValid = true;

                // Validation for EditTexts
                if (fullname.isEmpty()) {
                    txtFullname.setError("Fullname is required");
                    isValid = false;
                }
                if (password.isEmpty()) {
                    txtPassword.setError("Password is required");
                    isValid = false;
                }
                if (email.isEmpty()) {
                    txtEmail.setError("Email is required");
                    isValid = false;
                }
                if (address.isEmpty()) {
                    txtAddress.setError("Address is required");
                    isValid = false;
                }

                // Validation for Spinner
                if (spinnerJurusan.getSelectedItemPosition() == 0) {
                    tvErrorJurusan.setVisibility(View.VISIBLE);
                    isValid = false;
                }

                // Validation for RadioGroup
                if (selectedSemesterId == -1) {
                    tvErrorSemester.setVisibility(View.VISIBLE);
                    isValid = false;
                }

                // Validation for CheckBox
                if (!isAgreed) {
                    tvErrorAgreement.setVisibility(View.VISIBLE);
                    isValid = false;
                }

                if (isValid) {
                    // Get selected RadioButton text
                    RadioButton rbSelected = findViewById(selectedSemesterId);
                    String semester = rbSelected.getText().toString();

                    // Update Result Views
                    resNama.setText(": " + fullname);
                    resEmail.setText(": " + email);
                    resAddress.setText(": " + address);
                    resJurusan.setText(": " + jurusan);
                    resSemester.setText(": " + semester);
                    
                    // Show result layout
                    layoutResult.setVisibility(View.VISIBLE);
                    
                    Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please check your inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}