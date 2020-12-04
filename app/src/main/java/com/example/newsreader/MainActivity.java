package com.example.newsreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.example.newsreader.Api.BASE_URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView news = (TextView)findViewById(R.id.newsname);



        Call<News> call = RetrofitClient.getInstance().getMyApi().getNews();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                List<Article> articles = response.body().getArticles();

                news.setText(response.body().getArticles().get(0).getSource().getName() );

                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
                MyAdapter myAdapter = new MyAdapter(MainActivity.this,articles);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter);


            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Please check the intenet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }







    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

       private List<Article> articles;
       private Context context;

        public MyAdapter(Context context, List<Article> articles) {
            this.context = context;
            this.articles = articles;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView title, author, publish;
            ConstraintLayout layout ;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageview);
                title = itemView.findViewById(R.id.title);
                author = itemView.findViewById(R.id.auth_name);
                publish = itemView.findViewById(R.id.publish);
                layout = itemView.findViewById(R.id.main_layout);

            }
        }

        @Override
        public int getItemCount() {
            return articles.size();
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.list_item, parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

            for(int i = 0; i < articles.size(); i++){


                Glide.with(context).load(articles.get(position).getUrlToImage()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.imageView);
                holder.title.setText(articles.get(position).getTitle());
                holder.author.setText(articles.get(position).getAuthor());
                holder.publish.setText(articles.get(position).getPublishedAt());
            }

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = articles.get(position).getUrl();
                    Intent intent = new Intent(MainActivity.this,Webview.class);
                    intent.putExtra("news_url", url);
                    startActivity(intent);
                }
            });

        }




    }
}