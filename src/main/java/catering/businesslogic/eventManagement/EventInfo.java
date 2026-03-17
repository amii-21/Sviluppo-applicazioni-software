package catering.businesslogic.eventManagement;
import java.util.ArrayList;
import java.util.List;
import catering.persistence.*;
import java.sql.*;

public class EventInfo{
    private int numGuests; 
    private int eventId;
    private ArrayList<String> locations = new ArrayList<String>();
    private ArrayList<String> typeOfService = new ArrayList<String>();
    private StaffEventInfo currentStaffEventInfo;
    private ArrayList<Notes> notes = new ArrayList<Notes>();
    private ArrayList<Notes> description = new ArrayList<Notes>();
    private ArrayList<String> info = new ArrayList<String>();

   
    public EventInfo(int eventId,int numGuests, List<String> locations, List<String> typeOfService,List<Notes> notes, List<String> info){
        this.info.addAll(info);
        this.notes.addAll(notes);
        this.locations.addAll(locations);
        this.typeOfService.addAll(typeOfService);
        this.numGuests = numGuests;
        this.eventId = eventId;
    }
    public EventInfo(){
    }

    //Notes
    public List<Notes> getNotes(){
        return this.notes; 
    }
    public Notes getNote(int index){
        return this.notes.get(index); 
    }
    public void addNotes(Notes n){
        this.notes.add(n);
    }
    public void setNotes(int index, Notes n){
        this.notes.set(index, n);
    }
    public void deleteNotes(int index){
        this.notes.remove(index);
    }   	 
    //misc
    public void setNumGuests(int i ){
        this.numGuests = i;
    }
    public int getNumGuests(){
        return numGuests;
    }
    
    public List<String> getLocations() {
        return locations;
    }
    public String getLocation(int index){
        return locations.get(index);
    }
    public void setLocations(int index, String s){
        locations.set(index, s);
    }
    public void addLocations(String s){
        locations.add(s);
    }
    public void deleteLocations(int index){
        locations.remove(index);
    }

    public List<String> getInfo() {
        return info;
    }
    public String getInfo(int index){
        return info.get(index);
    }
    public void setInfo(int index, String s){
        info.set(index, s);
    }
    public void addInfo(String s){
        info.add(s);
    }
    public void deleteInfo(int index){
        info.remove(index);
    }
    //service
    public List<String> getTypeOfServices(){
        return typeOfService;
    }
    public String getTypeOfService(int index){
        return typeOfService.get(index);
    }
    public void setTypeOfService(int index, String service){
        this.typeOfService.set(index, service);   
    }
    public void addTypeOfService(String service){
        this.typeOfService.add(service);
    }
    public void deleteTypeOfService(int index){
        this.typeOfService.remove(index);        
    }	 
    //staff
    public StaffEventInfo getStaffEventInfo(){
        return currentStaffEventInfo;
    }


    /** DATABASE OPERATIONS */
    
    public static EventInfo load(int id){
        String query = "SELECT event_id, numGuests FROM EventInfos WHERE event_id = ?";
        EventInfo ret = new EventInfo();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                ret.eventId = rs.getInt("event_id");
                ret.numGuests = rs.getInt("numGuests");
                ret.locations = EventInfo.loadLocations(id);
                ret.typeOfService = EventInfo.loadTypeOfService(id);
                ret.notes = EventInfo.loadNotes(id);
                ret.info = EventInfo.loadInfo(id);
                ret.currentStaffEventInfo = StaffEventInfo.load(id);
            }
        }, id);

        return ret;
    }

    public static List<EventInfo> loadAll() {
    String query = "SELECT event_id, numGuests FROM EventInfos";
    List<EventInfo> results = new ArrayList<>();

    PersistenceManager.executeQuery(query, new ResultHandler() {
        public void handle(ResultSet rs) throws SQLException {
            do{
                EventInfo info = new EventInfo();
                int id = rs.getInt("event_id");
                info.eventId = id;
                info.numGuests = rs.getInt("numGuests");

                // Carica campi secondari
                info.locations = EventInfo.loadLocations(id);
                info.typeOfService = EventInfo.loadTypeOfService(id);
                info.notes = EventInfo.loadNotes(id);
                info.info = EventInfo.loadInfo(id);
                info.currentStaffEventInfo = StaffEventInfo.load(id);

                results.add(info);
            }while (rs.next());
        }
    });

    return results;
}

    public static ArrayList<String> loadLocations(int id) {
        String query = "SELECT event_id, location_name FROM EventInfoLocations WHERE event_id = ?";
        ArrayList<String> ret = new ArrayList<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            public void handle(ResultSet rs) throws SQLException {
                do{
                    ret.add(rs.getString("location_name"));
                }while(rs.next());
            }
        }, id);

        return ret;
    }
    public static ArrayList<String> loadTypeOfService(int id){
        String query = "SELECT event_id, service_type FROM EventInfoTypeOfServices WHERE event_id = ?";
        ArrayList<String> ret = new ArrayList<>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                ret.add(rs.getString("service_type"));
            }while(rs.next());
            }
        }, id);

        return ret;
    }
    public static ArrayList<Notes> loadNotes(int id){
        String query = "SELECT event_id, title, body FROM EventInfoNotes WHERE event_id = ?";
        ArrayList<Notes> ret = new ArrayList<>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                ret.add(new Notes(rs.getString("title"),rs.getString("body")));
            }while(rs.next());
            }
        }, id);

        return ret;
    }
   public static ArrayList<String> loadInfo(int id) {
        String query = "SELECT event_id, info_name FROM EventInfoInfo WHERE event_id = ?";
        ArrayList<String> ret = new ArrayList<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            public void handle(ResultSet rs) throws SQLException {
                do{
                    ret.add(rs.getString("info_name"));
                }while(rs.next());
            }
        }, id);

        return ret;
    }

    public static void updateNoteAdded(EventInfo eventInfo, Notes n){
        String query = "INSERT INTO EventInfoNotes(event_id, title, body) VALUES (?, ?, ?)";
        PersistenceManager.executeUpdate(query, eventInfo.eventId,n.getTitle(),n.getText());
    }
    public static void updateNoteModified(EventInfo eventInfo, int index, Notes n){
        String query = "UPDATE EventInfoNotes  SET `title` = ?, `body` = ?  WHERE `event_id` = ? AND `title` = ? AND `body` = ?";
        Notes toUpdate = eventInfo.getNote(index);
        PersistenceManager.executeUpdate(query,n.getTitle(),n.getText(),eventInfo.eventId,toUpdate.getTitle(),toUpdate.getText());
    }
    public static void updateNoteDeleted(EventInfo eventInfo, int index){
        String query = "DELETE FROM EventInfoNotes WHERE `event_id` = ? AND `title` = ? AND `body` = ?";
        Notes toDelete = eventInfo.getNote(index);
        PersistenceManager.executeUpdate(query,eventInfo.eventId,toDelete.getTitle(),toDelete.getText());
        
    }

    public static void updateNumGuestsModified(EventInfo eventInfo, int i){
        String query = "UPDATE EventInfos SET `numGuests` = ? WHERE `event_id` = ?";
        PersistenceManager.executeUpdate(query,i,eventInfo.eventId);
    }

    public static void updateLocationAdded(EventInfo eventInfo, String s){
        String query = "INSERT INTO EventInfoLocations(event_id, location_name) VALUES (?, ?)";
        PersistenceManager.executeUpdate(query,eventInfo.eventId,s);
    }
    public static void updateLocationModified(EventInfo eventInfo, int index, String s){
        String query = "UPDATE EventInfoLocations SET `location` = ? WHERE `event_id` = ? AND `location` = ?";
        String toUpdate = eventInfo.getLocation(index);
        PersistenceManager.executeUpdate(query,s,eventInfo.eventId,toUpdate);
    }
    public static void updateLocationDeleted(EventInfo eventInfo, int index){
        String query = "DELETE FROM EventInfoLocations WHERE `event_id` = ? AND `location` = ?";
        String toDelete = eventInfo.getLocation(index);
        PersistenceManager.executeUpdate(query,eventInfo.eventId,toDelete);
        
    }

    public static void updateInfoAdded(EventInfo eventInfo, String s){
        String query = "INSERT INTO EventInfoInfo(event_id, info_name) VALUES (?, ?)";
        PersistenceManager.executeUpdate(query,eventInfo.eventId,s);
    }
    public static void updateInfoModified(EventInfo eventInfo, int index, String s){
        String query = "UPDATE EventInfoInfo SET `info_name` = ? WHERE `event_id` = ? AND `info_name` = ?";
        String toUpdate = eventInfo.getInfo(index);
        PersistenceManager.executeUpdate(query,s,eventInfo.eventId,toUpdate);
    }
    public static void updateInfoDeleted(EventInfo eventInfo, int index){
        String query = "DELETE FROM EventInfoInfo WHERE `event_id` = ? AND `info_name` = ?";
        String toDelete = eventInfo.getInfo(index);
        PersistenceManager.executeUpdate(query,eventInfo.eventId,toDelete);
        
    }

    public static void updateTypeOfServiceAdded(EventInfo eventInfo, String s){
        String query = "INSERT INTO EventInfoTypeOfServices(event_id, service_type) VALUES (?, ?)";
        PersistenceManager.executeUpdate(query,eventInfo.eventId,s);
    }
    public static void updateTypeOfServiceModified(EventInfo eventInfo, int index, String s){
        String query = "UPDATE EventInfoTypeOfServices SET `service_type` = ? WHERE `event_id` = ? AND `service_type` = ?";
        String toUpdate = eventInfo.getTypeOfService(index);
        PersistenceManager.executeUpdate(query,s,eventInfo.eventId,toUpdate);
    }
    public static void updateTypeOfServiceDeleted(EventInfo eventInfo, int index){
        String query = "DELETE FROM EventInfoTypeOfServices WHERE `event_id` = ? AND `service_type` = ?";
        String toDelete = eventInfo.getTypeOfService(index);
        PersistenceManager.executeUpdate(query,eventInfo.eventId,toDelete);
        
    }
}