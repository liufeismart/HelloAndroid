package com.liufeismart.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liufeismart.helloandroid.activity.HelloTestActivity;
import com.liufeismart.helloandroid.settings.LanguageSettingsActiivty;
import com.liufeismart.helloandroid.wifi.HiddenNetworkActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private String[] data = {
            "语言设置",
            "Junit",
            "WIFI"
    };
    private Class[] jumpTo = {
            LanguageSettingsActiivty.class,
            HelloTestActivity.class,
            HiddenNetworkActivity.class

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) this.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //
        MyAdapter myAdapter = new MyAdapter(data);
        rv.setAdapter(myAdapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private String[] data;
        public MyAdapter(String[] data) {
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_main, null);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv.setText(data[position]);
            holder.tv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, jumpTo[position]);
                    MainActivity.this.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.length;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_);
        }
    }

//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//    }
}
