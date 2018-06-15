package tpcreative.co.androidwithrealmdatabase.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import tpcreative.co.androidwithrealmdatabase.R;
import tpcreative.co.androidwithrealmdatabase.common.adapter.BaseAdapter;
import tpcreative.co.androidwithrealmdatabase.common.adapter.BaseHolder;
import tpcreative.co.androidwithrealmdatabase.model.TaskManager;

public class MainAdapter extends BaseAdapter<TaskManager, BaseHolder> {

    private Activity myActivity;
    private ItemSelectedListener itemSelectedListener;
    private String TAG = MainAdapter.class.getSimpleName();

    public MainAdapter(LayoutInflater inflater, Activity activity, ItemSelectedListener itemSelectedListener) {
        super(inflater);
        this.myActivity = activity;
        this.itemSelectedListener = itemSelectedListener;
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.main_item, parent, false));
    }

    public class ItemHolder extends BaseHolder<TaskManager> {

        public ItemHolder(View itemView) {
            super(itemView);
        }

        private TaskManager data;
        @BindView(R.id.tvTask)
        TextView tvTask;
        @BindView(R.id.tvDate)
        TextView tvDate;
        int mPosition ;

        @Override
        public void bind(TaskManager data, int position) {
            super.bind(data, position);
            tvTask.setText(data.getTask());
            tvDate.setText(""+data.getDate());
            mPosition = position;
        }

        @OnClick(R.id.constraintLayout)
        public void onClick(){
            if (itemSelectedListener!=null){
                itemSelectedListener.onClickItem(mPosition);
            }
        }

    }

    public  interface ItemSelectedListener{
        void onClickItem(int position);
    }

}