package com.example.todolist;

import android.app.Application;
import android.util.Log;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private NoteDatabase noteDatabase;

    private MutableLiveData<Boolean> shoudCloseScreen = new MutableLiveData<>();

    public LiveData<Boolean> getShoudCloseScreen() {
        return shoudCloseScreen;
    }

    // Если много задач можно использовать коллекцию всех подписок
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public void saveNote(Note note) {
        Disposable disposable = noteDatabase.notesDao().add(note)
                .subscribeOn(Schedulers.io()) // для работы действия в фоновом потоке
                .observeOn(AndroidSchedulers.mainThread())// переключения на главный поток
                .subscribe(new Action() { // подписка
                    @Override
                    public void run() throws Throwable {
                        Log.d("AddNoteViewModel", "subscribe");
                        shoudCloseScreen.setValue(true); // установка LD в любом потоке
                    }
                }, new Consumer<Throwable>() { // обработка исключений
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("AddNoteViewModel", "Error saveNote()");
                    }
                });
        compositeDisposable.add(disposable);
    }

    // тут происходит очистка
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
