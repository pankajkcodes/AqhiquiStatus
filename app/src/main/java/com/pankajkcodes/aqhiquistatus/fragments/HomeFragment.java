package com.pankajkcodes.aqhiquistatus.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.pankajkcodes.aqhiquistatus.R;
import com.pankajkcodes.aqhiquistatus.adapters.StatusAdapter;
import com.pankajkcodes.aqhiquistatus.databinding.FragmentHomeBinding;
import com.pankajkcodes.aqhiquistatus.models.StatusModel;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private ViewPager2 pager2;
    private ArrayList<StatusModel> videos;
    private StatusModel model;
    private StatusAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        pager2 = root.findViewById(R.id.viewPager2);
        refreshLayout = root.findViewById(R.id.mainRefreshLayout);

        videos = new ArrayList<>();

        adapter = new StatusAdapter(videos);
        pager2.setAdapter(adapter);

        addVideoList();
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);

            addVideoList();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            }, 1000);
        });
        return root;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addVideoList() {
        /*
        videos.add(new StatusModel("https://whatsapp-status-video.com/videos/sad-whatsapp-status-video-rula-ke-gaya-ishq-tera.mp4", "Sad Status", "Best Sad Status Of 2021"));
        videos.add(new StatusModel("https://whatsapp-status-video.com/videos/emiway-best-whatsapp-status-video.mp4", "Attitude", "Best Motivation"));
        videos.add(new StatusModel("https://whatsapp-status-video.com/videos/best-love-dialog-video-for-whatsapp-status.mp4", "", ""));
        videos.add(new StatusModel("https://whatsapp-status-video.com/videos/main-kisi-aur-ka-hun-filhaal-status-video.mp4", "", ""));
        videos.add(new StatusModel("https://whatsapp-status-video.com/videos/atif-aslam-best-song-for-love-status-video.mp4", "", ""));
        videos.add(new StatusModel("https://whatsapp-status-video.com/videos/lagdi-lahore-di-street-dancer-movie-song-status-video.mp4", "", ""));
        videos.add(new StatusModel("https://whatsapp-status-video.com/videos/ye-bhi-jaane-whatsapp-status-video.mp4", "", ""));
        videos.add(new StatusModel("https://whatsapp-status-video.com/videos/ijazat-status-video-for-whatsapp.mp4", "", ""));
        videos.add(new StatusModel("https://whatsapp-status-video.com/videos/aditya-rao-kapoor-movie-malang-2020-song-video-status.mp4", "", ""));


         */

        db.collection("STATUS")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Log.d("TAG", document.getId() + " => " + document.getString("url"));
                            model = new StatusModel(document.getString("url"),
                                    document.getString("title"), document.getString("desc"));

                            videos.add(model);
                            adapter.notifyDataSetChanged();

                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}