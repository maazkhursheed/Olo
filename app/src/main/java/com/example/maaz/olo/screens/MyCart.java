package com.example.maaz.olo.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.maaz.olo.R;
import com.squareup.picasso.Picasso;

public class MyCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        String url="http://s33.postimg.org/jsq4h7e9b/pizza.jpg";
        //Initialize ImageView
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

//Loading image from below url into imageView

//        Picasso.with(this)
//                .load(url)
//                .into(imageView);

        Picasso.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.arrow)
                .error(R.string.hello_blank_fragment)
                .resize(250, 200)                        // optional
                .rotate(90)
                .into(imageView);
    }
}
