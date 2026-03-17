package catering.businesslogic.holidayManagement;
import java.util.*;
import catering.businesslogic.staffManagement.*;
import java.time.*;

import java.sql.*;

import catering.persistence.*;

public class HolidayTable{

    private static HolidayTable singleInstance;

    private List<HolidayRequest> table;
    
    private HolidayTable(){
        table = new ArrayList<HolidayRequest>();
    }

    public static HolidayTable getInstance() {
        if (singleInstance == null) {
            singleInstance = new HolidayTable();
        }
        return singleInstance;
    }

    public void addHolidayRequest(HolidayRequest request){
        table.add(request);
        Collections.sort(table);   
     
    }
    
    public void setHolidayRequest(int index, HolidayRequest request){
        table.set(index, request);
    }
    
    public void deleteHolidayRequest(int index){
        table.remove(index);
        Collections.sort(table);
    }

    public List<HolidayRequest> getTable(){
        return this.table;
    }

    public HolidayRequest getHolidayRequest(int index){
        return this.table.get(index);
    }


    /** DATABASE OPERATIONS */
    
    public static ArrayList<HolidayRequest> load(int id){
        String query = "SELECT staff_member_id, from_date, to_date FROM HolidayTableRequest WHERE staff_member_id = ?";
        ArrayList<HolidayRequest> ret = new ArrayList<HolidayRequest>();
        PersistenceManager.executeQuery(query, new ResultHandler(){
            public void handle(ResultSet rs) throws SQLException{
                do{
                    ret.add(new HolidayRequest(new DateSpan(LocalDate.parse(rs.getString("from_date")),LocalDate.parse(rs.getString("to_date"))), StaffMember.load(rs.getInt("staff_member_id"))));
                }while(rs.next());
            }
        }, id);

        return ret;
    }


    public static void updateAddHolidayRequest(HolidayTable holidayTable,HolidayRequest request){
        String query = "INSERT INTO HolidayTableRequest(from_date, to_date,staff_member_id) VALUES (?, ?, ?)";
        PersistenceManager.executeUpdate(query, request.getStartDate().toString(), request.getEndDate().toString(), request.getStaffMember().getContactId());
    }

    public static void updateChangedHolidayRequest(HolidayTable holidayTable, int index, HolidayRequest request){
        String query = "UPDATE HolidayTableRequest SET `from_date` = ?, `to_date` = ?  WHERE `staff_member_id` = ? AND `from_date` = ? AND `to_date` = ?";
        HolidayRequest hr = holidayTable.getHolidayRequest(index); 
        PersistenceManager.executeUpdate(query, request.getStartDate().toString(), request.getEndDate().toString(), hr.getStaffMember().getContactId(),hr.getStartDate().toString(),hr.getEndDate().toString());
    }

    public static void updateDeletedHolidayRequest(HolidayTable holidayTable, int index){
        String query = "DELETE FROM HolidayTableRequest WHERE `staff_member_id` = ? AND `from_date` = ? AND `to_date` = ? ";
        HolidayRequest hr = holidayTable.getHolidayRequest(index); 
        PersistenceManager.executeUpdate(query, hr.getStaffMember().getContactId(),hr.getStartDate().toString(),hr.getEndDate().toString());
    }
    
    
}