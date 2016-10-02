package com.example.a1493236.assignment2.Model;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a1493236.assignment2.MainActivityFragment;

import java.util.Collections;
import java.util.List;
import java.util.Comparator;

/**
 * Created by 1493236 on 2016-09-29.
 */
public class SpinnerActivity implements AdapterView.OnItemSelectedListener {

    private List<Note> data;
    private ListView notes;
    private ArrayAdapter<Note> adapter;

    public SpinnerActivity(List<Note> data, ListView notes, ArrayAdapter<Note> adapter){
        this.data = data;
        this.notes = notes;
        this.adapter = adapter;
        notes.setAdapter(this.adapter);         // Set adapter to ListView
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        switch(pos){
            // Title (String)
            case 0: Collections.sort(this.data, new Comparator<Note>(){
                        @Override
                        public int compare(Note o1, Note o2) {
                            return o1.getTitle().compareTo(o2.getTitle());
                        }
                    });
                    break;
            // Creation Date (Date)
            case 1: Collections.sort(this.data, new Comparator<Note>(){
                        @Override
                        public int compare(Note o1, Note o2) {
                            return o1.getCreated().compareTo(o2.getCreated());
                        }
                    });
                    break;
            // Category (int)
            case 2: Collections.sort(this.data, new Comparator<Note>(){
                        @Override
                        public int compare(Note o1, Note o2) {
                            return o1.getCategory() - o2.getCategory();
                        }
                    });
                    break;
            // Reminder (boolean)
            case 3: Collections.sort(this.data, new Comparator<Note>(){
                        @TargetApi(Build.VERSION_CODES.KITKAT)
                        @Override
                        public int compare(Note o1, Note o2) {
                            return Boolean.compare(o2.isHasReminder(), o1.isHasReminder());
                        }
                    });
                    break;
        }

        // Test onItemSelected and check proper positions
        Toast.makeText(parent.getContext(),
                "Position : " + pos + " | OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();

        adapter.clear();
        adapter.addAll(data);
        //adapter.notifyDataSetChanged();
        notes.setAdapter(adapter);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
