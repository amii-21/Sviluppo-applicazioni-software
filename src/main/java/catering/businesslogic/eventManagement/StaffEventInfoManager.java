package catering.businesslogic.eventManagement;
import java.time.LocalDate;
import java.util.ArrayList;
import catering.businesslogic.staffManagement.ContactedStaff;
import catering.businesslogic.CatERing;
import catering.businesslogic.user.User;

public class StaffEventInfoManager{

    private StaffEventInfo curreStaffEventInfo;
    private ArrayList<StaffEventInfoReceiver> eventReceivers = new ArrayList<StaffEventInfoReceiver>();
   
    public StaffEventInfo getCurrentStaffEventInfo(){
        return curreStaffEventInfo;
    }
    
    public void addReceiver(StaffEventInfoReceiver seir){
        eventReceivers.add(seir);
    }
    public void removeReceiver(StaffEventInfoReceiver seir){
        eventReceivers.remove(seir);
    }
    
    private void notifyNumberOfWaitStaff(){
        for(StaffEventInfoReceiver seir : eventReceivers){
            seir.updateNumberOfWaitStaff(curreStaffEventInfo,curreStaffEventInfo.getNumberOfWaitStaff());
        }
    }
    private void notifyNumberOfKitchenStaff(){
        for(StaffEventInfoReceiver seir : eventReceivers){
            seir.updateNumberOfKitchenStaff(curreStaffEventInfo,curreStaffEventInfo.getNumberOfKitchenStaff());
        }
    }
    private void notifyModifiedRoles(int index, Role role){
        for(StaffEventInfoReceiver seir : eventReceivers){
            seir.updateModifiedRole(curreStaffEventInfo,index,role);
        }
    }
    private void notifyAddedRole(Role role){
        for(StaffEventInfoReceiver seir : eventReceivers){
            seir.updateAddedRole(curreStaffEventInfo, role);
        }
    }
    private void notifyDeletedRole(int index){
        for(StaffEventInfoReceiver seir : eventReceivers){
            seir.updateDeletedRole(curreStaffEventInfo, index); 
        }
    }
    private void notifyAddedContactedStaff(ContactedStaff cont){
        for(StaffEventInfoReceiver seir : eventReceivers){
            seir.updateAddedContactedStaff(curreStaffEventInfo, cont);
        }
    }
    private void notifyDeleteContactedStaff(int index){
        for(StaffEventInfoReceiver seir : eventReceivers){
            seir.updateDeleteContactedStaff(curreStaffEventInfo, index); 
        }
    }
    private void notifyEndDateContactedStaff(int index, LocalDate endDate){
        for(StaffEventInfoReceiver seir : eventReceivers){
            seir.updateEndDateContactedStaff(curreStaffEventInfo, index, endDate);
        }
    }
    private void notifyPositionContactedStaff(int index, String position){
        for(StaffEventInfoReceiver seir : eventReceivers){
            seir.updatePositionContactedStaff(curreStaffEventInfo, index, position);
        }
    }

    public int getEventId(){
        return curreStaffEventInfo.getEventId();
    }

    public void setNumberOfKitchenStaff(int i){
        curreStaffEventInfo.setNumberOfKitchenStaff(i);
        notifyNumberOfKitchenStaff();
    }
    public void setNumberOfWaitStaff(int i){
        curreStaffEventInfo.setNumberOfWaitStaff(i);
        notifyNumberOfWaitStaff();
    }

    public void setCurrentStaffEventInfo(StaffEventInfo currStaffEventInfo){
        this.curreStaffEventInfo = currStaffEventInfo; 
    }

    public void setRole(int index, Role role){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            notifyModifiedRoles(index, role);
            curreStaffEventInfo.setRole(index, role);
            notifyNumberOfWaitStaff();
            notifyNumberOfKitchenStaff();
        }
    }
    public Role getRole(int index){
       return curreStaffEventInfo.getRole(index);
    }
    public ArrayList<Role> getRoles(){
        return curreStaffEventInfo.getRoles();
    }
    public int getNumberOfWaitStaff(){
        return curreStaffEventInfo.getNumberOfWaitStaff();
    }
    
    public int getNumberOfKitchenStaff(){
        return curreStaffEventInfo.getNumberOfKitchenStaff();
    }
    public ArrayList<ContactedStaff> getContactedStaffs(){
        return curreStaffEventInfo.getContactedStaffs();
    }
     
    public ContactedStaff getContactedStaff(int index){
        return curreStaffEventInfo.getContactedStaff(index);
    }
    public void addRole(Role role){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            curreStaffEventInfo.addRole(role);
            notifyAddedRole(role);
            notifyNumberOfWaitStaff();
            notifyNumberOfKitchenStaff();
        }
    }
    public void deleteRole(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
             notifyDeletedRole(index);
            curreStaffEventInfo.deleteRole(index);
            notifyNumberOfWaitStaff();
            notifyNumberOfKitchenStaff();
        }
    }
    public void addContactedStaff(ContactedStaff cont){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            curreStaffEventInfo.addContactedStaff(cont);
            notifyAddedContactedStaff(cont);
        }
    }
    public void deleteContactedStaff(int index){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            notifyDeleteContactedStaff(index);
            curreStaffEventInfo.deleteContactedStaff(index);
        }
    }
    
    public void setEndDateContactedStaff(int index ,LocalDate localDate){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            curreStaffEventInfo.setEndDateContactedStaff(index, localDate);
            notifyEndDateContactedStaff(index, localDate);
        }
        
    }
    public void setPositionContactedStaff(int index, String position){
        CatERing application = CatERing.getInstance();
        User user = application.getUserManager().getCurrentUser();
        if(user.isOwner() || user.isOrganizer()){
            curreStaffEventInfo.setPositionContactedStaff(index, position);
            notifyPositionContactedStaff(index, position);
        }
    }
}