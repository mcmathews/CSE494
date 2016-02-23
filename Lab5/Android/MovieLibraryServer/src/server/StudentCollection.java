package ser423.student.server;

public interface StudentCollection {
   public boolean saveToJsonFile();
   public boolean resetFromJsonFile();
   public boolean add(Student stud);
   public boolean remove(String aName);
   public Student get(String aName);
   public String[] getNames();
}
