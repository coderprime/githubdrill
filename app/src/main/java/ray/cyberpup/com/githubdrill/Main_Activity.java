package ray.cyberpup.com.githubdrill;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.HashMap;
import java.util.Set;

/**
 * Created on 6/16/15
 *
 * @author Raymond Tong
 */
public class Main_Activity extends AppCompatActivity
        implements DownloadTaskFrag.TaskListener,ListViewLoaderTaskFrag.TaskListener{


    private static final String DOWNLOAD = "download";
    private SearchView mSearch;
    private ListView usersList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersList = (ListView) findViewById(R.id.listView_git);






    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Integer... progress) {

    }

    @Override
    public void onPostExecute(Integer r) {

    }

    @Override
    public void onPostExecute(HashMap<Integer, String[]> results) {

        Set<Integer> keys = results.keySet();
        for (Integer key : keys) {
            String[] userData = results.get(key);
            System.out.printf("id:%d user:%s avatar:%s repos:%s %n", key
                    , userData[0], userData[1], userData[2]);
        }

    }

    @Override
    public void onCancelled() {

    }


    private void performSearch(String query){
        DownloadTaskFrag taskFrag = (DownloadTaskFrag) getSupportFragmentManager().
                            findFragmentByTag(DOWNLOAD);

        if(taskFrag == null){

            FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
            DownloadTaskFrag frag = DownloadTaskFrag.getInstance(query);
            tran.add(frag, DOWNLOAD).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearch = (SearchView) menu.findItem(R.id.git_search).getActionView();
        mSearch.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearch.setIconifiedByDefault(true);

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return true;
    }


}
