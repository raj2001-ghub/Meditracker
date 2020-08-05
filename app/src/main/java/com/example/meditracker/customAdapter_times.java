package com.example.meditracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class customAdapter_times extends RecyclerView.Adapter<customAdapter_times.customMyViewHolder>
        {
            private static Context context;
            private ArrayList medicine_namesc,time_listc,date_listc;
            private Button cancel;
            public customAdapter_times(Context context,ArrayList medicine_names,ArrayList time_list,ArrayList date_list){
                this.context=context;
                this.medicine_namesc=medicine_names;
                this.time_listc=time_list;
                this.date_listc=date_list;
            }

            @NonNull
            @Override
            public customAdapter_times.customMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater=LayoutInflater.from(context);
                View view=inflater.inflate(R.layout.rows_design_time,parent,false);                            ///////this line connects the row_design with the custom Adapter file..
                return new customAdapter_times.customMyViewHolder(view);                          ///////this line connects the row_design with the custom Adapter file..
            }

            @Override
            public void onBindViewHolder(@NonNull customAdapter_times.customMyViewHolder holder, int position) {
                holder.m.setText(String.valueOf(medicine_namesc.get(position)));    ///// this will be the IDS OF THE ITEMS IN row_design.xml
                holder.t.setText(String.valueOf(time_listc.get(position)));           //////////one important THING WHICH WE HAVE MISSED OUT IS THE SETTING UP OF DATE NOTIFICATION ADDED IN
                holder.d.setText((String.valueOf(date_listc.get(position))));
            }
            ////////////////////////////////////////////////////////////DATABASE
            @Override
            public int getItemCount() {
                return medicine_namesc.size();
            }
            public class customMyViewHolder extends RecyclerView.ViewHolder {

                TextView m,d,t;
                public customMyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    d = itemView.findViewById(R.id.date_notif_added);        //// we can do our visual operations on this..
                    m = itemView.findViewById(R.id.medname_notif_added);
                    t = itemView.findViewById(R.id.time_notif_added);

                    cancel=itemView.findViewById(R.id.buttonc);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int pos=getAdapterPosition();
                            cancelAlarms(pos+1);
                        }
                    });
                }

                private void cancelAlarms(int position) {
                    notificationsdatabase db=new notificationsdatabase(context);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);                     //////first 4 lines are useful for cancelling the alarm
                    Intent intent = new Intent(context, AlertReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, position, intent, 0);       /////CANCELLING THE REQUEST IS VERY EASY
                    alarmManager.cancel(pendingIntent);
                     String  s = "All Alarms of this medicine are cancelled";
                    t.setText("All Alarms of this medicine are cancelled");           //////this information has to go to the database too that all alarms are cancelled..
                    db.updatealarmtext(position,s);                      //////this is completely wrong ..we are supposed to be sending the actual position where cancel is pressed..
                                                                            //////it shouldnot be db.countrows()...
                }
            }

        }
