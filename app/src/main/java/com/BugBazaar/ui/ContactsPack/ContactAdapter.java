package com.BugBazaar.ui.ContactsPack;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.BugBazaar.R;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contacts> contactsList;

    public ContactAdapter(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contacts contact = contactsList.get(position);

        holder.contactName.setText(contact.getName());
        holder.contactNumber.setText(contact.getPhoneNumber());

        // Set a background color or other UI indication for selected contacts
        if (contact.isSelected()) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.selected_contact_bg_color));

            // Use a Handler to delay changing the background color back to TRANSPARENT
            new Handler().postDelayed(() -> {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }, 2000); // 5000 milliseconds (5 seconds)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        // Handle item click to toggle contact selection
        holder.itemView.setOnClickListener(v -> {
            // Update the selected contact
            contact.setSelected(!contact.isSelected());
            // Reset the background color of all contacts
            for (Contacts c : contactsList) {
                if (c != contact) {
                    c.setSelected(false);
                }
            }
            notifyDataSetChanged();

            // Check if the contact is selected
            if (contact.isSelected()) {
                // Open the SMS app with the selected contact and predefined SMS body
                openSmsApp(holder.itemView.getContext(), contact.getPhoneNumber(), "Hey there, I'm using BugBazaar for all of my bug needs.\n" +
                        "Check out our new application and you will never have to go back to any other shopping app. \n\n" +
                        "BugBazaar!! for all your vulnerabilty needs!!");

            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    private void openSmsApp(android.content.Context context, String phoneNumber, String smsBody) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber)); // Set the recipient phone number
        intent.putExtra("sms_body", smsBody); // Set the SMS body

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent); // Start the SMS app
        } else {
            // Handle the case where there is no SMS app installed
            Toast.makeText(context, "No SMS app found.", Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName;
        public TextView contactNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contactName);
            contactNumber = itemView.findViewById(R.id.contactNumber);
        }
    }
}
