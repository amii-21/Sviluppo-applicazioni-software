package catering.persistence;

import catering.businesslogic.eventManagement.EventInfo;
import catering.businesslogic.eventManagement.EventInfoReceiver;
import catering.businesslogic.eventManagement.Notes;
public class EventInfoPersistence implements EventInfoReceiver{
    @Override
    public void updateNoteAdded(EventInfo eventInfo, Notes n){
        EventInfo.updateNoteAdded(eventInfo,n);
    }
    @Override

    public void updateNoteModified(EventInfo eventInfo, int index ,Notes n){
        EventInfo.updateNoteModified(eventInfo, index, n);
    }
    @Override
    public void updateNoteDeleted(EventInfo eventInfo, int index){
        EventInfo.updateNoteDeleted(eventInfo, index);
    }
    @Override
    public void updateNumGuestsModified(EventInfo eventInfo, int i){
        EventInfo.updateNumGuestsModified(eventInfo, i);
    }
    @Override
    public void updateLocationAdded(EventInfo eventInfo, String s){
        EventInfo.updateLocationAdded(eventInfo, s);
    }
    @Override
    public void updateLocationModified(EventInfo eventInfo, int index, String s){
        EventInfo.updateLocationModified(eventInfo, index, s);
    }
    @Override
    public void updateLocationDeleted(EventInfo eventInfo, int index){
        EventInfo.updateLocationDeleted( eventInfo,  index);
    }
    @Override
    public void updateInfoAdded(EventInfo eventInfo, String s){
        EventInfo.updateInfoAdded( eventInfo,  s);
    }
    @Override
    public void updateInfoModified(EventInfo eventInfo, int index,String s){
        EventInfo.updateInfoModified( eventInfo,  index, s);
    }
    @Override
    public void updateInfoDeleted(EventInfo eventInfo, int index){
        EventInfo.updateInfoDeleted( eventInfo,  index);
    }
    @Override
    public void updateTypeOfServiceAdded(EventInfo eventInfo, String s){
        EventInfo.updateTypeOfServiceAdded( eventInfo,  s);
    }
    @Override
    public void updateTypeOfServiceModified(EventInfo eventInfo, int index, String s){
        EventInfo.updateTypeOfServiceModified( eventInfo,  index,  s);
    }
    @Override
    public void updateTypeOfServiceDeleted(EventInfo eventInfo, int index){
        EventInfo.updateTypeOfServiceDeleted( eventInfo,  index);
    }

}