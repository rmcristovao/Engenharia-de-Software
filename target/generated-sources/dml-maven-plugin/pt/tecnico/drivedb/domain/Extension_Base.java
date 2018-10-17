package pt.tecnico.drivedb.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class Extension_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.User> role$$user = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.User>() {
        @Override
        public pt.tecnico.drivedb.domain.User getValue(pt.tecnico.drivedb.domain.Extension o1) {
            return ((Extension_Base.DO_State)o1.get$obj$state(false)).user;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.Extension o1, pt.tecnico.drivedb.domain.User o2) {
            ((Extension_Base.DO_State)o1.get$obj$state(true)).user = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Extension> getInverseRole() {
            return pt.tecnico.drivedb.domain.User.role$$extension;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.App> role$$app = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.App>() {
        @Override
        public pt.tecnico.drivedb.domain.App getValue(pt.tecnico.drivedb.domain.Extension o1) {
            return ((Extension_Base.DO_State)o1.get$obj$state(false)).app;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.Extension o1, pt.tecnico.drivedb.domain.App o2) {
            ((Extension_Base.DO_State)o1.get$obj$state(true)).app = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.App,pt.tecnico.drivedb.domain.Extension> getInverseRole() {
            return pt.tecnico.drivedb.domain.App.role$$ex;
        }
        
    };
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Extension> getRelationUserHasExtension() {
        return pt.tecnico.drivedb.domain.User.getRelationUserHasExtension();
    }
    
    private final static class ExtensionHasApp {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.App> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.App>(role$$app, "ExtensionHasApp");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.App> getRelationExtensionHasApp() {
        return ExtensionHasApp.relation;
    }
    
    static {
        ExtensionHasApp.relation.setRelationName("pt.tecnico.drivedb.domain.Extension.ExtensionHasApp");
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
    protected  Extension_Base() {
        super();
    }
    
    // Getters and Setters
    
    public java.lang.String getName() {
        return ((DO_State)this.get$obj$state(false)).name;
    }
    
    public void setName(java.lang.String name) {
        ((DO_State)this.get$obj$state(true)).name = name;
    }
    
    private java.lang.String get$name() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).name;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$name(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).name = (java.lang.String)((value == null) ? null : value);
    }
    
    // Role Methods
    
    public pt.tecnico.drivedb.domain.User getUser() {
        return ((DO_State)this.get$obj$state(false)).user;
    }
    
    public void setUser(pt.tecnico.drivedb.domain.User user) {
        getRelationUserHasExtension().add(user, (pt.tecnico.drivedb.domain.Extension)this);
    }
    
    private java.lang.Long get$oidUser() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).user;
        return (value == null) ? null : value.getOid();
    }
    
    public pt.tecnico.drivedb.domain.App getApp() {
        return ((DO_State)this.get$obj$state(false)).app;
    }
    
    public void setApp(pt.tecnico.drivedb.domain.App app) {
        getRelationExtensionHasApp().add((pt.tecnico.drivedb.domain.Extension)this, app);
    }
    
    private java.lang.Long get$oidApp() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).app;
        return (value == null) ? null : value.getOid();
    }
    
    
    protected void checkDisconnected() {
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.user != null) handleAttemptToDeleteConnectedObject("User");
        if (castedState.app != null) handleAttemptToDeleteConnectedObject("App");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$name(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "NAME"), state);
        castedState.user = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_USER");
        castedState.app = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_APP");
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
    protected static class DO_State extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State {
        private java.lang.String name;
        private pt.tecnico.drivedb.domain.User user;
        private pt.tecnico.drivedb.domain.App app;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.name = this.name;
            newCasted.user = this.user;
            newCasted.app = this.app;
            
        }
        
    }
    
}
