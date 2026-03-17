package catering.persistence;

import java.time.LocalDate;

import catering.businesslogic.eventManagement.Role;
import catering.businesslogic.eventManagement.StaffEventInfo;
import catering.businesslogic.eventManagement.StaffEventInfoReceiver;
import catering.businesslogic.staffManagement.ContactedStaff;

public class StaffEventInfoPersistence implements StaffEventInfoReceiver {

    public void updateNumberOfWaitStaff(StaffEventInfo staffEventInfo,int i){
        StaffEventInfo.updateNumberOfWaitStaff(staffEventInfo, i);
    }
    public void updateNumberOfKitchenStaff(StaffEventInfo staffEventInfo,int i){
        StaffEventInfo.updateNumberOfKitchenStaff(staffEventInfo, i);   
    }
    public void updateModifiedRole(StaffEventInfo staffEventInfo, int index,Role role){
        StaffEventInfo.updateModifiedRole(staffEventInfo, index, role);   
    }
    public void updateAddedRole(StaffEventInfo staffEventInfo,Role role){
        StaffEventInfo.updateAddedRole(staffEventInfo, role);
    }
    public void updateDeletedRole(StaffEventInfo staffEventInfo,int index){
        StaffEventInfo.updateDeletedRole(staffEventInfo, index);   
    }
    public void updateAddedContactedStaff(StaffEventInfo staffEventInfo,ContactedStaff cont){
        StaffEventInfo.updateAddedContactedStaff(staffEventInfo, cont);   
    }
    public void updateDeleteContactedStaff(StaffEventInfo staffEventInfo,int index){
        StaffEventInfo.updateDeleteContactedStaff(staffEventInfo, index);   
    }
    public void updateEndDateContactedStaff(StaffEventInfo staffEventInfo, int index, LocalDate endDate){
        StaffEventInfo.updateEndDateContactedStaff(staffEventInfo, index, endDate);   
    }
    public void updatePositionContactedStaff(StaffEventInfo staffEventInfo, int index, String position){
        StaffEventInfo.updatePositionContactedStaff(staffEventInfo, index, position);  
    }

}