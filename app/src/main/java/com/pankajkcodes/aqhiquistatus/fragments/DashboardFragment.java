package com.pankajkcodes.aqhiquistatus.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pankajkcodes.aqhiquistatus.R;
import com.pankajkcodes.aqhiquistatus.adapters.SaveVideoAdapter;
import com.pankajkcodes.aqhiquistatus.databinding.FragmentDashboardBinding;
import com.pankajkcodes.aqhiquistatus.models.VideoModel;

import java.io.File;
import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private int requestCode = 10;

    private SaveVideoAdapter adapter;

    private File[] files;

    private final ArrayList<VideoModel> filesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerView);
        refreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        checkPermission();

        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            setUpLayout();
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
    private void setUpLayout() {

        filesList.clear();

        recyclerView.setHasFixedSize(true);

        adapter = new SaveVideoAdapter(requireContext(),getData());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager
                (3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<VideoModel> getData() {

        VideoModel model;
        String targetPath =  Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/Movies/storage/emulated/0/";

        File  targetDir = new File(targetPath);
        files = targetDir.listFiles();
        for (File file : files) {
            model = new VideoModel();
            model.setUri(Uri.fromFile(file));
            model.setFilename(file.getName());
            model.setPath(file.getAbsolutePath());
            if (!model.getUri().toString().endsWith(".nomedia")) {
                filesList.add(model);
            }
        }

        return filesList;
    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestCode);

        }
        setUpLayout();


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}