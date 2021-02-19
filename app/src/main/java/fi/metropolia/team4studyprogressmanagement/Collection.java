package fi.metropolia.team4studyprogressmanagement;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    private static final Collection collectionInstance = new Collection();

    public static Collection getInstance(){ return collectionInstance;}

    private List<Course> courses;

    private  Collection() {
        this.courses = new ArrayList<>();
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
    }
}
