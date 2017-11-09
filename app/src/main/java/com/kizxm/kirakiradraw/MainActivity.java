package com.kizxm.kirakiradraw;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private CanvasView canvasView;

    private Button fullPageScreenshot, customPageScreenshot;
    private RelativeLayout rootContent;
    private ImageView imageView;
    private TextView hiddenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvasView = (CanvasView) findViewById(R.id.canvasView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        canvasView.init(metrics);

        findViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.normal:
                canvasView.normal();
                return true;
            case R.id.emboss:
                canvasView.emboss();
                return true;
            case R.id.blur:
                canvasView.blur();
                return true;
            case R.id.clear:
                canvasView.clear();
                return true;

            case R.id.save:
                takeScreenshot(ScreenshotType.FULL);

        }

        return super.onOptionsItemSelected(item);
    }

    private void findViews() {

        rootContent = (RelativeLayout) findViewById(R.id.activity_main);

//        imageView = (ImageView) findViewById(R.id.image_view);
//
//        hiddenText = (TextView) findViewById(R.id.hidden_text);
    }

    private void takeScreenshot(ScreenshotType screenshotType) {
        Bitmap b = null;
        switch (screenshotType) {
            case FULL:
                b = ScreenshotUtils.getScreenShot(rootContent);
                break;
        }

        if (b != null) {
//            showScreenShotImage(b);

            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenshotUtils.store(b, "screenshot" + screenshotType + ".jpg", saveFile);//save the screenshot to selected path
            shareScreenshot(file);
        } else
            Toast.makeText(this, "Saving Failed", Toast.LENGTH_SHORT).show();

    }
//    private void showScreenShotImage(Bitmap b) {
//        imageView.setImageBitmap(b);
//    }
private void shareScreenshot(File file) {
    Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);
    intent.setType("image/*");
    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
    intent.putExtra(Intent.EXTRA_STREAM, uri);//pass uri here
}

}
