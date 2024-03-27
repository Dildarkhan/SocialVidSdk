package com.dildarkhan.socialvidsdk.ui.home;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.dildarkhan.socialvidsdk.R;
import com.dildarkhan.socialvidsdk.adapters.VideoAdapter;
import com.dildarkhan.socialvidsdk.databinding.FragmentHomeBinding;

import com.dildarkhan.socialvidsdk.model.ResponseVideo;
import com.dildarkhan.socialvidsdk.model.Video;
import com.dildarkhan.socialvidsdk.network.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context context = null;

    private TextView tvVideo; // DE TEST. Sau nay sua thanh clip de xem
    private ViewPager2 viewPager2;
    ArrayList<Video> videos;
    public VideoAdapter videoAdapter;


    Uri videoUri;

    public static HomeFragment newInstance(String strArg) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("name", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity(); // use this reference to invoke main callbacks
        }
        catch (IllegalStateException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// inflate res/layout_blue.xml to make GUI holding a TextView and a ListView
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_home, null);
        //tvVideo = (TextView) layout.findViewById(R.id.tvVideo);





/////////////////////////////////////////////////////////////////////////
        viewPager2 = layout.findViewById(R.id.viewPager);
        videos = new ArrayList<>();
        videoAdapter = new VideoAdapter(context, videos);
       // VideoAdapter.setUser(user);
        viewPager2.setAdapter(videoAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                videoAdapter.pauseVideo(videoAdapter.getCurrentPosition());
                videoAdapter.playVideo(position);
                videoAdapter.updateWatchCount(position);
                Log.e("Selected_Page", String.valueOf(videoAdapter.getCurrentPosition()));
                videoAdapter.updateCurrentPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        viewPager2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {

            }

            @Override
            public void onViewDetachedFromWindow(View view) {
//                Log.i("position", viewPager2.getVerticalScrollbarPosition() + "");
                videoAdapter.pauseVideo(videoAdapter.getCurrentPosition());

            }
        });

        loadVideos();
        return layout;
    }

    @Override public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {


    }//on click

    public void pauseVideo() {
        SharedPreferences currentPosPref = context.getSharedPreferences("position", Context.MODE_PRIVATE);
        SharedPreferences.Editor positionEditor = currentPosPref.edit();
        int currentPosition = videoAdapter.getCurrentPosition();
        positionEditor.putInt("position", currentPosition);
        videoAdapter.pauseVideo(currentPosition);
        positionEditor.apply();
    }

    public void continueVideo() {
        SharedPreferences currentPosPref = context.getSharedPreferences("position", Context.MODE_PRIVATE);
        int currentPosition = currentPosPref.getInt("position", -1);
        if (currentPosition != -1) {
            videoAdapter.playVideo(currentPosition);
        }
    }

    private void loadVideos() {

        Call<JsonObject> call = RetrofitClient.getInstance().getApiService().getVideos();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("KHAN ", response.body().toString());
                JsonArray jsonArray = response.body().getAsJsonArray("msg");
                Log.d("KHAN aa",jsonArray.toString());
                for(int i = 0; i<jsonArray.size();i++){
                    JsonObject jsonObject = (JsonObject) jsonArray.get(i);
                    String caption = String.valueOf(jsonObject.get("description"));
                    String vidUri = String.valueOf(jsonObject.get("video"));

                    URL url;
                    try {
                        url = new URL(vidUri.replace("\"", ""));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
/*
                    String caption = String.valueOf(jsonObject.get("description"));
                    String caption = String.valueOf(jsonObject.get("description"));
*/
                    Video video = new Video("",url.toString(),"","",caption);
                    videos.add(0, video);
                    videoAdapter.notifyItemInserted(0);
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("KHAN error",t.getMessage());
            }
        });

    }


}












/*{

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}*/