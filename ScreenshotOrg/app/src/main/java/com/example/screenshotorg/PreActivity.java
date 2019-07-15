package com.example.screenshotorg;

//CLOUD VISION

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.screenshotorg.ui.main.SectionsPagerAdapter;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.material.tabs.TabLayout;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class PreActivity extends AppCompatActivity {

    private final String TAG = "CloudVisionExample";
    static final int REQUEST_GALLERY_IMAGE = 100;
    static final int REQUEST_CODE_PICK_ACCOUNT = 101;
    static final int REQUEST_ACCOUNT_AUTHORIZATION = 102;
    static final int REQUEST_PERMISSIONS = 13;

    private static String accessToken;
    private ImageView selectedImage;
    private TextView labelResults;
    private TextView textResults;
    private Account mAccount;
    private ProgressDialog mProgressDialog; //Put this in MainActivity so you can use it in other classes

    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
    private String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_CONTACTS,
            //Manifest.permission.GET_ACCOUNTS,
    };

    //Check phone permissions
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);

        mProgressDialog = new ProgressDialog(this);
        checkPermissions();

        Button selectImageButton = findViewById(R.id.select_image_button);
        selectedImage = findViewById(R.id.selected_image);
        labelResults = findViewById(R.id.tv_label_results);
        textResults = findViewById(R.id.tv_texts_results);
        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        Button firsttime = findViewById(R.id.firsttime);
        firsttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDB();
            }
        });

        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDB();
            }
        });

        //Don't need
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Need this guy to choose account
                //This guy is in checkPermissions of MainActivity
                ActivityCompat.requestPermissions(PreActivity.this,
                        new String[]{Manifest.permission.GET_ACCOUNTS},
                        REQUEST_PERMISSIONS);
            }
        });
    }

    //Don't need
    private void launchImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"),
                REQUEST_GALLERY_IMAGE);
    }

    //In MainActivity
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAuthToken();
                } else {
                    Toast.makeText(PreActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissions) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;
                        }
                    }
                    // Show permissionsDenied
                    //updateViews();
                }
            }
        }


    }

    //Ignore requestCode == REQUEST_GALLERY_IMAGE (only need for choose image from gallery activity)
    //The rest goes in Main Activity for choose user account Google activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == RESULT_OK && data != null) {
            performCloudVisionRequest(data.getData());
        } else if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                AccountManager am = AccountManager.get(this);
                Account[] accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
                for (Account account : accounts) {
                    if (account.name.equals(email)) {
                        mAccount = account;
                        break;
                    }
                }
                getAuthToken();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "No Account Selected", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == REQUEST_ACCOUNT_AUTHORIZATION) {
            if (resultCode == RESULT_OK) {
                Bundle extra = data.getExtras();
                onTokenReceived(extra.getString("authtoken"));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Authorization Failed", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    //In Image Processor
    public void performCloudVisionRequest(Uri uri) {
        if (uri != null) {
            Bitmap bitmap = null;
            try {
//                InputStream image_stream;
//                try {
//
//                    image_stream = this.getContentResolver().openInputStream(uri);
//
//                    bitmap = BitmapFactory.decodeStream(image_stream);
//                } catch (FileNotFoundException e){
//                    e.printStackTrace();
//                }

                bitmap = resizeBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
                Log.e("CHECKK: ", uri.toString());
                //Bitmap bitmap = BitmapFactory.decodeFile(uri.toString());
                if (bitmap == null){
                    Log.e("HEY: ", "bitmap is null");
                }
                callCloudVision(bitmap, "a");
                selectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void performCloudVisionRequest1(String filepath) {
        if (!filepath.equals("")) {
            Bitmap bitmap = null;
            try {
                bitmap = resizeBitmap(BitmapFactory.decodeFile(filepath));
                if (bitmap == null){
                    Log.e("HEY: ", "bitmap is null");
                }
                callCloudVision(bitmap, filepath);
                selectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    //In ImageProcessor
    @SuppressLint("StaticFieldLeak")
    private void callCloudVision(final Bitmap bitmap, final String filepath) throws IOException {
        mProgressDialog = ProgressDialog.show(this, null, "Scanning image with Vision API...", true);
        Log.e("gazua : ", "you are here");
        new AsyncTask<Object, Void, BatchAnnotateImagesResponse>() {
            @Override
            protected BatchAnnotateImagesResponse doInBackground(Object... params) {
                try {
                    GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    Vision.Builder builder = new Vision.Builder
                            (httpTransport, jsonFactory, credential);
                    Vision vision = builder.build();

                    List<Feature> featureList = new ArrayList<>();
                    Feature labelDetection = new Feature();
                    labelDetection.setType("LABEL_DETECTION");
                    labelDetection.setMaxResults(10); //Need to change
                    featureList.add(labelDetection);

                    Feature textDetection = new Feature();
                    textDetection.setType("TEXT_DETECTION");
                    textDetection.setMaxResults(10); //Need to change
                    featureList.add(textDetection);

                    List<AnnotateImageRequest> imageList = new ArrayList<>();
                    AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();
                    Image base64EncodedImage = getBase64EncodedJpeg(bitmap);
                    annotateImageRequest.setImage(base64EncodedImage);
                    annotateImageRequest.setFeatures(featureList);
                    imageList.add(annotateImageRequest);

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(imageList);

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    annotateRequest.setDisableGZipContent(true);
                    Log.d(TAG, "Sending request to Google Cloud");

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    String t = getDetectedLabels(response);
                    Log.e("my labels: ", t);
                    ///CALL SORT FUNCTION HERE
                    sort(getDetectedLabels(response), filepath);
                    return response;

                } catch (GoogleJsonResponseException e) {
                    Log.e(TAG, "Request error: " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "Request error: " + e.getMessage());
                }
                return null;
            }

            protected void onPostExecute(BatchAnnotateImagesResponse response) {
                mProgressDialog.dismiss();
                textResults.setText(getDetectedTexts(response));
                labelResults.setText(getDetectedLabels(response));
                //sort( getDetectedLabels(response), filepath);
            }

        }.execute();
    }

    //In ImageProcessor
    private String getDetectedLabels(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("");
        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                message.append(String.format(Locale.getDefault(), "%.3f: %s",
                        label.getScore(), label.getDescription()));
                message.append("\n");
            }
        } else {
            message.append("nothing\n");
        }

        return message.toString();
    }

    //In ImageProcessor
    private String getDetectedTexts(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("");
        List<EntityAnnotation> texts = response.getResponses().get(0).getTextAnnotations();
        if (texts != null) {
            for (EntityAnnotation text : texts) {
                message.append(String.format(Locale.getDefault(), "%s: %s",
                        text.getLocale(), text.getDescription()));
                message.append("\n");
            }
        } else {
            message.append("nothing\n");
        }

        return message.toString();
    }

    //In ImageProcessor
    public Bitmap resizeBitmap(Bitmap bitmap) {

        int maxDimension = 1024;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    //In ImageProcessor
    public Image getBase64EncodedJpeg(Bitmap bitmap) {
        Image image = new Image();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        image.encodeContent(imageBytes);
        return image;
    }

    //Main
    private void pickUserAccount() {
        String[] accountTypes = new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    //Main
    private void getAuthToken() {
        String SCOPE = "oauth2:https://www.googleapis.com/auth/cloud-platform";
        if (mAccount == null) {
            pickUserAccount();
        } else {
            new GetOAuthToken(PreActivity.this, mAccount, SCOPE, REQUEST_ACCOUNT_AUTHORIZATION)
                    .execute();
        }
    }

    //Main
    public void onTokenReceived(String token) {
        accessToken = token;
        launchImagePicker();
    }

    //For first time users only
    private void createDB(){
        JSONObject json = new JSONObject();
        //String emptyjsonobj = obj.toString();
        //ArrayList<String> mylist= new ArrayList<String>();
        try{
            JSONArray jArray0 = new JSONArray();
            json.put("total", jArray0);

//            JSONArray jarray00 = new JSONArray();
//            json.put("notconfirmed", jarray00);

//            JSONObject allArrays = new JSONObject();
//            String[] stringarr = {};
//            allArrays.put("food", stringarr);
//            //allArrays.put("places", stringarr);
//            //allArrays.put("cosmetics", stringarr);

            //json.put("all", allArrays);
//
            JSONArray jArray1 = new JSONArray();
            json.put("shopping", jArray1);

            JSONArray jArray2 = new JSONArray();
            json.put("food", jArray2);

            JSONArray jArray3 = new JSONArray();
            json.put("places", jArray3);

            JSONArray jArray4 = new JSONArray();
            json.put("cosmetic", jArray4);

            JSONArray jArray5 = new JSONArray();
            json.put("fashion", jArray5);

            JSONArray jArray6 = new JSONArray();
            json.put("text", jArray6);

            JSONArray jArray7 = new JSONArray();
            json.put("celebrities", jArray7);

            JSONArray jArray8 = new JSONArray();
            json.put("etc", jArray8);

//            JSONObject last = new JSONObject();
//            json.put("last", last);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Student init = new Student (0, json.toString());
        addStudent(init);
        //json.put("total", new JSONArray(mylist));

//        for (int i = 0 ; i < 10; i ++){
//            json.put()
//        }
//        for (int i = 0 ; i < 10 ; i++){
//            Student s = new Student(i, emptyjsonobj);
//            addStudent(s);
//        }

        Student last = new Student(10, getLastImagePath()); //Last image processed is item 10
        addStudent(last);
        Toast.makeText(PreActivity.this, "DB Created!", Toast.LENGTH_SHORT).show();

    }

    public void addStudent (Student stu) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.addHandler(stu);
    }

    public String getLastImagePath() {
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        //uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        //currentUri = uri;
        //Log.e("URI tag", uri.toString());

//        String[] projection = { MediaStore.MediaColumns.DATA,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        //FOR ACCESSING THE SCREENSHOTS FOLDER
        cursor = this.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                null,
                MediaStore.Images.Media.DATA + " like ? ",
                new String[] {"%Screenshots%"},
                android.provider.MediaStore.Images.Media.DATE_TAKEN + " DESC"
        );
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages.get(0);
    }

    //For updating former users
    public ArrayList<String> toProcess(){
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        ArrayList<String> imagesToProcess = new ArrayList();
        String absolutePathOfImage = null;
        //uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        //currentUri = uri;
        //Log.e("URI tag", uri.toString());

//        String[] projection = { MediaStore.MediaColumns.DATA,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        //FOR ACCESSING THE SCREENSHOTS FOLDER
        cursor = this.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                null,
                MediaStore.Images.Media.DATA + " like ? ",
                new String[] {"%Screenshots%"},
                android.provider.MediaStore.Images.Media.DATE_TAKEN + " DESC"
        );  //Just do it once instead while
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);
        }

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String lastImg = dbHandler.findHandler(10).getStudentName();
        int j;
        for (int i=0 ; i < listOfAllImages.size(); i ++){
            if (listOfAllImages.get(i).equals(lastImg)){
                j = i;
                break;
            }
            imagesToProcess.add(listOfAllImages.get(i));
        }

        Collections.reverse(imagesToProcess);

        return imagesToProcess;

    }

    public void updateDB(){

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        try {
            JSONObject json = new JSONObject((dbHandler.findHandler(0).getStudentName()));
            JSONArray totalarr = json.getJSONArray("total");

//            JSONObject a = new JSONObject();
//            String[] myArray = {"a","b"};
//            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(myArray));
//            a.put("myarray", myArray);


            ArrayList<String> mylist = toProcess(); //list of filepath
            Log.e("Babies: " , mylist.toString());
            for (int i = 0 ; i < mylist.size(); i++){
                //Object of the item, its
                JSONObject j = new JSONObject();
                j.put("path", mylist.get(i)); // j must also have the attributes "categories" and "text"
                //String[] strarr = {};
                j.put("categories", "a");
                j.put("text", "a");
                performCloudVisionRequest1(mylist.get(i));
                totalarr.put(j);

                //Make this the last processed photo
                dbHandler.updateHandler( 10 ,mylist.get(i));

                //Add each to JSON "total"
                //Add each to JSON "unconfirmed"


            }

            json.put("total", totalarr);
            dbHandler.updateHandler(0, json.toString());
            Log.e("Finished my thing: ","hey you're done");
            Log.e("Print current state1", json.toString());
        } catch (JSONException e){
            e.printStackTrace();
        }
        //mylist (String list of filepaths)


    }

    public static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }

    public void sort(String label, String filepath){ //take care of error above
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String[] shopping = {"perfume", "cosmetic", "clothes", "jewelry", "shoes", "shopping", "product"};
        String[] food= {"fruit", "food", "dish", "cuisine","kitchen"};
        String[] places = {"city", "tourist","landmark", "metropolis", "vacation", "beach", "sea", "island", "tourism", "caribbean", "mountain", "nature", "tree"};
        String[] cosmetics = {"cosmetic", "lip", "eyeshadow", "tint", "beauty", "perfume", "blush", "mascara", "foundation", "skin care"};
        String[] fashion = {"hat", "sunglass", "shirt", "dress", "scarf", "pants","fashion", "shoes", "clothes", "bag"};
        String[] text = {"text", "document"};

        int count = 0;
        //Shopping
        try {
            JSONObject json = new JSONObject((dbHandler.findHandler(0).getStudentName()));
            JSONArray foodjson = json.getJSONArray("food");
            //ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(myArray));

            for (String s :food){
                if (containsIgnoreCase(label, s)){
                    foodjson.put(filepath);
                    json.put("food", foodjson);
                    //dbHandler.updateHandler(0, json.toString()); //For multiple categories this belongs outside
                    count++;
                    break;
                }
            }

            JSONArray shoppingjson = json.getJSONArray("shopping");

            for (String s :shopping){
                if (containsIgnoreCase(label, s)){
                    shoppingjson.put(filepath);
                    json.put("shopping", shoppingjson);
                    //dbHandler.updateHandler(0, json.toString()); //For multiple categories this belongs outside
                    count++;
                    break;
                }
            }

            JSONArray placesjson = json.getJSONArray("places");
            for (String s :places){
                if (containsIgnoreCase(label, s)){
                    placesjson.put(filepath);
                    json.put("places", placesjson);
                    //dbHandler.updateHandler(0, json.toString()); //For multiple categories this belongs outside
                    count++;
                    break;
                }
            }

            JSONArray cosmeticjson = json.getJSONArray("cosmetic");
            for (String s :cosmetics){
                if (containsIgnoreCase(label, s)){
                    cosmeticjson.put(filepath);
                    json.put("cosmetic", cosmeticjson);
                    //dbHandler.updateHandler(0, json.toString()); //For multiple categories this belongs outside
                    count++;
                    break;
                }
            }

            JSONArray fashionjson = json.getJSONArray("fashion");
            for (String s :fashion){
                if (containsIgnoreCase(label, s)){
                    fashionjson.put(filepath);
                    json.put("fashion", fashionjson);
                    //dbHandler.updateHandler(0, json.toString()); //For multiple categories this belongs outside
                    count++;
                    break;
                }
            }

            JSONArray textjson = json.getJSONArray("text");
            for (String s :text){
                if (containsIgnoreCase(label, s)){
                    textjson.put(filepath);
                    json.put("text", textjson);
                    //dbHandler.updateHandler(0, json.toString()); //For multiple categories this belongs outside
                    count++;
                    break;
                }
            }

            if (count == 0){
                JSONArray etcjson = json.getJSONArray("etc");
                etcjson.put(filepath);
                json.put("etc", etcjson);
            }

            dbHandler.updateHandler(0, json.toString());

            Log.e("Print current state", json.toString());

            //dbHandler.updateHandler(2, foodjson.toString());
        } catch(JSONException e){
            e.printStackTrace();
        }

    }

}