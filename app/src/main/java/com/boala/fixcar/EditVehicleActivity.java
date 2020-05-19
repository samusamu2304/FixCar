package com.boala.fixcar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class EditVehicleActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE = 1;
    EditText fechaITV, fechaNeumaticos, fechaAceite, fechaRevision, marca, modelo, matricula, motor, color, kilometraje, seguro;
    ImageView header;
    int id = -1;

    Vehiculo getresult;

    FixCarApi fixCarApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fechaITV = findViewById(R.id.fechaItv);
        fechaITV.setOnClickListener(this);
        fechaNeumaticos = findViewById(R.id.fechaNeumaticos);
        fechaNeumaticos.setOnClickListener(this);
        fechaAceite = findViewById(R.id.fechaAceite);
        fechaAceite.setOnClickListener(this);
        fechaRevision = findViewById(R.id.fechaRevision);
        fechaRevision.setOnClickListener(this);
        marca = findViewById(R.id.marca);
        modelo = findViewById(R.id.modelo);
        matricula = findViewById(R.id.matricula);
        motor = findViewById(R.id.motor);
        color = findViewById(R.id.color);
        kilometraje = findViewById(R.id.kilometraje);
        seguro = findViewById(R.id.seguro);
        header = findViewById(R.id.header);
        header.setOnClickListener(this);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new
                OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder().header("Authorization", Credentials.basic("Cesur","FixCar"));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).addInterceptor(loggingInterceptor).build();
        Gson gson = new GsonBuilder().setLenient().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fixcarcesur.herokuapp.com/model/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        fixCarApi = retrofit.create(FixCarApi.class);

        fab.setOnClickListener(this);
        id = getIntent().getIntExtra("idVeh",-1);

        getVehicle(id);

    }

    private void showDatePickerDialog(final EditText editText){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day+"/"+(month+1)+"/"+year;
                editText.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(),"datePicker");
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.fechaItv:
                showDatePickerDialog(fechaITV);
                break;
            case R.id.fechaNeumaticos:
                showDatePickerDialog(fechaNeumaticos);
                break;
            case R.id.fechaAceite:
                showDatePickerDialog(fechaAceite);
                break;
            case R.id.fechaRevision:
                showDatePickerDialog(fechaRevision);
                break;
            case R.id.fab:
                try {
                    if (id > -1) {
                        editVehicle();
                    }else{
                        addVehicle();
                    }
                    break;
                }catch (Exception e){
                    Snackbar.make(view, "Campos vacios", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.header:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
                break;
        }

    }
    private void editVehicle(){
        Call<Boolean> call = fixCarApi.putVehicle(id,"3",
                kilometraje.getText().toString(),
                Vehiculo.dateToString2(Vehiculo.stringToDate(fechaITV.getText().toString())),
                Vehiculo.dateToString2(Vehiculo.stringToDate(fechaNeumaticos.getText().toString())),
                Vehiculo.dateToString2(Vehiculo.stringToDate(fechaAceite.getText().toString())),
                Vehiculo.dateToString2(Vehiculo.stringToDate(fechaRevision.getText().toString())),
                modelo.getText().toString(),
                marca.getText().toString(),
                motor.getText().toString(),
                seguro.getText().toString(),
                color.getText().toString(),
                matricula.getText().toString(),
                "imagen.jpg");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                if (!response.isSuccessful()){
                    Log.e("error",String.valueOf(response.code()));
                }
                finish();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("error2:",t.getMessage());
            }
        });

    }
    private void addVehicle(){
        Call<Boolean> call = fixCarApi.postVehicle("3",
                kilometraje.getText().toString(),
                Vehiculo.dateToString2(Vehiculo.stringToDate(fechaITV.getText().toString())),
                Vehiculo.dateToString2(Vehiculo.stringToDate(fechaNeumaticos.getText().toString())),
                Vehiculo.dateToString2(Vehiculo.stringToDate(fechaAceite.getText().toString())),
                Vehiculo.dateToString2(Vehiculo.stringToDate(fechaRevision.getText().toString())),
                modelo.getText().toString(),
                marca.getText().toString(),
                motor.getText().toString(),
                seguro.getText().toString(),
                color.getText().toString(),
                matricula.getText().toString(),
                "imagen.jpg");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                if (!response.isSuccessful()){
                    Log.e("error",String.valueOf(response.code()));
                }
                finish();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("error2:",t.getMessage());
            }
        });
    }

    private void getVehicle(int vehId){
        Call<VehiculoExpandable> call = fixCarApi.getVehicle(vehId);
        call.enqueue(new Callback<VehiculoExpandable>() {
            @Override
            public void onResponse(Call<VehiculoExpandable> call, retrofit2.Response<VehiculoExpandable> response) {
                if (!response.isSuccessful()){
                    Log.e("Code: ",String.valueOf(response.code()));
                    return;
                }
                Vehiculo respuesta = response.body();
                getresult = new Vehiculo(respuesta);
                updateUI();
            }

            @Override
            public void onFailure(Call<VehiculoExpandable> call, Throwable t) {
                Log.e("error: ",t.getMessage());
            }
        });
    }

    private void updateUI(){
            fechaITV.setText(Vehiculo.dateToString(getresult.getFechaItv()));
            fechaNeumaticos.setText(Vehiculo.dateToString(getresult.getFechaRuedas()));
            fechaAceite.setText(Vehiculo.dateToString(getresult.getFechaAceite()));
            fechaRevision.setText(Vehiculo.dateToString(getresult.getFechaRevision()));
            marca.setText(getresult.getMarca());
            modelo.setText(getresult.getModelo());
            matricula.setText(getresult.getMatricula());
            motor.setText(getresult.getMotor());
            color.setText(getresult.getColor());
            kilometraje.setText(String.valueOf(getresult.getKmVehiculo()));
            seguro.setText(getresult.getSeguro());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();

                header.setImageURI(selectedImage);
            header.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }
}