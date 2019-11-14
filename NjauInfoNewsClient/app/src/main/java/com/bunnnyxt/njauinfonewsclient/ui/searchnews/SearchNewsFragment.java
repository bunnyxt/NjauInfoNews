package com.bunnnyxt.njauinfonewsclient.ui.searchnews;

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
import android.widget.Button;
import android.widget.EditText;
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
import com.bunnnyxt.njauinfonewsclient.api.SearchNewsApi;
import com.bunnnyxt.njauinfonewsclient.api.model.News;
import com.bunnnyxt.njauinfonewsclient.ui.latestnews.LatestNewsFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsFragment extends Fragment {

//    private SearchNewsViewModel searchNewsViewModel;

    private boolean isLoadingSearchNewsList;
    private List<News> searchNewsList;

    ListView listView;

    private int latestPn;
    private int searchTid;

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
        SearchNewsFragment.NewsAdapter adapter = new SearchNewsFragment.NewsAdapter(getActivity(), R.layout.news_item, searchNewsList);
        listView.setAdapter(adapter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        searchNewsViewModel =
//                ViewModelProviders.of(this).get(SearchNewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_searchnews, container, false);
//        final TextView textView = root.findViewById(R.id.text_tools);
//        searchNewsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        latestPn = 1;

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        searchNewsList = new ArrayList<News>();

        listView = getView().findViewById(R.id.list_view_search_news);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = searchNewsList.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getOriUrl()));
                startActivity(intent);
            }
        });

        final String[] ctype = new String[]{"全部新闻", "通知公告", "教研动态", "学生动态", "就业实践", "图片新闻", "其他"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = getActivity().findViewById(R.id.spinner_search_category);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(ctype[position]);
                System.out.println(position);

                searchTid = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("nothing");
            }
        });

        Button button = getActivity().findViewById(R.id.button_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // get latest news list
                        isLoadingSearchNewsList = true;

                        EditText searchTitle = getActivity().findViewById(R.id.edit_text_search_title);
                        EditText searchAuthor = getActivity().findViewById(R.id.edit_text_search_author);

                        try {
                            SearchNewsApi api = new SearchNewsApi(searchTid, searchTitle.getText().toString(), searchAuthor.getText().toString(), latestPn, 20);
                            searchNewsList = api.SearchNews();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isLoadingSearchNewsList = false;

                        updateUIHandler.post(updateUIResults);
                    }
                }).start();
            }
        });

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