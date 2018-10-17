package pt.tecnico.drivedb.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class EnvironmentVar_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.EnvironmentVar,pt.tecnico.drivedb.domain.Session> role$$session = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.EnvironmentVar,pt.tecnico.drivedb.domain.Session>() {
        @Override
        public pt.tecnico.drivedb.domain.Session getValue(pt.tecnico.drivedb.domain.EnvironmentVar o1) {
            return ((EnvironmentVar_Base.DO_State)o1.get$obj$state(false)).session;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.EnvironmentVar o1, pt.tecnico.drivedb.domain.Session o2) {
            ((EnvironmentVar_Base.DO_State)o1.get$obj$state(true)).session = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.EnvironmentVar> getInverseRole() {
            return pt.tecnico.drivedb.domain.Session.role$$var;
        }
        
    };
    
    private final static class SessionContainsEnvironmentVar {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.EnvironmentVar,pt.tecnico.drivedb.domain.Session> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.EnvironmentVar,pt.tecnico.drivedb.domain.Session>(role$$session, "SessionContainsEnvironmentVar");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.EnvironmentVar,pt.tecnico.drivedb.domain.Session> getRelationSessionContainsEnvironmentVar() {
        return SessionContainsEnvironmentVar.relation;
    }
    
    static {
        SessionContainsEnvironmentVar.relation.setRelationName("pt.tecnico.drivedb.domain.EnvironmentVar.SessionContainsEnvironmentVar");
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
    protected  EnvironmentVar_Base() {
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
    
    public java.lang.String getPath() {
        return ((DO_State)this.get$obj$state(false)).path;
    }
    
    public void setPath(java.lang.String path) {
        ((DO_State)this.get$obj$state(true)).path = path;
    }
    
    private java.lang.String get$path() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).path;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$path(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).path = (java.lang.String)((value == null) ? null : value);
    }
    
    // Role Methods
    
    public pt.tecnico.drivedb.domain.Session getSession() {
        return ((DO_State)this.get$obj$state(false)).session;
    }
    
    public void setSession(pt.tecnico.drivedb.domain.Session session) {
        getRelationSessionContainsEnvironmentVar().add((pt.tecnico.drivedb.domain.EnvironmentVar)this, session);
    }
    
    private java.lang.Long get$oidSession() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).session;
        return (value == null) ? null : value.getOid();
    }
    
    
    protected void checkDisconnected() {
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.session != null) handleAttemptToDeleteConnectedObject("Session");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$name(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "NAME"), state);
        set$path(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "PATH"), state);
        castedState.session = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_SESSION");
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
        private java.lang.String path;
        private pt.tecnico.drivedb.domain.Session session;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.name = this.name;
            newCasted.path = this.path;
            newCasted.session = this.session;
            
        }
        
    }
    
}
