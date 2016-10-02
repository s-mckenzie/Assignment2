package com.example.a1493236.assignment2;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1493236.assignment2.Model.DatabaseException;
import com.example.a1493236.assignment2.Model.Note;
import com.example.a1493236.assignment2.Model.NoteData;
import com.example.a1493236.assignment2.Model.NoteDatabaseHandler;
import com.example.a1493236.assignment2.Model.NoteTable;
import com.example.a1493236.assignment2.Model.SpinnerActivity;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView notes;
    private ArrayAdapter<Note> adapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // 1. Retrieve the ListView
        notes = (ListView) root.findViewById(R.id.note_ListView);

        // 2. Create and initialize adapter
        //adapter = new ArrayAdapter<>(this.getContext(), R.layout.list_item_contact, R.id.firstName_TextView);
        adapter = new NoteDataAdapter(this.getContext());

        // 3.  for today we have sample data
        final List<Note> data = NoteData.getData();
        adapter.addAll(data);

        // 4.  Connect the adapter to the ListView
        notes.setAdapter(adapter);

        // Toast the Contact on click
        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), NoteData.getNoteById(id).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //*************************** TESTING *************************

        NoteDatabaseHandler dbh = new NoteDatabaseHandler(getContext());
        NoteTable contactTable = dbh.getNoteTable();
        try {
            contactTable.create(NoteData.getNoteById(0));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        //*************************************************************

        Spinner spinner = (Spinner) root.findViewById(R.id.sort_Spinner);
        SpinnerActivity spinnerHandler = new SpinnerActivity(data, notes, adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sort_adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(sort_adapter);

        spinner.setOnItemSelectedListener(spinnerHandler);

        return root;
    }

    /**
     * Data Adapter for Notes
     */
    public class NoteDataAdapter extends ArrayAdapter<Note> {

        public NoteDataAdapter(Context context)
        {
            super(context, -1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // inflate or reuse previously inflated UI
            View root;
            if(convertView != null)
                root = convertView;
            else {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                root = inflater.inflate(R.layout.list_item_note, parent, false);
            }

            // Update UI components
            Note note = getItem(position);

            TextView title = (TextView) root.findViewById(R.id.title_TextView);
            TextView body = (TextView) root.findViewById(R.id.body_TextView);
            ImageView color = (ImageView) root.findViewById(R.id.color_ImageView);
            ImageView reminder = (ImageView) root.findViewById(R.id.reminder_ImageView);


            title.setText(note.getTitle());
            body.setText(note.getBody());
            color.setBackgroundColor(note.getCategory());

            if(note.isHasReminder()){
                reminder.setImageResource(android.R.drawable.ic_lock_idle_alarm);
            }
            else{
                reminder.setImageResource(android.R.drawable.ic_delete);        // Because crossed out alarm somehow doesnt exist in Drawable
            }

            return root;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId(); // use Contact IDs
        }
    }
}
