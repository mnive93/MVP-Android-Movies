package co.sample.movielist.util;

import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by nivedita on 09/04/18.
 */

public class FileHelper {

    public final static String TAG = "FileHelper";
    public static void loadImage(ImageView imageView, String path){
        Log.d(TAG, "Path is : " + path);
        Glide.with(imageView.getContext())
                .load("http://image.tmdb.org/t/p/w185/"+ path)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }




}
