package com.example.myapplication;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.master.glideimageview.GlideImageView;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profil extends Fragment {
    ImageButton profilduzenleisim, profilduzenlesoyisim, profilduzenlemail;
    ImageButton profilonay;

    GlideImageView profilresimid;

    EditText profilisimedit;
    EditText profilsoyisimedit;
    EditText profilmailedit;


    TextView profilduzenleid;

    FirebaseDatabase database;
    DatabaseReference myRef, myRefx, myRefy, myRefz;

    DatabaseReference myRefPic;

    Context context;

    prefence pr;

    String myid;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;



    public Profil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.profilduzenle, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context=getActivity().getApplicationContext();

        pr=new prefence();
        myid=pr.getValue(context);


        profilonay=(ImageButton)getView().findViewById(R.id.profilonay);
        profilduzenleisim=(ImageButton)getView().findViewById(R.id.profilduzenleisim);
        profilduzenlesoyisim=(ImageButton)getView().findViewById(R.id.profilduzenlesoyisim);
        profilduzenlemail=(ImageButton)getView().findViewById(R.id.profilduzenlemail);
        profilduzenleid=(TextView) getView().findViewById(R.id.profilduzenleid);
        profilisimedit=(EditText) getView().findViewById(R.id.profilisimedit);
        profilsoyisimedit=(EditText) getView().findViewById(R.id.profilsoyisimedit);
        profilmailedit=(EditText) getView().findViewById(R.id.profilmailedit);
        profilresimid=(GlideImageView) getView().findViewById(R.id.profilresimid);

        storageReference = FirebaseStorage.getInstance().getReference("bireyselusers");
        myRefPic = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid);


        myRef = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid);
        myRefx = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid);
        myRefy = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid);
        myRefz = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                bireyselkullanicilar bk = dataSnapshot.getValue(bireyselkullanicilar.class);
                profilduzenleid.setText(bk.getId());
                profilisimedit.setText(bk.getIsim());
                profilsoyisimedit.setText(bk.getSoyisim());
                profilmailedit.setText(bk.getEmail());

                if(bk.getImageURL()!= null){

                    Glide.with(Profil.this).load(bk.getImageURL()).into(profilresimid);

                }else{


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        profilduzenleisim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profilisimedit.setEnabled(true);
                profilonay.setVisibility(View.VISIBLE);
            }
        });

        profilduzenlesoyisim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profilsoyisimedit.setEnabled(true);
                profilonay.setVisibility(View.VISIBLE);
            }
        });

        profilduzenlemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profilmailedit.setEnabled(true);
                profilonay.setVisibility(View.VISIBLE);
            }
        });



        profilonay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                writeNewPost(myid,profilisimedit.getText().toString(),profilsoyisimedit.getText().toString(),profilmailedit.getText().toString());
                profilisimedit.setEnabled(false);
                profilsoyisimedit.setEnabled(false);
                profilmailedit.setEnabled(false);
                profilonay.setVisibility(View.GONE);


            }
        });


        profilresimid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImage();
            }
        });





    }

    private void openImage() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);

    }


    private String getFileExtension(Uri uri){

        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadImage () {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();


        if (imageUri != null){

            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if (!task.isSuccessful()) {

                        throw task.getException();

                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful()){

                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        myRefPic = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid);

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        myRefPic.updateChildren(map);

                        pd.dismiss();

                    }else {

                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }else {

            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){

                Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();

            }else{

                uploadImage();
            }


        }


    }



    private void writeNewPost(String id, String isim, String soyisim, String email) {

        Map<String, Object> updateisim = new HashMap<>();
        Map<String, Object> updatesoyisim = new HashMap<>();
        Map<String, Object> updateemail = new HashMap<>();
        updateisim.put("isim", isim);
        updatesoyisim.put("soyisim", soyisim);
        updateemail.put("email", email);


        myRefx.updateChildren(updateisim);
        myRefy.updateChildren(updatesoyisim);
        myRefz.updateChildren(updateemail);

    }




}