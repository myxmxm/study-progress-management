package fi.metropolia.team4studyprogressmanagement;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created abstract class CourseDatabase to hold the database.
 */

@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase {
//create singleton
    private static CourseDatabase instance;
    public static synchronized CourseDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),CourseDatabase.class,"CourseDatabase")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
//abstract method of DAO class
    public abstract CourseDao getCourseDao();
}
