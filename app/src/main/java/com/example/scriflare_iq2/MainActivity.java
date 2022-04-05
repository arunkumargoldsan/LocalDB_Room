package com.example.scriflare_iq2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.scriflare_iq2.room_db.UserModal;
import com.example.scriflare_iq2.room_db.UserRVAdapter;
import com.example.scriflare_iq2.room_db.ViewModal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView userRv;
    private static final int ADD_USER_REQ = 1;
    private static final int EDIT_USER_REQ = 2;
    private ViewModal viewModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userRv = (RecyclerView) findViewById(R.id.rv_user_list);
        ((FloatingActionButton) findViewById(R.id.fab_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                startActivityForResult(intent, ADD_USER_REQ);
            }
        });

        userRv.setLayoutManager(new LinearLayoutManager(this));
        userRv.setHasFixedSize(true);

        final UserRVAdapter adapter = new UserRVAdapter();

        userRv.setAdapter(adapter);

        viewModal = ViewModelProviders.of(this).get(ViewModal.class);

        viewModal.getAllUsers().observe(this, new Observer<List<UserModal>>() {
            @Override
            public void onChanged(List<UserModal> userModals) {
                adapter.submitList(userModals);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModal.delete(adapter.getUserAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,  "User Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(userRv);

        adapter.setOnItemClickListener(new UserRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserModal modal) {
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                intent.putExtra(NewUserActivity.EXTRA_ID, modal.getId());
                intent.putExtra(NewUserActivity.EXTRA_NAME, modal.getName());
                intent.putExtra(NewUserActivity.EXTRA_EMAIL, modal.getEmail());
                intent.putExtra(NewUserActivity.EXTRA_MOBILE, modal.getMobile());
                intent.putExtra(NewUserActivity.EXTRA_GENDER, modal.getGender());

                startActivityForResult(intent, EDIT_USER_REQ);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_USER_REQ && resultCode == RESULT_OK) {
            String name = data.getStringExtra(NewUserActivity.EXTRA_NAME);
            String email = data.getStringExtra(NewUserActivity.EXTRA_EMAIL);
            String mobile = data.getStringExtra(NewUserActivity.EXTRA_MOBILE);
            String gender = data.getStringExtra(NewUserActivity.EXTRA_GENDER);

            UserModal modal = new UserModal(name, email, mobile, gender);
            viewModal.insert(modal);
            Toast.makeText(MainActivity.this,  "User Saved !!!", Toast.LENGTH_SHORT).show();
        } else if(requestCode == EDIT_USER_REQ && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewUserActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(MainActivity.this, "Course can't be updated !!!", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(NewUserActivity.EXTRA_NAME);
            String email = data.getStringExtra(NewUserActivity.EXTRA_EMAIL);
            String mobile = data.getStringExtra(NewUserActivity.EXTRA_MOBILE);
            String gender = data.getStringExtra(NewUserActivity.EXTRA_GENDER);

            UserModal modal = new UserModal(name, email, mobile, gender);
            modal.setId(id);
            viewModal.update(modal);
            Toast.makeText(MainActivity.this, "User Updated !!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "User not saved", Toast.LENGTH_SHORT).show();
        }
    }
}