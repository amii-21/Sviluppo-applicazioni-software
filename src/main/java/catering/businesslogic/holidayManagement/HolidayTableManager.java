package catering.businesslogic.holidayManagement;

import java.util.*;

import catering.businesslogic.CatERing;
import catering.businesslogic.user.User;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.staffManagement.*;
import catering.businesslogic.availabilityManagement.*;
import catering.util.Pair;

public class HolidayTableManager{
    
    private ArrayList<HolidayTableReceiver> eventReceivers;
    // it's a singleton!
    private HolidayTable currentHolidayTable;
    
    public HolidayTableManager(){
        eventReceivers = new ArrayList<HolidayTableReceiver>();
        currentHolidayTable = HolidayTable.getInstance();    
    }
    
    //*event sender methods*
    public void addReceiver(HolidayTableReceiver htr){
        if(!eventReceivers.contains(htr))
            eventReceivers.add(htr);
    }

    public void removeReceiver(HolidayTableReceiver htr){
        eventReceivers.remove(htr);
    }

    private void notifyAddHolidayRequest(HolidayRequest request){
        for(HolidayTableReceiver htr : eventReceivers){
            htr.updateAddHolidayRequest(currentHolidayTable,request);
        }
    }

    private void notifyChangedHolidayRequest(int index,HolidayRequest request){
        for(HolidayTableReceiver htr : eventReceivers){
            htr.updateChangedHolidayRequest(currentHolidayTable, index, request);
        }
    }

    private void notifyDeletedHolidayRequest(int index){
        for(HolidayTableReceiver htr : eventReceivers){
            htr.updateDeletedHolidayRequest(currentHolidayTable,index);
        }
    }
    
    //*operations methods*
    public void addHolidayRequest(HolidayRequest request){
        if(request.getStaffMember().getPermanent()){
            currentHolidayTable.addHolidayRequest(request);
            notifyAddHolidayRequest(request);
        }
    }
    public void setHolidayRequest(int index, HolidayRequest request){
        notifyChangedHolidayRequest(index,request);
        currentHolidayTable.setHolidayRequest(index, request);
    }
    public void deleteHolidayRequest(int index){
        notifyDeletedHolidayRequest(index);
        currentHolidayTable.deleteHolidayRequest(index);
    }
    
    // owner
    public void handleRequest(int index, boolean accept) throws UseCaseLogicException{
        CatERing application = CatERing.getInstance();

        User user = application.getUserManager().getCurrentUser();
        
        if(!user.isOwner()){
            throw new UseCaseLogicException("User is not an owner");
        }
        if(currentHolidayTable == null){
            throw new UseCaseLogicException("There is no HolidayTable in the system");
        }

        if(accept == false){
            deleteHolidayRequest(index);
            notifyDeletedHolidayRequest(index);
            //business logic per avvisare l'utente del rifiuto
            return;
        }
        
        HolidayRequest hr = getHolidayRequest(index);
        
        StaffMemberManager staffManager = application.getStaffMemberManager();
        
        staffManager.setCurrentStaffMember(hr.getStaffMember());
        staffManager.acceptHolidayRequest(hr);        
            
        notifyDeletedHolidayRequest(index);
        deleteHolidayRequest(index);
        
        application.getAvailabilityTableManager().revokeAvailabilityForHoliday(hr.getStaffMember());
        
    }
    
    public HolidayTable getHolidayRequests(){
        return this.currentHolidayTable;
    } 
    public HolidayRequest getHolidayRequest(int index){
        return this.currentHolidayTable.getHolidayRequest(index);
    }

    public Pair<HolidayRequest, List<Availability>> getHolidayRequestInfo(int index){
        HolidayRequest hr = this.currentHolidayTable.getHolidayRequest(index);
        List<Availability> availabilityList = CatERing.getInstance().getAvailabilityTableManager().getAvailabilityOfStaffMember(hr.getStaffMember());
        return new Pair<HolidayRequest,List<Availability>>(hr, availabilityList);
    }
}