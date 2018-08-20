package icelabs.eeyan.bigman.volunteeapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Jobs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link JobDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class JobListActivity extends AppCompatActivity {
    public static final ArrayList<JobContent.JobItem> JobsList = new ArrayList<JobContent.JobItem>();
    private String url = "http://192.168.1.149:5500/jobs.json";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        new JSONParser().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.job_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.job_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        setUpSearch();
    }

    private void setUpSearch() {
        MaterialSearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processQuery(newText);
                return true;
            }
        });
    }

    private void processQuery(String query) {
        List<JobContent.JobItem> filteredList = new ArrayList<>();

        for (JobContent.JobItem item : JobsList) {
            if (item.getmJobTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
            //SimpleItemRecyclerViewAdapter.filterList(filteredList);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, JobContent.ITEMS, mTwoPane));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final JobListActivity mParentActivity;
        private final List<JobContent.JobItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobContent.JobItem item = (JobContent.JobItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(JobDetailFragment.ARG_ITEM_ID, item.mID);
                    JobDetailFragment fragment = new JobDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.job_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, JobDetailActivity.class);
                    intent.putExtra(JobDetailFragment.ARG_ITEM_ID, item.mID);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(JobListActivity parent,
                                      List<JobContent.JobItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.job_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            JobContent.JobItem currentJobItem = mValues.get(position);

            holder.mLocation.setText(currentJobItem.getmLocation());
            holder.mJobType.setText(currentJobItem.getmJobType());
            holder.mImageView.setImageResource(currentJobItem.getmImageResource());
            holder.mJobTitle.setText(currentJobItem.getmJobTitle());
            holder.mJobDescription.setText(currentJobItem.getmJobDescription());
            holder.mPostTime.setText(currentJobItem.getmPostTime());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView mImageView;
            final TextView mJobTitle, mJobDescription, mCompanyName, mLocation, mJobType, mPostTime, mJobDetails, mRating, mRatingUsers, mDuration;

            ViewHolder(View itemView) {
                super(itemView);
                mLocation = itemView.findViewById(R.id.job_location);
                mJobType = itemView.findViewById(R.id.job_type);
                mImageView = itemView.findViewById(R.id.job_logo);
                mJobTitle = itemView.findViewById(R.id.job_title);
                mCompanyName = itemView.findViewById(R.id.company_name);
                mJobDescription = itemView.findViewById(R.id.job_description);
                mPostTime = itemView.findViewById(R.id.job_post_time);
                mJobDetails = itemView.findViewById(R.id.long_description);
                mRating = itemView.findViewById(R.id.mRating);
                mRatingUsers = itemView.findViewById(R.id.rating_users);
                mDuration = itemView.findViewById(R.id.duration);
            }
        }

        public void filterList(List<JobContent.JobItem> filteredList) {
            JobContent.ITEMS = filteredList;
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_menu);
        MaterialSearchView searchView = findViewById(R.id.searchView);
        searchView.setMenuItem(menuItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private class JSONParser extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(JobListActivity.this);
        HttpURLConnection connection;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                url = new URL("http://192.168.1.149:5500/jobs.json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {
                int response_code = connection.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);

                        return (result.toString());
                    }
                } else {
                    return ("Unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
            List<JobContent.JobItem> data = new ArrayList<>();
            progressDialog.dismiss();

            try{
                JSONArray jsonArray = new JSONArray(s);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JobContent.JobItem jobItem = new JobContent.JobItem();

                    jobItem.setmID(jsonObject.getString("ID"));
                    jobItem.setmImageResource(R.drawable.landscape);
                    jobItem.setmJobTitle(jsonObject.getString("job_title"));
                    jobItem.setmJobDescription(jsonObject.getString("job_description"));
                    jobItem.setmJobType(jsonObject.getString("job_type"));
                    jobItem.setmLocation(jsonObject.getString("job_location"));
                    jobItem.setmPostTime(jsonObject.getString("post_time"));
                    jobItem.setmJobDetails(jsonObject.getString("job_details"));
                    jobItem.setmRating(jsonObject.getString("rating"));
                    jobItem.setmRatingUsers(jsonObject.getString("rating_users"));
                    jobItem.setmDuration(jsonObject.getString("duration"));

                    data.add(jobItem);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

