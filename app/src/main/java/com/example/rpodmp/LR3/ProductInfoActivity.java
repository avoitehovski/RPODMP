package com.example.rpodmp.LR3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rpodmp.R;
import com.example.rpodmp.databinding.ActivityProductInfoBinding;
import com.example.rpodmp.entities.Product;

public class ProductInfoActivity extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    private ImageView imageView;
    String currentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        Bundle arguments = getIntent().getExtras();
        ActivityProductInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product_info);
        Product product = (Product)arguments.getSerializable(Product.class.getSimpleName());
        binding.setProduct(product);

        imageView = findViewById(R.id.cameraImageView);
        Button cameraButton = findViewById(R.id.getCameraImageButton);
        cameraButton.setOnClickListener(v -> {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, 0);
        });

        Button galleryButton = findViewById(R.id.getGalleryImageButton);
        galleryButton.setOnClickListener(v -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
        });

        TextView emailTextView = findViewById(R.id.emailValueTextView);
        emailTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+ product.getEmail()));
            intent.putExtra(Intent.EXTRA_SUBJECT, "email_subject");
            intent.putExtra(Intent.EXTRA_TEXT, "email_body");
            startActivity(intent);
        });

        TextView phoneTextview = findViewById(R.id.phoneValueTextView);
        phoneTextview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", product.getPhone(), null));
            startActivity(intent);
        });

        TextView linkTextView = findViewById(R.id.linkValueTextView);
        linkTextView.setOnClickListener(v -> {
            Uri uri = Uri.parse(product.getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
    }
}