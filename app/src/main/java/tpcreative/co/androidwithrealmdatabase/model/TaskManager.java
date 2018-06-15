package tpcreative.co.androidwithrealmdatabase.model;
import android.util.Log;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import tpcreative.co.androidwithrealmdatabase.common.RealmController;

public class TaskManager extends RealmObject {

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @PrimaryKey
    private String key;
    private String task ;
    private String date;
    @Ignore
    static private TaskManager instance;
    @Ignore
    public static final String TAG = TaskManager.class.getSimpleName();

    public TaskManager(String key, String task,String date){
        this.key = key;
        this.task = task;
        this.date = date;
    }

    public TaskManager(){
        this.key = null;
        this.task = null;
        this.date = null;
    }

    public static TaskManager with(){
        if (instance == null){
            synchronized(TaskManager.class){
                if (instance == null){
                    instance = new TaskManager();//instance will be created at request time
                }
            }
        }
        return instance;
    }

    public synchronized void onInsert(TaskManager cTalkManager){
        try {
            if (cTalkManager.key==null){
                return;
            }
            RealmController realmController = RealmController.with();
            TaskManager mTalkManager = (TaskManager) realmController.mInsertObject(cTalkManager);
            Log.d(TAG,new Gson().toJson(mTalkManager));
        }
        catch (Exception e){
            Log.d(TAG,e.getMessage());
        }
    }

    public final synchronized boolean onDelete(String key,String value){
        try{
            RealmController realmController = RealmController.with();
            realmController.mDeleteItem(key,value,TaskManager.class);
            return true;
        }
        catch (Exception e){
            Log.d(TAG,e.getMessage());
        }
        return false;
    }

    public final synchronized TaskManager getContact(String key,int value){
        try{
            RealmController realmController = RealmController.with();
            return (TaskManager) realmController.getSearchObject(key,value,TaskManager.class);
        }
        catch (Exception e){
            Log.d(TAG,e.getMessage());
        }
        return null;
    }


    public final synchronized List<TaskManager> getList(){
        try{
            RealmController realmController = RealmController.with();
            return realmController.getALLObject(TaskManager.class);
        }
        catch (Exception e){
            Log.d(TAG,e.getMessage());
        }
        return null;
    }


    public String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
