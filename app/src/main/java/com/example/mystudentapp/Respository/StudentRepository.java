package com.example.mystudentapp.Respository;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.example.mystudentapp.MODEL.Student;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudentRepository {

    private static final String API_URL =
            "http://192.168.90.247:8001/api/students";

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    private final Handler mainHandler =
            new Handler(Looper.getMainLooper());

    public void getStudents(
            MutableLiveData<List<Student>> students,
            MutableLiveData<String> error
    ) {
        executor.execute(() -> {
            HttpURLConnection connection = null;

            try {
                URL url = new URL(API_URL);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                int responseCode = connection.getResponseCode();

                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new Exception("HTTP Error: " + responseCode);
                }

                InputStream inputStream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream)
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                JSONObject jsonObject =
                        new JSONObject(response.toString());

                JSONArray dataArray =
                        jsonObject.getJSONArray("data");

                List<Student> studentList = new ArrayList<>();

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject item = dataArray.getJSONObject(i);

                    Student student = new Student(
                            item.getInt("id"),
                            item.getString("nis"),
                            item.getString("name"),
                            item.optString("address", "")
                    );

                    studentList.add(student);
                }

                mainHandler.post(() ->
                        students.setValue(studentList)
                );

            } catch (Exception e) {
                mainHandler.post(() ->
                        error.setValue(
                                "Gagal mengambil data: " + e.getMessage()
                        )
                );
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }
}
