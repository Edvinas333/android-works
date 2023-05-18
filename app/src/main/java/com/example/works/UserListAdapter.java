package com.example.works;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {

    private final Activity context;

    private final Context mContext;

    private List<User> mUserList;

    private UserDAO userDAO;


    public UserListAdapter(Context context, List<User> userList) {
        super(context, 0, userList);
        mContext = context;
        mUserList = userList;
        this.context = (Activity) context;
        userDAO = new UserDAO(context);

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_user, parent, false);
        }

        User currentUser = mUserList.get(position);

        TextView commentTextView = listItem.findViewById(R.id.user_textview);
        commentTextView.setText(currentUser.getUsername());

        TextView adminTextView = listItem.findViewById(R.id.admin_textview);
        TextView userTextView = listItem.findViewById(R.id.textview_for_user);

        TextView emailTextView = listItem.findViewById(R.id.textview_for_email);
        emailTextView.setText(currentUser.getEmail());

        if (currentUser.isAdmin()) {
            adminTextView.setText("Administratorius");
            notifyDataSetChanged();
        } else {
            userTextView.setText("Vartotojas");

            Button deleteButton = listItem.findViewById(R.id.deluserbtn);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        boolean success = new UserDAO(mContext).deleteUserData(currentUser.getUsername());
                        if (success) {
                            mUserList.remove(position);
                            Toast.makeText(v.getContext(), "Vartotojas pašalintas!", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(v.getContext(), "Nepavyko pašalinti vartotojo.", Toast.LENGTH_SHORT).show();
                        }
                }
            });

        }


        return listItem;

    }
}
