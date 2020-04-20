package edu.upenn.cis350.advanceddirectives;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfilePicActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE_CODE = 1;
    ImageView currPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);
        this.currPic = findViewById(R.id.currPic);
        try {
            byte[] decodedString = Base64.decode(Home.currentUser.getImage(), Base64.DEFAULT);
            if (decodedString != null) {
                Bitmap decodedByte = BitmapFactory.decodeByteArray(
                        decodedString, 0, decodedString.length);
                currPic.setImageBitmap(decodedByte);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPreviewPicClick(View v) {
        Intent galleryIntent =
        new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE_CODE);

    }

    public void onUploadPicBut(View v) {
        Bitmap image = ((BitmapDrawable) currPic.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            image.compress(Bitmap.CompressFormat.JPEG, 1, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),
                    Base64.DEFAULT);
            Home.currentUser.setImage(encodedImage);
        } catch (Exception e) {

        }
//        System.out.println(encodedImage.length());
        Home.updateDB();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            currPic.setImageURI(selectedImage);
        }
    }
}
