package no.trygvejw.fant.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;

import lombok.SneakyThrows;
import no.trygvejw.fant.CurrentUser;
import no.trygvejw.fant.FantApi;
import no.trygvejw.fant.FantApplication;
import no.trygvejw.fant.R;
import no.trygvejw.fant.api.VolleyHttpQue;
import no.trygvejw.fant.http.MultiPartForm;


public class AddItemFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private TextView title;
    private TextView description;
    private TextView price;
    private Button imageBtn;
    private Button submitBtn;
    private ImageView imageView;
    private Uri selectedImage;

    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();
        super.onPrepareOptionsMenu(menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_item, container, false);

        title = root.findViewById(R.id.new_item_title);
        description = root.findViewById(R.id.new_item_desc);
        price = root.findViewById(R.id.new_item_price);

        imageBtn = root.findViewById(R.id.uploadImg);
        imageView = root.findViewById(R.id.show_item_img);


        submitBtn = root.findViewById(R.id.new_item_add_btn);


        imageBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });


        submitBtn.setOnClickListener(v -> {
            if (selectedImage == null) {
                Snackbar.make(container.getRootView(), "add image to submitt", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            } else {
                try {
                    addItem();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });


        // Inflate the layout for this fragment
        return root;
    }

    @SneakyThrows
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE) {
            //InputStream inputStream = FantApplication.getAppContext().getContentResolver().openInputStream();
            imageView.setImageURI(data.getData());
            selectedImage = data.getData();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addItem() throws FileNotFoundException {
        MultiPartForm multiPartForm = new MultiPartForm();

        if (selectedImage != null) {
            ContentResolver cR = FantApplication.getAppContext()
                                                .getContentResolver();
            multiPartForm
                    .addPart("title", title.getText()
                                           .toString())
                    .addPart("desc", description.getText()
                                                .toString())
                    .addPart("price", price.getText()
                                           .toString())
                    .addPart(
                            "image",
                            selectedImage.getLastPathSegment(),
                            cR.getType(selectedImage),
                            cR.openInputStream(selectedImage));

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    FantApi.ADD_ITEM_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("OK");
                            Navigation.findNavController(getView())
                                      .popBackStack();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ERROR");
                    System.out.println(Arrays.toString(error.networkResponse.data));
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return multiPartForm.getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return multiPartForm.getContentTypeHeader();
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return CurrentUser.getInstance().getAuthHeaders();
                }
            };


            System.out.println(FantApi.GET_ITEMS_URL);

            VolleyHttpQue.instance().addToRequestQue(stringRequest);
        }


    }
}