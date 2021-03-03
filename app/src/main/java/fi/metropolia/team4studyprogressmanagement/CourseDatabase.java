package fi.metropolia.team4studyprogressmanagement;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase {

    private static CourseDatabase instance;
    public static synchronized CourseDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),CourseDatabase.class,"CourseDatabase")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


    public abstract CourseDao getCourseDao();

    /*private static final CourseDatabase COURSE_DATABASE_INSTANCE = new CourseDatabase();

    public static CourseDatabase getInstance(){ return COURSE_DATABASE_INSTANCE;}

    private List<Course> courses;

    private CourseDatabase() {
        this.courses = new ArrayList<>();
        this.courses.add(new Course("Math",4,3,2,"Hard"));
        this.courses.add(new Course("Physics",2,3,1,"Easy"));
        this.courses.add(new Course("IT",5,10,3,"Hard"));
    }

    public List<Course> getAllCourse(){
        return this.courses;}

    public Course getCourse (int indexOfCourse){
        return this.courses.get(indexOfCourse);
    }

    public void addCourse (Course course){
        this.courses.add(course);
    }

    public void removeCourse (int indexOfCourse ){
        this.courses.remove(indexOfCourse);
    }*/
}
