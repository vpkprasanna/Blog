package com.example.prasanna.blog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArticleListAdapter articleListAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        articleListAdapter = new ArticleListAdapter();

        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setAdapter(articleListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ///progress dialogbox
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Articles");
        progressDialog.show();

        ApiManager.getApiInterface().getArticles()
                .enqueue(new Callback<List<Article>>() {

                    @Override
                    public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                        progressDialog.dismiss();
                       if(response.isSuccessful()){
                           articleListAdapter.setData(response.body());
                       }else{
                           //show alert
                           progressDialog.dismiss();
                           Toast.makeText(ArticleListActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                       }
                    }

                    @Override
                    public void onFailure(Call<List<Article>> call, Throwable t) {
                        Toast.makeText(ArticleListActivity.this,"Failed",Toast.LENGTH_SHORT).show();

                    }
                });
    }
    public class ArticleListAdapter extends RecyclerView.Adapter<articleItemViewHolder>{
        List<Article> articleList = new ArrayList<>();

        @Override
        public articleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article,parent,false);
            return new articleItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(articleItemViewHolder holder, final int position) {
            holder.articleName.setText(articleList.get(position).getHeading());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(ArticleListActivity.this,"Article Clicked : "+articleList.get(position).getHeading(),Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return articleList.size();
        }

        public void setData(List <Article> data){
            this.articleList = data;
            this.notifyDataSetChanged();
        }
    }
}
