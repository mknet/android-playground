package net.marcelkoch.playground.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.marcelkoch.playground.data.Job;
import net.marcelkoch.playground.data.JobsOpenHelper;

import java.util.List;

/**
 * Created by marcel on 28.04.15.
 */
public class Jobs extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.jobs);

        JobsOpenHelper jobsOpenHelper = new JobsOpenHelper(this);

        final List<Job> jobs = jobsOpenHelper.getList();

        final ListView jobsView = (ListView) findViewById(R.id.jobs);
        ArrayAdapter<Job> arrayAdapter = new ArrayAdapter<Job>(this, R.layout.job_list_item, jobs);
        jobsView.setAdapter(arrayAdapter);

        jobsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MK", "clicked=" + position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.jobs_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
