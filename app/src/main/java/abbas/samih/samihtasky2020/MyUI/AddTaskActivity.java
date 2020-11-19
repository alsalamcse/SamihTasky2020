package abbas.samih.samihtasky2020.MyUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import abbas.samih.samihtasky2020.R;
import abbas.samih.samihtasky2020.data.MyTask;

//1. design  addTask XML
public class AddTaskActivity extends AppCompatActivity {
    //2.
    private EditText etTitle,etSubject;
    private SeekBar skbrImportant,skbrNeccesary;
    private Button btnSave;
    private ImageButton imgBtnl;
    private Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        //3.
        //find view by id
        etTitle=findViewById(R.id.etTitle);
        etSubject=findViewById(R.id.etSubject);
//        etDueDate=findViewById(R.id.etDueDate);
        skbrImportant=findViewById(R.id.skbrImportant);
        skbrNeccesary=findViewById(R.id.skbrNeccesary);
//        skbrNecessary=findViewById(R.id.skbrNeccesary);
        btnSave=findViewById(R.id.btnSaveTask);
        imgBtnl=findViewById(R.id.imgBtn);
        btnUpload=findViewById(R.id.btnUpload);

        //4. listeners
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });
    }

    //5.
    public void validateForm()
    {
        String title=etTitle.getText().toString();
        String subject=etSubject.getText().toString();
        int imp=skbrImportant.getProgress();
        int nec=skbrNeccesary.getProgress();
        boolean isok=true;
        if(title.length()==0)
        {
            isok=false;
            etTitle.setError("at least one char");
        }
        if(isok)
        {
            //6. save on firebase database
            //6.1: build your data Object:
            MyTask myTask=new MyTask();
            myTask.setTitle(title);
            myTask.setSubject(subject);
            myTask.setNecessary(nec);
            myTask.setImportant(imp);
            //6.2
            saveTask(myTask);
        }
    }
    //6.2 request to save my task (firebase datatbase)
    private void saveTask(MyTask myTask) {
        //1.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //2.
        DatabaseReference reference = database.getReference();
        //3. user id
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        //4. My Object Key
        String key = reference.child("AllTasks").push().getKey();
        //5. update ypur Object
        myTask.setOwner(uid);
        myTask.setKey(key);
        //6. actual stroring
        reference.child("AllTasks").child(uid).child(key).setValue(myTask).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AddTaskActivity.this, "add successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(AddTaskActivity.this, "add failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });
    }
}