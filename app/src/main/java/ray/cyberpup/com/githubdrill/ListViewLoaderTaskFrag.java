package ray.cyberpup.com.githubdrill;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created on 6/16/15
 *
 * @author Raymond Tong
 */
public class ListViewLoaderTaskFrag extends Fragment {
    private static final String LOG_TAG = DownloadTaskFrag.class.getSimpleName();

    TaskListener mListener;
    ListViewLoaderTask mTask;

    interface TaskListener{
        public void onPreExecute();
        public void onProgressUpdate(Integer... progress);
        public void onPostExecute(Integer r);
        public void onCancelled();
    }

    public static DownloadTaskFrag getInstance(String query){

        DownloadTaskFrag taskFragment = new DownloadTaskFrag();
        Bundle args = new Bundle();
        args.putString("query",query);

        taskFragment.setArguments(args);
        return taskFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        mTask = new ListViewLoaderTask();

        mTask.execute();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mListener = (TaskListener)activity;
        } catch(ClassCastException e){
            throw new ClassCastException(activity.toString() +
                    " must implement TaskListener");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    void cancel() {
        mTask.cancel(true);
    }

    int mInput;
    private class ListViewLoaderTask extends AsyncTask<Void,Integer,Void> {


        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {

            if(mListener!=null)
                mListener.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void results) {
            if(mListener!=null)
                mListener.onPostExecute();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

            int percentCompleted = (int)(progress[0]*100f/mInput);
            if(mListener!=null)
                mListener.onProgressUpdate(percentCompleted);



        }

        @Override
        protected void onCancelled() {
            if(mListener!=null)
                mListener.onCancelled();

        }


    }

}
