package com.example.mystudentapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mystudentapp.MODEL.Student;
import com.example.mystudentapp.Respository.StudentRepository;

import java.util.List;

public class StudentViewModel extends ViewModel {

    private final StudentRepository repository =
            new StudentRepository();

    private final MutableLiveData<List<Student>> students =
            new MutableLiveData<>();

    private final MutableLiveData<String> error =
            new MutableLiveData<>();

    public MutableLiveData<List<Student>> getStudents() {
        return students;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void loadStudents() {
        repository.getStudents(students, error);
    }
}
