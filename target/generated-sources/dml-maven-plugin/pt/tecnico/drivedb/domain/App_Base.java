package pt.tecnico.drivedb.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class App_Base extends pt.tecnico.drivedb.domain.PlainFile {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.App,pt.tecnico.drivedb.domain.Extension> role$$ex = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.App,pt.tecnico.drivedb.domain.Extension>() {
        @Override
        public pt.tecnico.drivedb.domain.Extension getValue(pt.tecnico.drivedb.domain.App o1) {
            return ((App_Base.DO_State)o1.get$obj$state(false)).ex;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.App o1, pt.tecnico.drivedb.domain.Extension o2) {
            ((App_Base.DO_State)o1.get$obj$state(true)).ex = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.App> getInverseRole() {
            return pt.tecnico.drivedb.domain.Extension.role$$app;
        }
        
    };
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.App> getRelationExtensionHasApp() {
        return pt.tecnico.drivedb.domain.Extension.getRelationExtensionHasApp();
    }
    
    // Slots
    
    // Role Slots
    
    // Init Instance
    
    private void initInstance() {
        init$Instance(true);
    }
    
    @Override
    protected void init$Instance(boolean allocateOnly) {
        super.init$Instance(allocateOnly);
        
    }
    
    // Constructors
    protected  App_Base() {
        super();
    }
    
    // Getters and Setters
    
    // Role Methods
    
    public pt.tecnico.drivedb.domain.Extension getEx() {
        return ((DO_State)this.get$obj$state(false)).ex;
    }
    
    public void setEx(pt.tecnico.drivedb.domain.Extension ex) {
        getRelationExtensionHasApp().add(ex, (pt.tecnico.drivedb.domain.App)this);
    }
    
    private java.lang.Long get$oidEx() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).ex;
        return (value == null) ? null : value.getOid();
    }
    
    
    protected void checkDisconnected() {
        super.checkDisconnected();
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.ex != null) handleAttemptToDeleteConnectedObject("Ex");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        super.readStateFromResultSet(rs, state);
        DO_State castedState = (DO_State)state;
        castedState.ex = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_EX");
    }
    protected pt.ist.fenixframework.dml.runtime.Relation get$$relationFor(String attrName) {
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        
    }
    protected static class DO_State extends pt.tecnico.drivedb.domain.PlainFile.DO_State {
        private pt.tecnico.drivedb.domain.Extension ex;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.ex = this.ex;
            
        }
        
    }
    
}
