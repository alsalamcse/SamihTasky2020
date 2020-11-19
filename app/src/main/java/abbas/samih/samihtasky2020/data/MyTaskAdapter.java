package abbas.samih.samihtasky2020.data;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import abbas.samih.samihtasky2020.R;

/**
 * target: arrange data source using listview
 * א
 * */
//.1
public class MyTaskAdapter extends ArrayAdapter<MyTask>
{

    /**
     * constructor
     * @param context the activity ot (app) that this adapter belong to.
     * @param resource XML design of the "item"
     */
    //2 fix error
    public MyTaskAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    /**\
     * building the single item view
     * @param position index item in the listevew
     * @param convertView item view
     * @param parent listeview
     * @return
     */
    @NonNull
    @Override
    //3. overriding getview
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        //3.1
        View v= LayoutInflater.from(getContext()).inflate(R.layout.item_task_view,parent,false);
        //3.2 find view by id
        TextView tvTitle=v.findViewById(R.id.itmTvTitle);
        TextView tvImportant=v.findViewById(R.id.itmTvImportant);
        TextView tvNecessary=v.findViewById(R.id.itmTvNecessary);
        TextView tvSubject=v.findViewById(R.id.itmTvSubject);

        ImageButton btnDelete=v.findViewById(R.id.itmbtnDelete);
        ImageButton btnCall=v.findViewById(R.id.itmbtnCall);
        ImageButton btnEdit=v.findViewById(R.id.itmbtnEdit);
        // 3.3 get the soutable task object
        MyTask task = getItem(position);

        //3.4 connect the dat to the view (view the data using item views)
        tvTitle.setText(task.getTitle());
        tvSubject.setText(task.getSubject());
        switch (task.getImportant())
        {
            case 5: tvNecessary.setBackgroundColor(Color.RED);break;
            case 4: tvNecessary.setBackgroundColor(Color.YELLOW);break;
            case 3: tvNecessary.setBackgroundColor(Color.CYAN);break;
            case 2: tvNecessary.setBackgroundColor(Color.MAGENTA);break;
            case 1: tvNecessary.setBackgroundColor(Color.rgb(0,200,0));break;
        }
        switch (task.getNecessary())
        {
            case 5: tvImportant.setBackgroundColor(Color.RED);break;
            case 4: tvImportant.setBackgroundColor(Color.YELLOW);break;
            case 3: tvImportant.setBackgroundColor(Color.CYAN);break;
            case 2: tvImportant.setBackgroundColor(Color.MAGENTA);break;
            case 1: tvImportant.setBackgroundColor(Color.rgb(0,200,0));break;
        }
        return v;
    }
}
