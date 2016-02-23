package ser423.student.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;
import java.util.Arrays;

public class Student {
   public String name;
   public int studentid;
   public Vector<String> takes;

   Student(String name, int studentid, String[] courses){
      this.name = name;
      this.studentid = studentid;
      this.takes = new Vector<String>();
      this.takes.addAll(Arrays.asList(courses));
   }

   Student(String jsonStr){
      try{
         JSONObject jo = new JSONObject(jsonStr);
         name = jo.getString("name");
         studentid = jo.getInt("studentid");
         takes = new Vector<String>();
         JSONArray ja = jo.getJSONArray("takes");
         for (int i=0; i< ja.length(); i++){
            takes.add(ja.getString(i));
         }
      }catch (Exception ex){
         System.out.println(this.getClass().getSimpleName()+
                            ": error converting from json string");
      }
   }

   public Student(JSONObject jsonObj){
      try{
         System.out.println("constructor from json received: "+
                            jsonObj.toString());
         name = jsonObj.getString("name");
         studentid = jsonObj.getInt("studentid");
         takes = new Vector<String>();
         JSONArray ja = jsonObj.getJSONArray("takes");
         for (int i=0; i< ja.length(); i++){
            takes.add(ja.getString(i));
         }
      }catch(Exception ex){
         System.out.println(this.getClass().getSimpleName()+
                            ": error converting from json string");
      }
   }

   public JSONObject toJson(){
      JSONObject jo = new JSONObject();
      try{
         jo.put("name",name);
         jo.put("studentid",studentid);
         jo.put("takes",takes);
      }catch (Exception ex){
         System.out.println(this.getClass().getSimpleName()+
                            ": error converting to json");
      }
      return jo;
   }

   public String toJsonString(){
      String ret = "";
      try{
         ret = this.toJson().toString();
      }catch (Exception ex){
         System.out.println(this.getClass().getSimpleName()+
                            ": error converting to json string");
      }
      return ret;
   }
}
