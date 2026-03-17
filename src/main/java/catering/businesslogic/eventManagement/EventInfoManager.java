package catering.businesslogic.eventManagement;

import java.util.ArrayList;
import java.util.List;
import catering.businesslogic.CatERing;
import catering.businesslogic.user.User;
import catering.businesslogic.staffManagement.*;

public class EventInfoManager{
    
    private EventInfo currentEventInfo;
    private ArrayList<EventInfoReceiver> eventReceivers = new ArrayList<EventInfoReceiver>();
   
    public void setCurrentEventInfo(EventInfo currentEventInfo){
        this.currentEventInfo = currentEventInfo;
    }
    public EventInfo getCurrentEventInfo(){
        return currentEventInfo;
    }

    // event sender methods
    public void addReceiver(EventInfoReceiver eir){
        this.eventReceivers.add(eir);
    }
    public void removeReceiver(EventInfoReceiver eir){
        this.eventReceivers.remove(eir);
    }
    private void notifyNoteAdded(Notes n){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateNoteAdded(currentEventInfo,n);
        }
    }
    private void notifyNoteModified(int index, Notes n){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateNoteModified(currentEventInfo, index, n);
        }
    }
    private void notifyNoteDeleted(int index){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateNoteDeleted(currentEventInfo, index);
        }
    }
    private void notifyNumGuestsModified(int newNumGuest){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateNumGuestsModified(currentEventInfo,newNumGuest);
        }
    }
    private void notifyLocationAdded(String location){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateLocationAdded(currentEventInfo, location);
        }
    }
    private void notifyLocationModified(int index, String newLocation){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateLocationModified(currentEventInfo, index, newLocation);
        }
    }
    private void notifyLocationDeleted(int index){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateLocationDeleted(currentEventInfo, index);
        }
    }
    private void notifyInfoAdded(String s){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateInfoAdded(currentEventInfo, s);
        }
    }
    private void notifyInfoModified(int index, String s){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateInfoModified(currentEventInfo, index, s);
        }
    }
    private void notifyInfoDeleted(int index){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateInfoDeleted(currentEventInfo, index);
        }
    }
    private void notifyTypeOfServiceAdded(String s){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateTypeOfServiceAdded(currentEventInfo, s);
        }
    }
    private void notifyTypeOfServiceModified(int index, String newTypeOfService){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateTypeOfServiceModified(currentEventInfo, index, newTypeOfService);
        }
    }
    private void notifyTypeOfServiceDeleted(int index){
        for(EventInfoReceiver eir : eventReceivers){
            eir.updateTypeOfServiceDeleted(currentEventInfo, index);
        } 
    }

   
    // *operations methods*
    public void addNotes(Notes n){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.addNotes(n);
            notifyNoteAdded(n);
        }
    }
    public List<Notes> getNotes(){
       return currentEventInfo.getNotes();
    }
    public Notes getNote(int index){
       return currentEventInfo.getNote(index);
    }
    public void setNotes(int index, Notes n){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyNoteModified(index, n);
            currentEventInfo.setNotes(index, n);
        }
    }
    public void deleteNotes(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyNoteDeleted(index);
            currentEventInfo.deleteNotes(index);
        }
    }  	
    public void setNumGuests(int newNumGuest){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.setNumGuests(newNumGuest);
            notifyNumGuestsModified(newNumGuest);
        }
    } 
    public int getNumGuests(){
        return currentEventInfo.getNumGuests();
    }
    public void setLocations(int index, String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyLocationModified(index, s);
            currentEventInfo.setLocations(index, s);
        }
    }
    public List<String> getLocations(){
        return currentEventInfo.getLocations();
    }
    public String getLocation(int index){
        return currentEventInfo.getLocation(index);
    }
    public void addLocations(String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.addLocations(s);
            notifyLocationAdded(s);
        }
    }
    public void deleteLocations(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyLocationDeleted(index);
            currentEventInfo.deleteLocations(index);
        }
    } 	
    
    public void addInfo(String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.addInfo(s);
            notifyInfoAdded(s);
        }
    }

    public void setInfo(int index,String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyInfoModified(index, s);
            currentEventInfo.setInfo(index, s);
        }
    }
    
    public List<String> getInfo(){
        return currentEventInfo.getInfo();
    }
    public String getInfo(int index){
        return currentEventInfo.getInfo(index);
    }
    	
    public void deleteInfo(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyInfoDeleted(index);
            currentEventInfo.deleteInfo(index);
        }
    } 
    public void setTypeOfService(int index, String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyTypeOfServiceModified(index, s);
            currentEventInfo.setTypeOfService(index, s);
        }
    }
    public void addTypeOfService(String s){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            currentEventInfo.addTypeOfService(s);
            notifyTypeOfServiceAdded(s);
        }
    }
    public void deleteTypeOfService(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            setCurrentStaffEventInfo();
            notifyTypeOfServiceDeleted(index);
            currentEventInfo.deleteTypeOfService(index);
        }
    }
    public List<String> getTypeOfServices(){
        return currentEventInfo.getTypeOfServices();
    }
    public String getTypeOfService(int index){
        return currentEventInfo.getTypeOfService(index);
    }
    public void setCurrentStaffEventInfo(){
        CatERing application = CatERing.getInstance();
        StaffEventInfoManager seim = application.getStaffEventInfoManager();
        seim.setCurrentStaffEventInfo(currentEventInfo.getStaffEventInfo());
    }

    public List<EventInfo> getEventInfoPartecipated(StaffMember staffmember){
        return EventInfo.loadAll();
    }
}