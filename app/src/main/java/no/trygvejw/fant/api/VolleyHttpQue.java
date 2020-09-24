package no.trygvejw.fant.api;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import no.trygvejw.fant.FantApi;
import no.trygvejw.fant.FantApplication;
import no.trygvejw.fant.MainActivity;

public class VolleyHttpQue {

    private static VolleyHttpQue instance = null;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public static VolleyHttpQue instance(){
        if (instance == null){
            instance = new VolleyHttpQue();
        }
        return instance;
    }

    private VolleyHttpQue(){
        this.requestQueue = Volley.newRequestQueue(FantApplication.instance().getAppContext());

        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);

            }
        };

        this.imageLoader = new ImageLoader(requestQueue, imageCache);

    }

    public <T> Request<T> addToRequestQue(Request<T> request){
        return requestQueue.add(request);
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }


}
