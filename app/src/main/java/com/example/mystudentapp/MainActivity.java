package com.example.mystudentapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mystudentapp.MODEL.Student;
import com.example.mystudentapp.viewModel.StudentViewModel;

public class MainActivity extends AppCompatActivity {

    private StudentViewModel studentViewModel;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvStatus = findViewById(R.id.tvStatus);
        TextView tvStudents = findViewById(R.id.tvStudents);

        studentViewModel = new ViewModelProvider(this)
                .get(StudentViewModel.class);

        tvStatus.setText("Memuat data mahasiswa...");

        studentViewModel.getStudents().observe(this, students -> {
            if (students == null || students.isEmpty()) {
                tvStudents.setText("Belum ada data mahasiswa.");
                return;
            }

            StringBuilder result = new StringBuilder();

            for (Student student : students) {
                Log.d(TAG, "Nama: " + student.getName());

                result.append("ID: ").append(student.getId())
                        .append("\nNIS: ").append(student.getNis())
                        .append("\nNama: ").append(student.getName())
                        .append("\nAlamat: ").append(student.getAddress())
                        .append("\n\n");
            }

            tvStudents.setText(result.toString());
            tvStatus.setText("Data berhasil dimuat");
        });

        studentViewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                tvStatus.setText(errorMessage);
                Toast.makeText(
                        this,
                        errorMessage,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        studentViewModel.loadStudents();
    }
}
