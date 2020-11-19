package abbas.samih.samihtasky2020.MyUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import abbas.samih.samihtasky2020.R;
import abbas.samih.samihtasky2020.data.MyTaskAdapter;

public class MainActivity extends AppCompatActivity {
    private ImageButton ibtnAdd;
    //a.1 after building the array adapter
    ListView lstTasks;
    //a.2 adapter
    MyTaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibtnAdd=findViewById(R.id.ibtnAdd);
        ibtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AddTaskActivity.class);
                startActivity(i);
            }
        });

        //a.3
        lstTasks=findViewById(R.id.lstvTasks);
        //a.4
        taskAdapter=new MyTaskAdapter(getBaseContext(),R.layout.item_task_view);
        //a.5 connect listview to the adapter
        lstTasks.setAdapter(taskAdapter);
    }
    //a.6
    public void downloadFromFireBase()
    {
        //where we saved before we need to connect to download
        //1.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //2.
        DatabaseReference reference = database.getReference();
        //3. user id
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        //4. My Object Key
        reference.child("AllTasks").child(uid);
    }
}