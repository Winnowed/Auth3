package mycompany.com.auth3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){

            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            //Will open login activity here
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter your e-mail address", Toast.LENGTH_SHORT).show();
            //stopping the fuction execution further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        //if validations are ok
        //we will first show a progresbar.
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            //user is successfully registered and loged in
                            //We will start the profile activity here
                            //right now lets display a toast only
                            //Toast.makeText(MainActivity.this, "Registered Successfully",Toast.LENGTH_SHORT).show();
                            //progressDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                        }
                    }});
    }
}
