package tpcreative.co.androidwithrealmdatabase.ui;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tpcreative.co.androidwithrealmdatabase.R;
import tpcreative.co.androidwithrealmdatabase.model.TaskManager;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemSelectedListener, TextView.OnEditorActionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edtInput)
    EditText edtInput;
    LinearLayoutManager llm;
    private MainAdapter adapter;
    private List<TaskManager> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        setTitle("Realm Database");
        setupRecyclerViewItem();
        edtInput.setOnEditorActionListener(this);
        onUpdateUI();

    }


    public void setupRecyclerViewItem() {
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new MainAdapter(getLayoutInflater(), this, this);
        recyclerView.setAdapter(adapter);
    }


    public void onUpdateUI(){
        List<TaskManager> mResponse = TaskManager.with().getList();
        if (mResponse != null) {
            mList.clear();
            mList.addAll(mResponse);
            adapter.setDataSource(mList);
            edtInput.setText("");
        }
    }

    @Override
    public void onClickItem(final int position) {
        new MaterialDialog.Builder(this)
                .title(R.string.lb_Realm)
                .positiveText(R.string.lbUpdate)
                .negativeText(R.string.lbDelete)
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        Log.d(TAG,input.toString());
                    }}).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                TaskManager.with().onDelete("key",mList.get(position).getKey());
                onUpdateUI();
            }
        }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                TaskManager taskManager = mList.get(position);
                String value = dialog.getInputEditText().getText().toString().trim();
                if (value!=null && !value.equals("")){
                    taskManager.setTask(dialog.getInputEditText().getText().toString());
                    TaskManager.with().onInsert(taskManager);
                    onUpdateUI();
                }
            }
        }).show().getInputEditText().setText(mList.get(position).getTask());
    }


    @OnClick(R.id.btnSend)
    public void onSend() {
        String task = edtInput.getText().toString().trim();
        if (task != null && !task.equals("")) {
            Log.d(TAG, "Action send");
            TaskManager taskManager = new TaskManager(System.currentTimeMillis() + "", task, TaskManager.with().getCurrentTime());
            TaskManager.with().onInsert(taskManager);
            onUpdateUI();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String task = edtInput.getText().toString().trim();
            if (task != null && !task.equals("")) {
                Log.d(TAG, "Action send");
                TaskManager taskManager = new TaskManager(System.currentTimeMillis() + "", task, TaskManager.with().getCurrentTime());
                TaskManager.with().onInsert(taskManager);
                onUpdateUI();
            }
            handled = true;
        }
        return handled;
    }

}
