package com.bunnnyxt.njauinfonewsclient.ui.categorynews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bunnnyxt.njauinfonewsclient.R;
import com.bunnnyxt.njauinfonewsclient.api.GetNewsListApi;
import com.bunnnyxt.njauinfonewsclient.api.model.News;
import com.bunnnyxt.njauinfonewsclient.ui.latestnews.LatestNewsFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryNewsFragment extends Fragment {

//    private CategoryNewsViewModel categoryNewsViewModel;

    private boolean isLoadingNewsList;
    private List<News> newsList;

    ListView listView;

    int latestPn;
    int nowTid;

    final Handler updateUIHandler = new Handler();

    final Runnable updateUIResults = new Runnable() {
        @Override
        public void run() {
            updateUI();
        }
    };

    private void updateUI() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, latestNewsInfoList);
        CategoryNewsFragment.NewsAdapter adapter = new CategoryNewsFragment.NewsAdapter(getActivity(), R.layout.news_item, newsList);
        listView.setAdapter(adapter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        categoryNewsViewModel =
//                ViewModelProviders.of(this).get(CategoryNewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categorynews, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        categoryNewsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        latestPn = 1;
        nowTid = 0;

        final String[] ctype = new String[]{"全部新闻", "通知公告", "教研动态", "学生动态", "就业实践", "图片新闻", "其他"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = root.findViewById(R.id.spinner_category);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(ctype[position]);
                System.out.println(position);

                nowTid = position;

                newsList = new ArrayList<News>();

                listView = getView().findViewById(R.id.list_view_category_news);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        News news = newsList.get(position);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(news.getOriUrl()));
                        startActivity(intent);
                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // get latest news list
                        isLoadingNewsList = true;
                        latestPn = 1;

                        try {
                            GetNewsListApi api = new GetNewsListApi(nowTid, latestPn, 20);
                            newsList = api.GetNewsList();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isLoadingNewsList = false;


//                for (int i = 0; i < latestNewsList.size(); i++){
//                    latestNewsInfoList.add(latestNewsList.get(i).getTitle());
//                }

                        updateUIHandler.post(updateUIResults);
                    }
                }).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("nothing");
            }
        });

        return root;
    }

    public class NewsAdapter extends ArrayAdapter<News> {
        private int resourceId;

        public NewsAdapter(Context context, int textViewResourceId, List<News> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            News news = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            try {
                TextView newsItemTitle = view.findViewById(R.id.news_item_title);
                TextView newsItemCategory = view.findViewById(R.id.news_item_category);
                TextView newsItemCtime = view.findViewById(R.id.news_item_ctime);
                TextView newsItemAuthor = view.findViewById(R.id.news_item_author);

                newsItemTitle.setText(news.getTitle());
                newsItemCategory.setText(news.getCategory());
                newsItemCtime.setText(news.getCtimeString());
                newsItemAuthor.setText("作者：" + news.getAuthor());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return view;
        }
    }
}