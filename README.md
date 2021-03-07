Android project with Git version control integration and remote git repository

Team 4 Study Progress Management

Content of app and user guide:

1. For the first time user open the app, user will be asked to complete registration process. 
During the registration process user need to create username, password, and give general information
about their study, such as school name, major, and total credits of study program. After the successful
registration, user will be ask to login by using the same information they provided in registration process.

2. After user successfully login to their account, user will see a page with 3 tabs on top of that page.
   
   1. The first tab is Edit. The first main purpose of this tab is to allow user to edit the information 
   they have provided during registration process, such as username, password. The second purpose of 
   this fragment is to give user accessibility to edit their course list,when user click the 
   corresponding button, they will be taken to course list activity.
       
       ---from this page user are able to edit their course list, such as insert new course
          or remove all courses by on click. all course will be display as singleton listView
          when they click one list item, they will be taken to another page to view more
          detailed information about specific course they have chosen.
              
             ---Firstly, this page will shows detailed information about each course inserted by user from
                previous page,Secondly, users are able to re-edit every information about the course except course Id
                Thirdly, users can click delete button to delete any course that they don't want to be stored
                on this app.

   2.  The second tab is Search. From this fragment we give user several option to search their course related data,
       for instance, user will be able to find the course name by specific grade, credit, and semester.
       
   3.  The third tab is Statistic. This tab will shows 3 critical statistic data such as study completion
       rate, total credits, and GPA, and there is one button under each statistic data, when user click each
       button, user will get more detailed information about corresponding statistic data in a diagram form.
       
       1 diagram--It is a pie chart about user's study progress, which indicates how many
                  percent dose each course account for the total program study, and it also shows how
                  many percent of study has not yet completed.
            
       2 diagram--It is a column chart about user's total credits in each semester
                  and an overall credits.
                 
       3 diagram--It is another column chart which shows user's GPA in each semester and the overall GPA.