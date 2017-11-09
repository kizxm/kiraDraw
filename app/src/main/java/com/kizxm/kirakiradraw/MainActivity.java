package com.kizxm.kirakiradraw;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kizxm.kirakiradraw.screenshot_logic.ScreenshotType;
import com.kizxm.kirakiradraw.screenshot_logic.ScreenshotUtils;

import java.io.File;

import static com.kizxm.kirakiradraw.R.mipmap.ic_launcher;

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

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("  ~~starting kiraDraw~~");
        progress.setIcon(ic_launcher);
        progress.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3500);

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
    }

    private void takeScreenshot(ScreenshotType screenshotType) {
        Bitmap b = null;
        switch (screenshotType) {
            case FULL:
                b = ScreenshotUtils.getScreenShot(rootContent);
                break;
        }

        if (b != null) {
            File saveFile = ScreenshotUtils.getMainDirectoryName(this);
            File file = ScreenshotUtils.store(b, "screenshot" + screenshotType + ".jpg", saveFile);
            shareScreenshot(file);
        } else
            Toast.makeText(this, "Saving Failed", Toast.LENGTH_SHORT).show();

    }

private void shareScreenshot(File file) {
    Uri uri = Uri.fromFile(file);
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);
    intent.setType("image/*");
    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
    intent.putExtra(Intent.EXTRA_STREAM, uri);
}

}
