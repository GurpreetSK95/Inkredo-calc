package me.gurpreetsk.emicalulator.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.emicalulator.R;
import me.gurpreetsk.emicalulator.adapter.EmiAdapter;
import me.gurpreetsk.emicalulator.model.Emi;

public class EmiListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    EmiAdapter adapter;
    ArrayList<Emi> emis = new ArrayList<>();

    private static final String TAG = EmiListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_list);
        ButterKnife.bind(this);

        emis = getIntent().getParcelableArrayListExtra("emis");
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (emis != null) {
            adapter = new EmiAdapter(EmiListActivity.this, emis);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(EmiListActivity.this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

}
