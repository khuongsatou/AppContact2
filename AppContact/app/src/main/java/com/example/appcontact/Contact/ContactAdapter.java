package com.example.appcontact.Contact;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcontact.Model.SqliteRender;
import com.example.appcontact.R;

import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<Contact> contactList;
    private MyOnClickListener myOnClickListener;
    private Context context;
    private  ContactAdapter contactAdapter;
    private SqliteRender sqliteRender;

    public void setContactAdapter(ContactAdapter contactAdapter) {
        this.contactAdapter = contactAdapter;
    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public ContactAdapter(Context context,List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_list_contact,parent,false);

        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactHolder holder, final int position) {
        holder.tvNameContact.setText(contactList.get(position).getName());
        holder.tvNumberContact.setText(contactList.get(position).getNumber());

        holder.tvNameContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog( position);
            }
        });
    }

    private void ShowDialog(final int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_items_show);

        final EditText edtName =  dialog.findViewById(R.id.edtContactName);
        final EditText edtNumber = dialog.findViewById(R.id.edtContactNumber);
        Button btnSave =  dialog.findViewById(R.id.btnSave);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);

        edtName.setText(contactList.get(position).getName());
        edtNumber.setText(contactList.get(position).getNumber());
        sqliteRender = new SqliteRender(context);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String number = edtNumber.getText().toString();

                sqliteRender.Update(new Contact(name,number),position);

                //contactList.set(position,new Contact(name,number));
                contactAdapter.notifyDataSetChanged();
                Toast.makeText(context,"Save Success",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //contactList.remove(position);
                sqliteRender.Delete(position);

                contactAdapter.notifyDataSetChanged();
                Toast.makeText(context,"Delete Success",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactHolder extends  RecyclerView.ViewHolder {
        private TextView tvNameContact,tvNumberContact;
        public ContactHolder(@NonNull View itemView) {
            super(itemView);
             tvNameContact = itemView.findViewById(R.id.tvNameContact);
             tvNumberContact =  itemView.findViewById(R.id.tvNumberPhone);
        }
    }
}
