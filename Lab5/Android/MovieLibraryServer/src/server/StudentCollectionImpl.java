package ser423.student.server;

import java.util.Hashtable;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.Enumeration;

import org.json.JSONObject;
import org.json.JSONTokener;

public class StudentCollectionImpl implements StudentCollection {

   public Hashtable<String,Student> students;
   private static final boolean debugOn = false;
   private static final String studentJsonFileName = "movies.json";

   public StudentCollectionImpl() {
      debug("creating a new student collection");
      students = new Hashtable<String,Student>();
      this.resetFromJsonFile();
   }

   private void debug(String message) {
      if (debugOn)
         System.out.println("debug: "+message);
   }

   public boolean resetFromJsonFile(){
      boolean ret = true;
      try{
         students.clear();
         String fileName = studentJsonFileName;
         File f = new File(fileName);
         FileInputStream is = new FileInputStream(f);
         JSONObject studentMap = new JSONObject(new JSONTokener(is));
         Iterator<String> it = studentMap.keys();
         while (it.hasNext()){
            String mType = it.next();
            JSONObject studentJson = studentMap.optJSONObject(mType);
            Student stud = new Student(studentJson);
            students.put(stud.name, stud);
            debug("added "+stud.name+" : "+stud.toJsonString()+
                  "\nstudents.size() is: " + students.size());
         }
      }catch (Exception ex){
         System.out.println("Exception reading json file: "+ex.getMessage());
         ret = false;
      }
      return ret;
   }

   public boolean saveToJsonFile(){
      boolean ret = true;
      try {
         String jsonStr;
         JSONObject obj = new JSONObject();
         for (Enumeration<String> e = students.keys(); e.hasMoreElements();){
            Student aStud = students.get((String)e.nextElement());
            obj.put(aStud.name,aStud.toJson());
         }
         PrintWriter out = new PrintWriter(studentJsonFileName);
         out.println(obj.toString(2));
         out.close();
      }catch(Exception ex){
         ret = false;
      }
      return ret;
   }
   
   public boolean add(Student aStud){
      boolean ret = true;
      debug("adding student named: "+((aStud==null)?"unknown":aStud.name));
      try{
         students.put(aStud.name,aStud);
      }catch(Exception ex){
         ret = false;
      }
      return ret;
   }

   public boolean remove(String aName){
      debug("removing student named: "+aName);
      return ((students.remove(aName)==null)?false:true);
   }

   public String[] getNames(){
      String[] ret = {};
      debug("getting "+students.size()+" student names.");
      if(students.size()>0){
         ret = (String[])(students.keySet()).toArray(new String[0]);
      }
      return ret;
   }
   
   public Student get(String aName){
      Student ret = new Student("unknown",0,new String[]{"empty"});
      Student aStud = students.get(aName);
      if (aStud != null) {
         ret = aStud;
      }
      return ret;
   }

   
}
