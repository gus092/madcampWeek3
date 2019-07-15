package com.example.screenshotorg;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.Batch;
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
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ImageProcessor {
//    private final String TAG = "CloudVisionExample";
//    static final int REQUEST_GALLERY_IMAGE = 100;
//    static final int REQUEST_CODE_PICK_ACCOUNT = 101;
//    static final int REQUEST_ACCOUNT_AUTHORIZATION = 102;
//    static final int REQUEST_PERMISSIONS = 13;
//    private ImageView selectedImage;
//    private TextView labelResults;
//    private TextView textResults;
//    private Account mAccount;
//    private ProgressDialog mProgressDialog;
//    private BatchAnnotateImagesResponse imageResult;
//
//
//    public ImageProcessor(){
//        //textResults = Fragment1.textResults;
//    }
//
//    //Used to be in main, onActivityResult
//    public BatchAnnotateImagesResponse performCloudVisionRequest(Context context, Uri uri) {
//        if (uri != null) {
//            try {
//                Bitmap bitmap = resizeBitmap(
//                        MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri));
//                callCloudVision(context, bitmap);
//                selectedImage.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                Log.e("Google cloud vision 3", e.getMessage());
//            }
//        }
//
//        return imageResult;
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private BatchAnnotateImagesResponse callCloudVision(Context context, final Bitmap bitmap) throws IOException {
//        BatchAnnotateImagesResponse myResult;
//        mProgressDialog = ProgressDialog.show(context, null,"Scanning image with Vision API...", true);
//
//        new ProcessImage().execute(bitmap);
//
//        return imageResult;
//    }
//
//
//    private class ProcessImage extends AsyncTask<Bitmap, Bitmap, BatchAnnotateImagesResponse> {
//        @Override
//        protected BatchAnnotateImagesResponse doInBackground(Bitmap... imgs) {
//            try {
//                Bitmap currImg = imgs[0];
//                GoogleCredential credential = new GoogleCredential().setAccessToken(MainActivity.accessToken);
//                HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
//                JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
//
//                Vision.Builder builder = new Vision.Builder
//                        (httpTransport, jsonFactory, credential);
//                Vision vision = builder.build();
//
//                List<Feature> featureList = new ArrayList<>();
//                Feature labelDetection = new Feature();
//                labelDetection.setType("LABEL_DETECTION");
//                labelDetection.setMaxResults(10); //Need to change
//                featureList.add(labelDetection);
//
//                Feature textDetection = new Feature();
//                textDetection.setType("TEXT_DETECTION");
//                textDetection.setMaxResults(10); //Need to change
//                featureList.add(textDetection);
//
//                List<AnnotateImageRequest> imageList = new ArrayList<>();
//                AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();
//                Image base64EncodedImage = getBase64EncodedJpeg(currImg);
//                annotateImageRequest.setImage(base64EncodedImage);
//                annotateImageRequest.setFeatures(featureList);
//                imageList.add(annotateImageRequest);
//
//                BatchAnnotateImagesRequest batchAnnotateImagesRequest =
//                        new BatchAnnotateImagesRequest();
//                batchAnnotateImagesRequest.setRequests(imageList);
//
//                Vision.Images.Annotate annotateRequest =
//                        vision.images().annotate(batchAnnotateImagesRequest);
//                annotateRequest.setDisableGZipContent(true);
//                Log.d("Google Cloud vision 4", "Sending request to Google Cloud");
//
//                BatchAnnotateImagesResponse response = annotateRequest.execute();
//                return response;
//
//            } catch (GoogleJsonResponseException e) {
//                Log.e("Google Cloud vision 1", "Request error: " + e.getContent());
//            } catch (IOException e) {
//                Log.d("Google Cloud vision 2", "Request error: " + e.getMessage());
//            }
//            return null;
//        }
//
//        protected void onPostExecute(BatchAnnotateImagesResponse response) {
//            mProgressDialog.dismiss();
//            imageResult = response;
//            textResults.setText(getDetectedTexts(response));
//            //labelResults.setText(getDetectedLabels(response));
//        }
//    }
//
//    private String getDetectedLabels(BatchAnnotateImagesResponse response){
//        StringBuilder message = new StringBuilder("");
//        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
//        if (labels != null) {
//            for (EntityAnnotation label : labels) {
//                message.append(String.format(Locale.getDefault(), "%.3f: %s",
//                        label.getScore(), label.getDescription()));
//                message.append("\n");
//            }
//        } else {
//            message.append("nothing\n");
//        }
//
//        return message.toString();
//    }
//
//    private String getDetectedTexts(BatchAnnotateImagesResponse response){
//        StringBuilder message = new StringBuilder("");
//        List<EntityAnnotation> texts = response.getResponses().get(0)
//                .getTextAnnotations();
//        if (texts != null) {
//            for (EntityAnnotation text : texts) {
//                message.append(String.format(Locale.getDefault(), "%s: %s",
//                        text.getLocale(), text.getDescription()));
//                message.append("\n");
//            }
//        } else {
//            message.append("nothing\n");
//        }
//
//        return message.toString();
//    }
//
//    public Bitmap resizeBitmap(Bitmap bitmap) {
//
//        int maxDimension = 1024;
//        int originalWidth = bitmap.getWidth();
//        int originalHeight = bitmap.getHeight();
//        int resizedWidth = maxDimension;
//        int resizedHeight = maxDimension;
//
//        if (originalHeight > originalWidth) {
//            resizedHeight = maxDimension;
//            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
//        } else if (originalWidth > originalHeight) {
//            resizedWidth = maxDimension;
//            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
//        } else if (originalHeight == originalWidth) {
//            resizedHeight = maxDimension;
//            resizedWidth = maxDimension;
//        }
//        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
//    }
//
//    public Image getBase64EncodedJpeg(Bitmap bitmap) {
//        Image image = new Image();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
//        byte[] imageBytes = byteArrayOutputStream.toByteArray();
//        image.encodeContent(imageBytes);
//        return image;
//    }

}
