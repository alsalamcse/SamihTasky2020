package abbas.samih.samihtasky2020.MyUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import abbas.samih.samihtasky2020.MyUtils.MyValidations;
import abbas.samih.samihtasky2020.R;

public class SignUpActivity extends AppCompatActivity {
    //1. XML Design
    //2.

    private EditText etFirstName,etLastName,etPhone,etEmail2,etPassWord2,etPassword1;
    /**
     * כפור שמירת ניתונים
     */
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       //3.
        etFirstName=findViewById(R.id .etFirstName) ;
        etLastName=findViewById(R.id .etLastName) ;
        etPhone= findViewById(R.id .etPhone) ;
        etEmail2= findViewById(R.id .etEmail2) ;
        etPassWord2=findViewById(R.id .etPassWord2) ;
        etPassword1=findViewById(R.id.etPassWord1);
        btnSave=findViewById(R.id .btnSave) ;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });


    }


   /*

    */
    // java doc: java documentation
    /**
     * המתודה בודקת תקינות טופס ההרשמה ואם תקין מבצעת הרשמה
     */
    private void validateForm()
    {
        String passw2=etPassWord2.getText().toString();
        String passw1=etPassword1.getText().toString();
        String fname=etFirstName.getText().toString();
        String lname=etLastName.getText().toString();
        String phone=etPhone.getText().toString();
        String email=etEmail2.getText().toString();

        boolean isOK=true;

        if(fname.length()<2)
        {
            isOK=false;
            etFirstName.setError("at least to letters");
        }
        if(lname.length()<2)
        {
            isOK=false;
            etLastName.setError("at least to letters");
        }
        if(email.length()<5 || email.indexOf('@')==0 || email.indexOf('@')>=email.length()-2 ||
                email.indexOf('.')==0 || email.indexOf('.')>=email.length()-1 || email.lastIndexOf('.')<email.indexOf('@'))
        {
            isOK=false;
            etEmail2.setError("Wrong Eamil syntax");
        }
        if(passw1.equals(passw2)==false)
        {
            isOK=false;
            etPassWord2.setError("Passwords must be the same!");

        }
        else {
            MyValidations myValidations = new MyValidations();
            if (myValidations.validatePasword(passw1) == false) {
                isOK = false;
                etPassword1.setError("Invalid Password");
            }
        }



        if(isOK)//==> isOK==true
        {
            //todo: create account and return to sign in screen/close this screen
            createNewAccount(email,passw1,fname,lname,phone);
        }

    }

    /**
     * רישום משתמש חדש לפי מיל ו סיסימא
     * @param email אימיל
     * @param passw1  סיסמא
     * @param fname שם פרטי
     * @param lname שם משפחה
     * @param phone טלפון
     */
    private void createNewAccount(String email, String passw1, String fname, String lname, String phone)
    {
        //1
        FirebaseAuth auth=FirebaseAuth.getInstance();//אחראית על רישום וכניסת משתמשים
        //מאזין לאירוע הרשמה בפיירבייס2.
        OnCompleteListener<AuthResult> listener=new OnCompleteListener<AuthResult>() {
            @Override//Response
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())// ההרשמה הצליחה כמו שנדרש
                {
                    Toast.makeText(SignUpActivity.this, "Successfully Signing up ", Toast.LENGTH_SHORT).show();
                    //next screen or close this screen
                    finish();//close this screen
                    //next screen
//                    Intent i=new Intent(getBaseContext(),MainActivity.class); startActivity(i);
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Signing up, Failed: " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    etEmail2.setError("Signing up, Failed: " +task.getException().getMessage());
                }
            }
        };
        //3.
        auth.createUserWithEmailAndPassword(email,passw1).addOnCompleteListener(listener);
    }
}