package mycompany.com.auth3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextAddress;
    private Button buttonSave;
    Button buttonAddLocation = null;
    LinearLayout myLayout = null;
    private LinearLayout scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));


        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextAddress =(EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonAddLocation = (Button) findViewById(R.id.buttonAddLocation);
        //myLayout = (LinearLayout) findViewById(R.id.myLayout);
        scrollView = (LinearLayout) findViewById(R.id.scrollview);

        buttonAddLocation.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View v) {
                Button secondBtn = new Button(ProfileActivity.this);

                scrollView = (LinearLayout) findViewById(R.id.scrollview);

                LinearLayout.LayoutParams myFunnyParams = new LinearLayout.LayoutParams(

                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );


                secondBtn.setText(+(++i)+  ".Bina");
                secondBtn.setId(i);

                scrollView.addView(secondBtn,myFunnyParams);
            }
        });

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail=(TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("welcome    "+user.getEmail());
        buttonLogout=(Button)findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

    }

    private void saveUserInformation()
    {
        String name = editTextName.getText().toString().trim();
        String add =editTextAddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, add);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information Saved", Toast.LENGTH_LONG).show();


    }
    @Override
    public void onClick(View view) {

        if (view==buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == buttonSave){
            saveUserInformation();
        }

    }
}
