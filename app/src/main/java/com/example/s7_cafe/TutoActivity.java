package com.example.s7_cafe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class TutoActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);
        imageView = (ImageView) findViewById(R.id.imageView);

        //소프트키(네비게이션바) 없애기 시작
        View decorView = getWindow().getDecorView();

        int uiOption = getWindow().getDecorView().getSystemUiVisibility();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOption);
        //소프트키(네비게이션바) 없애기 끝



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView.setImageResource(R.drawable.p2);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView.setImageResource(R.drawable.p3);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imageView.setImageResource(R.drawable.p4);
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        imageView.setImageResource(R.drawable.p5);
                                        imageView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                imageView.setImageResource(R.drawable.p6);
                                                imageView.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        imageView.setImageResource(R.drawable.p7);
                                                        imageView.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                imageView.setImageResource(R.drawable.p8);
                                                                imageView.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        imageView.setImageResource(R.drawable.p9);
                                                                        imageView.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                imageView.setImageResource(R.drawable.p10);
                                                                                imageView.setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        imageView.setImageResource(R.drawable.p11);
                                                                                        imageView.setOnClickListener(new View.OnClickListener() {
                                                                                            @Override
                                                                                            public void onClick(View v) {
                                                                                                imageView.setImageResource(R.drawable.p12);
                                                                                                imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(View v) {

                                                                                                        imageView.setImageResource(R.drawable.p13);
                                                                                                        imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(View v) {
                                                                                                                imageView.setImageResource(R.drawable.p14);
                                                                                                                imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(View v) {
                                                                                                                        imageView.setImageResource(R.drawable.p15);
                                                                                                                        imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                                            @Override
                                                                                                                            public void onClick(View v) {

                                                                                                                                imageView.setImageResource(R.drawable.p16);
                                                                                                                                imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                                                    @Override
                                                                                                                                    public void onClick(View v) {

                                                                                                                                        imageView.setImageResource(R.drawable.p17);
                                                                                                                                        imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                                                            @Override
                                                                                                                                            public void onClick(View v) {

                                                                                                                                                imageView.setImageResource(R.drawable.p18);
                                                                                                                                                imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onClick(View v) {

                                                                                                                                                        imageView.setImageResource(R.drawable.p19);
                                                                                                                                                        imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onClick(View v) {

                                                                                                                                                                imageView.setImageResource(R.drawable.p20);
                                                                                                                                                                imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void onClick(View v) {

                                                                                                                                                                        imageView.setImageResource(R.drawable.p21);
                                                                                                                                                                        imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                                                                                            @Override
                                                                                                                                                                            public void onClick(View v) {

                                                                                                                                                                                imageView.setImageResource(R.drawable.p22);
                                                                                                                                                                                imageView.setOnClickListener(new View.OnClickListener() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onClick(View v) {

                                                                                                                                                                                        Intent intent = new Intent(TutoActivity.this, DayActivity.class);
                                                                                                                                                                                        startActivity(intent);
                                                                                                                                                                                        finish();
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                            }
                                                                                                                                                                        });
                                                                                                                                                                    }
                                                                                                                                                                });
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                    }
                                                                                                                                                });
                                                                                                                                            }
                                                                                                                                        });
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            }
                                                                                                                        });
                                                                                                                    }
                                                                                                                });
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(TutoActivity.this,EndGameActivity.class);
        startActivity(intent);
    }
}
