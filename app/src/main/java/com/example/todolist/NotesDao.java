package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface NotesDao {

    // Получение данных из БД
    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    // Добавление заметки в БД
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable add(Note note);

    // Удаление заметки из БД по id
    @Query("DELETE FROM notes WHERE id = :id")
    Completable remove(int id);
}
