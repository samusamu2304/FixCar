package com.boala.fixcar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class VehAdapterEx extends RecyclerView.Adapter<VehAdapterEx.VehHolder> {
    /**
     * Asignacion de datos al recyclerview de vehiculos
     **/
    private Context context;
    private ArrayList<VehiculoExpandable> content;

    VehAdapterEx(Context context, ArrayList<VehiculoExpandable> content) {
        this.context = context;
        this.content = content;
    }

    @NonNull
    @Override
    public VehAdapterEx.VehHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_expandable, parent, false);
        return new VehAdapterEx.VehHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehAdapterEx.VehHolder holder, final int position) {
        final VehiculoExpandable data = content.get(position);
        holder.setData(data);
/**
 AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

 final int id = (int) System.currentTimeMillis();
 Intent intentAlarm = new Intent(context, MainActivity.class);
 PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intentAlarm,PendingIntent.FLAG_UPDATE_CURRENT);
 alarmManager.setExact(AlarmManager.RTC_WAKEUP,data.getFechaItv().getTime(),pendingIntent);
 **/
        LinearLayout LLCard = holder.itemView.findViewById(R.id.LLCard);
        LLCard.setOnClickListener(new View.OnClickListener() {
            boolean expanded = data.isExpanded();

            @Override
            public void onClick(View view) {
                data.setExpanded(!expanded);
                MainActivity.fabAddCar.show();
                notifyItemChanged(position);
            }
        });
        holder.expandable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditVehicleActivity.class);
                intent.putExtra("pos", position);
                intent.putExtra("idVeh", data.getIdVehiculo());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class VehHolder extends RecyclerView.ViewHolder {
        private TextView marca, modelo, matricula, motor, kilometraje, itv, neumaticos, aceite, revision, ensurance;
        LinearLayout expandable,itvLayout, tiresLayout,oilLayout,revisionLayout,ensuranceLayout;
        ImageView itvIcon, aceiteIcon, neumaticosIcon, revisionIcon, ensuranceIcon,imageView, alert;

        public VehHolder(@NonNull View itemView) {
            super(itemView);
            marca = itemView.findViewById(R.id.marca);
            modelo = itemView.findViewById(R.id.modelo);
            matricula = itemView.findViewById(R.id.matricula);
            motor = itemView.findViewById(R.id.motor);
            kilometraje = itemView.findViewById(R.id.kilometraje);
            itv = itemView.findViewById(R.id.itv);
            neumaticos = itemView.findViewById(R.id.neumaticos);
            aceite = itemView.findViewById(R.id.aceite);
            revision = itemView.findViewById(R.id.revision);
            expandable = itemView.findViewById(R.id.expandableTab);
            itvIcon = itemView.findViewById(R.id.itvIcon);
            neumaticosIcon = itemView.findViewById(R.id.neumaticosIcon);
            aceiteIcon = itemView.findViewById(R.id.aceiteIcon);
            revisionIcon = itemView.findViewById(R.id.revisionIcon);
            ensuranceIcon = itemView.findViewById(R.id.ensuranceIcon);
            ensurance = itemView.findViewById(R.id.ensurance);
            imageView = itemView.findViewById(R.id.cardImage);
            itvLayout = itemView.findViewById(R.id.itvLayout);
            tiresLayout = itemView.findViewById(R.id.tiresLayout);
            oilLayout = itemView.findViewById(R.id.oilLayout);
            revisionLayout = itemView.findViewById(R.id.revisionLayout);
            ensuranceLayout = itemView.findViewById(R.id.ensuranceLayout);
            alert = itemView.findViewById(R.id.notificationAlert);

        }

        public void setData(VehiculoExpandable data) {
            boolean expanded = data.isExpanded();
            boolean hasNotification = false;
/** filtros fallidos
            if (data.getKmVehicle()==0){
                kilometraje.setVisibility(View.GONE);
            }else{
                kilometraje.setText(data.getKmVehicle() + "km");
            }
            if (data.getItvDate()==null){
                itv.setVisibility(View.GONE);
            }else{
                itv.setText(Vehiculo.dateToString(data.getItvDate()));
            }
            if (data.getBrand()==null){
                marca.setVisibility(View.GONE);
            }else{
                marca.setText(data.getBrand());
            }
            if (data.getModel()==null){
                modelo.setVisibility(View.GONE);
            }else{
                modelo.setText(data.getModel());
            }
            if (data.getLicencePlate()==null){
                matricula.setVisibility(View.GONE);
            }else{
                matricula.setText(data.getLicencePlate());
            }
            if (data.getEngine()==null){
                motor.setVisibility(View.GONE);
            }else{
                motor.setText(data.getEngine());
            }
            if (data.getTiresDate()==null){
                neumaticos.setVisibility(View.GONE);
            }else{
                neumaticos.setText(Vehiculo.dateToString(data.getTiresDate()));
            }
            if (data.getOilDate()==null){
                aceite.setVisibility(View.GONE);
            }else{
                aceite.setText(Vehiculo.dateToString(data.getOilDate()));
            }
            if (data.getRevisionDate()==null){
                revision.setVisibility(View.GONE);
            }else{
                revision.setText(Vehiculo.dateToString(data.getRevisionDate()));
            }
            if (data.getInsuranceDate()==null){
                ensurance.setVisibility(View.GONE);
            }else{
                ensurance.setText(Vehiculo.dateToString(data.getInsuranceDate()));
            }
 **/
            kilometraje.setText(data.getKmVehicle() + "km");
            if (Vehiculo.dateToString(data.getItvDate()).equals("30/11/0002")) {
                itv.setText("");
                itvLayout.setVisibility(View.GONE);
            }else{
                itv.setText(Vehiculo.dateToString(data.getItvDate()));
                itvLayout.setVisibility(View.VISIBLE);
            }
            marca.setText(data.getBrand());
            modelo.setText(data.getModel());
            matricula.setText(data.getLicencePlate());
            motor.setText(data.getEngine());
            if (Vehiculo.dateToString(data.getTiresDate()).equals("30/11/0002")) {
                neumaticos.setText("");
                tiresLayout.setVisibility(View.GONE);
            }else{
                neumaticos.setText(Vehiculo.dateToString(data.getTiresDate()));
                tiresLayout.setVisibility(View.VISIBLE);
            }
            if (Vehiculo.dateToString(data.getOilDate()).equals("30/11/0002")) {
                aceite.setText("");
                oilLayout.setVisibility(View.GONE);
            }else{
                aceite.setText(Vehiculo.dateToString(data.getOilDate()));
                oilLayout.setVisibility(View.VISIBLE);
            }
            if (Vehiculo.dateToString(data.getRevisionDate()).equals("30/11/0002")) {
                revision.setText("");
                revisionLayout.setVisibility(View.GONE);
            }else{
                revision.setText(Vehiculo.dateToString(data.getRevisionDate()));
                revisionLayout.setVisibility(View.VISIBLE);
            }
            if (Vehiculo.dateToString(data.getInsuranceDate()).equals("30/11/0002")) {
                ensurance.setText("");
                ensuranceLayout.setVisibility(View.GONE);
            }else{
                ensurance.setText(Vehiculo.dateToString(data.getInsuranceDate()));
                ensuranceLayout.setVisibility(View.VISIBLE);
            }
            expandable.setVisibility(expanded ? View.VISIBLE : View.GONE);

            Log.e("imagen: ",data.getImage());
            if (data.getImage()!=null && data.getImage().length()>1) {
                Picasso.get().load("https://fixcarcesur.herokuapp.com" + data.getImage().substring(2)).placeholder(R.drawable.ic_car_placeholder).error(R.drawable.ic_car_placeholder).into(imageView);
                Log.e("imagen: ","https://fixcarcesur.herokuapp.com" + data.getImage().substring(2));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }



            Log.e("time", String.valueOf(data.getItvDate().getTime()));
            Log.e("tiempos","itv: "+data.getItvDate().getTime()+",actual: "+System.currentTimeMillis());
            if (data.getItvDate().getTime()-System.currentTimeMillis() < 432000000 && !Vehiculo.dateToString(data.getItvDate()).equals("30/11/0002")){
                itvIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo_itv_red));
                hasNotification = true;
            }else {
                itvIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo_itv));
            }
            if (data.getOilDate().getTime()-System.currentTimeMillis() < 432000000 && !Vehiculo.dateToString(data.getOilDate()).equals("30/11/0002")){
                aceiteIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.oil_red));
                hasNotification = true;
            }else {
                aceiteIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.oil));
            }
            if (data.getTiresDate().getTime()-System.currentTimeMillis() < 432000000 && !Vehiculo.dateToString(data.getTiresDate()).equals("30/11/0002")){
                neumaticosIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.car_tire_alert_red));
                hasNotification = true;
            }else {
                neumaticosIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.car_tire_alert));
            }
            if (data.getRevisionDate().getTime()-System.currentTimeMillis() < 432000000 && !Vehiculo.dateToString(data.getRevisionDate()).equals("30/11/0002")){
                revisionIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.car_cog_red));
                hasNotification = true;
            }else{
                revisionIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.car_cog));
            }
            if (data.getInsuranceDate().getTime()-System.currentTimeMillis() < 432000000 && !Vehiculo.dateToString(data.getInsuranceDate()).equals("30/11/0002")){
                ensuranceIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.shield_car_red));
                hasNotification = true;
            }else{
                ensuranceIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.shield_car));
            }
            if (hasNotification){
                alert.setVisibility(View.VISIBLE);
            }else {
                alert.setVisibility(View.GONE);
            }

        }

    }
}

