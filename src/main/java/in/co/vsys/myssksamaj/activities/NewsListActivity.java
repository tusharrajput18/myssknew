package in.co.vsys.myssksamaj.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import in.co.vsys.myssksamaj.R;

public class NewsListActivity extends AppCompatActivity {

    private RecyclerView mNewsListRecycler;
    private FloatingActionButton flot_add_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
    }
}