package com.example.abe.mobiletrabalho.danger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.abe.mobiletrabalho.R;

public class DangerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DangerAdapter dangerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_danger);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dangerAdapter = new DangerAdapter(100);
        recyclerView.setAdapter(dangerAdapter);
    }
}
