package com.example.works;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WorkListAdapter extends ArrayAdapter<Work> {

    private Activity context;
    private PopupWindow popupWindow;
    private final Context mContext;
    private List<Work> mWorkList;
    private AlertDialog editDialog;
    private WorkDAO workDAO;

    Button editButton;

    public WorkListAdapter(Context context, List<Work> workList) {
        super(context, 0, workList);
        mContext = context;
        mWorkList = workList;
        this.context = (Activity) context;
        workDAO = new WorkDAO(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        Work currentWork = mWorkList.get(position);

        TextView commentTextView = listItem.findViewById(R.id.comment_textview);
        commentTextView.setText(currentWork.getComment());

        Button deleteButton = listItem.findViewById(R.id.delbtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform delete operation here, e.g.:
                boolean success = new WorkDAO(mContext).deleteData(currentWork.getId());
                if (success) {
                    mWorkList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "Darbas pašalintas!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "Nepavyko pašalinti darbo!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final int positionPopup = position;

        Button editButton = listItem.findViewById(R.id.editbtn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPopup(positionPopup);
            }
        });




        return listItem;

    }

    private void editPopup(final int positionPopup) {
        //sukuriamas isokantis langas
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.edit_popup, context.findViewById(R.id.popup));
        popupWindow = new PopupWindow(layout, 1100, 540, true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);


        //susiejamas kodas su vaizdu
        EditText comment = layout.findViewById(R.id.edit_comment);
        Button save = layout.findViewById(R.id.edit_button);
        comment.setText(mWorkList.get(positionPopup).getComment());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment2 = comment.getText().toString();

                Work work = mWorkList.get(positionPopup);
                work.setComment(comment2);


                workDAO.updateData(work);

                //paredagavus atnaujiname vaizda
                mWorkList = (ArrayList<Work>) workDAO.readAll();
                notifyDataSetChanged();

                popupWindow.dismiss();
            }
        });

    }


    }

