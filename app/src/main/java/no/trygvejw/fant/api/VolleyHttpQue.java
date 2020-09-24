package no.trygvejw.fant.api;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import no.trygvejw.fant.FantApplication;

public class VolleyHttpQue {

    private static VolleyHttpQue instance = null;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleyHttpQue() {
        this.requestQueue = Volley.newRequestQueue(FantApplication.getAppContext());

        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url,
                                  Bitmap bitmap) {
                cache.put(url, bitmap);

            }
        };

        this.imageLoader = new ImageLoader(requestQueue, imageCache);

    }

    public static VolleyHttpQue instance() {
        if (instance == null) {
            instance = new VolleyHttpQue();
        }
        return instance;
    }

    public <T> void addToRequestQue(Request<T> request) {
        requestQueue.add(request);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }


}
