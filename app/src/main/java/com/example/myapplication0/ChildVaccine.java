package com.example.myapplication0;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DateSorter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;


public class ChildVaccine extends Fragment  {

    EditText editDatePicker, editTimePicker;
    Button butSubmut;
    private DatePicker datePicker;
    DatePickerDialog datePickerDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vaccine_child, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context;
        final DatabaseReference dbrefChildSet;
        dbrefChildSet = FirebaseDatabase.getInstance().getReference("Date_Info");
        final String uUid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        editDatePicker = view.findViewById(R.id.date_picker);
        editTimePicker = view.findViewById(R.id.time_picker);
        butSubmut = view.findViewById(R.id.submit_vaccine);
        SharedPreferences dateSP = getContext().getSharedPreferences("Date", 00);
        final SharedPreferences.Editor editorDate = dateSP.edit();


        editDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFrag = new DatePickerFragment();
                newFrag.show(getFragmentManager(),"Birth Date");

            }
        });

        editTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFrag = new TimePickerFragment();
                newFrag.show(getFragmentManager(),"Choose Time");



            }
        });

        butSubmut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // button clicks data stores.
                String date = editDatePicker.getText().toString();
                String time = editTimePicker.getText().toString();
                if(date.equals("")|| time.equals("")){
                    editDatePicker.setError("Insert Data");
                    editDatePicker.setError("Insert Data");
                }
                else {
                    editorDate.putString("Date", editDatePicker.getText().toString());
                    editorDate.putString("Time",editTimePicker.getText().toString());
                    editorDate.commit();
                    DataSetter dateSet = new DataSetter(editDatePicker.getText().toString(),editTimePicker.getText().toString());

                    dbrefChildSet.child(uUid).setValue(dateSet);

                    VaccineSchedule vaccineSchedule = new VaccineSchedule();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_content,vaccineSchedule);
                    transaction.addToBackStack("Schedule");
                    transaction.commit();
                }
            }
        });

    }
    public void addReminder(int statrYear, int startMonth, int startDay, int startHour, int startMinut, int endYear, int endMonth, int endDay, int endHour, int endMinuts){
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(statrYear, startMonth, startDay, startHour, startMinut);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(endYear, endMonth, endDay, endHour, endMinuts);
        long endMillis = endTime.getTimeInMillis();

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put(CalendarContract.Events.CALENDAR_ID, 1);
        eventValues.put(CalendarContract.Events.TITLE, "OCS");
        eventValues.put(CalendarContract.Events.DESCRIPTION, "Clinic App");
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, "Nasik");
        eventValues.put(CalendarContract.Events.DTSTART, startMillis);
        eventValues.put(CalendarContract.Events.DTEND, endMillis);

        //eventValues.put(Events.RRULE, "FREQ=DAILY;COUNT=2;UNTIL="+endMillis);
        eventValues.put("eventStatus", 1);
        eventValues.put("visibility", 3);
        eventValues.put("transparency", 0);
        eventValues.put(CalendarContract.Events.HAS_ALARM, 1);

        Uri eventUri = getActivity().getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);;
        long eventID = Long.parseLong(eventUri.getLastPathSegment());

        /***************** Event: Reminder(with alert) Adding reminder to event *******************/

        String reminderUriString = "content://com.android.calendar/reminders";

        ContentValues reminderValues = new ContentValues();

        reminderValues.put("event_id", eventID);
        reminderValues.put("minutes", 1);
        reminderValues.put("method", 1);

        Uri reminderUri = getActivity().getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
    }


    static public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


        public TimePickerFragment() {
            // Required empty public constructor
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker

            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            EditText editTime = (EditText) getActivity().findViewById(R.id.time_picker);
//
//            Calendar calendar  = Calendar.getInstance();
//            calendar.set(Calendar.YEAR,Calendar.DAY_OF_MONTH,Calendar.DATE,hourOfDay,minute,0);
//            setAlarm(calendar.getTimeInMillis());
            String amPm ;
            if(DateFormat.is24HourFormat(getActivity()) != true){
                if(hourOfDay>=12){
                    amPm = "PM";
                    String timePicked = (hourOfDay-12)+ ":" + minute + " "+ amPm;
                    //Toast.makeText(super.getContext(),timePicked,Toast.LENGTH_LONG).show();
                    editTime.setText(timePicked);
                }
                else {
                    amPm = "AM";
                    String timePicked = (hourOfDay)+ ":" + minute + " "+ amPm;
                    //Toast.makeText(super.getContext(),timePicked,Toast.LENGTH_LONG).show();
                    editTime.setText(timePicked);
                }
            }
            else {
                String timePicked = (hourOfDay)+ ":" + minute ;
//                Toast.makeText(super.getContext(),timePicked,Toast.LENGTH_LONG).show();
                editTime.setText(timePicked);

            }

        }

        private void setAlarm(long timeInMillis) {
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intAlarm = new Intent(getContext(),MyAlarm.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),1,intAlarm,0);
            alarmManager.setRepeating(AlarmManager.RTC, timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);

            Toast.makeText(getContext(),"ALarm is set",Toast.LENGTH_SHORT).show();
        }
    }

    static public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //Use the current date as the default date in the date picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //Do something with the date chosen by the user
            EditText tv = (EditText) getActivity().findViewById(R.id.date_picker);


            String stringOfDate = day + "/" + (month+1) + "/" + year;
            tv.setText(stringOfDate);
        }
    }
}

