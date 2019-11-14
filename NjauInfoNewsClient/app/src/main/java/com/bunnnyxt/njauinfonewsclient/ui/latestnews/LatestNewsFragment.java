package com.bunnnyxt.njauinfonewsclient.ui.latestnews;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bunnnyxt.njauinfonewsclient.R;
import com.bunnnyxt.njauinfonewsclient.api.GetNewsListApi;
import com.bunnnyxt.njauinfonewsclient.api.model.News;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LatestNewsFragment extends Fragment {

//    private LatestNewsViewModel latestNewsViewModel;

    private boolean isLoadingLatestNewsList;
    private List<News> latestNewsList;
    //private List<String> latestNewsInfoList;

    ListView listView;

    private int latestPn;

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
        NewsAdapter adapter = new NewsAdapter(getActivity(), R.layout.news_item, latestNewsList);
        listView.setAdapter(adapter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        latestNewsViewModel =
//                ViewModelProviders.of(this).get(LatestNewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_latestnews, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        latestNewsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        latestNewsList = new ArrayList<News>();
        //latestNewsInfoList = new ArrayList<String>();

        listView = getView().findViewById(R.id.list_view_latest_news);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = latestNewsList.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getOriUrl()));
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                // get latest news list
                isLoadingLatestNewsList = true;
                latestPn = 1;

                try {
                    GetNewsListApi api = new GetNewsListApi(0, latestPn, 20);
                    latestNewsList = api.GetNewsList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isLoadingLatestNewsList = false;


//                for (int i = 0; i < latestNewsList.size(); i++){
//                    latestNewsInfoList.add(latestNewsList.get(i).getTitle());
//                }

                updateUIHandler.post(updateUIResults);
            }
        }).start();
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