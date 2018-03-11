package com.example.josephmolina.getacross.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.josephmolina.getacross.R;
import com.example.josephmolina.getacross.Utils.Utility.DexterCameraPermission;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoTranslateFragment extends Fragment {
    private Unbinder unbinder;
    @BindView(R.id.camera)
    CameraView cameraView;
    @BindView(R.id.snapshotButton)
    ImageView snapshotButton;

    public PhotoTranslateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_translate, container, false);
        unbinder = ButterKnife.bind(this, view);

        DexterCameraPermission.checkCameraPermission(getActivity());
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onCameraOpened(CameraOptions options) {
                snapshotButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCameraClosed() {
                snapshotButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCameraError(CameraException error) {
            }

            @Override
            public void onPictureTaken(byte[] picture) {

            }

        });
        cameraView.start();

        return view;
    }

    @OnClick(R.id.snapshotButton)
    public void onSnapShotButton() {
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }
}
