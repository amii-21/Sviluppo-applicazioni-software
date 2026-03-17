package catering.businesslogic.staffManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

public class ContactedStaff{

    private Contact contact;
    private LocalDate requestDate;
    private LocalDate endDate;
    private String position;
    private int eventId;
    
    private ContactedStaff(int eventId, Contact contact,LocalDate starDate,LocalDate endDate,String position){
        this.position = position;
        this.contact = contact;
        this.endDate = endDate;
        this.requestDate = LocalDate.now();
        this.eventId = eventId;
    }
    
    public ContactedStaff(int eventId,Contact contact,LocalDate endDate,String position){
        this.position = position;
        this.contact = contact;
        this.endDate = endDate;
        this.requestDate = LocalDate.now();
        this.eventId = eventId;
    }

    public Contact getContact(){
        return this.contact;
    }

    public int getContactId(){
        return this.contact.getContactId();
    }

    public int getEventId(){
        return eventId;
    }

    public String getPosition(){
        return this.position;
    }
    
    public void setPosition(String position){
        this.position = position;
    }

    public LocalDate getRequestDate(){
        return this.requestDate;
    }

    public LocalDate getEndDate(){
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }


    /** DATABASE OPERATIONS */
    public static ArrayList<ContactedStaff> load(int id){
        String query = "SELECT `date_requested`, `date_end`, `position`, `event_id`, `contact_id` FROM ContactedStaffs WHERE `event_id` = ?";
        ArrayList<ContactedStaff> ret = new ArrayList<ContactedStaff>();
        
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                    ret.add(new ContactedStaff(rs.getInt("event_id"),Contact.load(rs.getInt("contact_id")),LocalDate.parse(rs.getString("date_requested")),LocalDate.parse(rs.getString("date_end")),rs.getString("position")));
                }while(rs.next());
            }
        }, id);

        return ret;
    }
    
}