package abbas.samih.samihtasky2020.MyUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import abbas.samih.samihtasky2020.MyUtils.MyValidations;
import abbas.samih.samihtasky2020.R;

//1 xml
public class SignInActivity extends AppCompatActivity {
    //2.
    private EditText etEmail,etPassWord;
    private Button btnLogIN,btnSignUp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //7. check if I signed in before
        FirebaseAuth auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)// user signed in before
        {
            Intent i=new Intent(getBaseContext(),MainActivity.class);
            finish();
            startActivity(i);
        }
//3.
        etEmail=findViewById(R.id .etEmail) ;
        etPassWord=findViewById(R.id .etPassWord) ;
        btnLogIN=findViewById(R.id .btnLogIn) ;
        btnSignUp= findViewById(R.id .btnSignUp) ;
        //4.
        btnLogIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //5
                validateForm();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });
    }
//5
    private void validateForm()
    {
        String email=etEmail.getText().toString();
        String passw=etPassWord.getText().toString();
        boolean isOK=true;
        if(email.length()<5 || email.indexOf('@')==0 || email.indexOf('@')>=email.length()-2 ||
                email.indexOf('.')==0 || email.indexOf('.')>=email.length()-1 || email.lastIndexOf('.')<email.indexOf('@'))
        {
            isOK=false;
            etEmail.setError("Wrong Eamil syntax");
        }
        MyValidations myValidations = new MyValidations();
        if (myValidations.validatePasword(passw) == false) {
            isOK = false;
            etPassWord.setError("Invalid Password");
        }

        if(isOK)
        {
            signIn(email,passw);
        }
    }
//6
    private void signIn(String email, String passw)
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent i=new Intent(SignInActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(SignInActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    etEmail.setError(task.getException().getMessage());
                }
            }
        });
    }


}
