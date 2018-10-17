package pt.tecnico.drivedb.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class Session_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Dir> role$$workingDir = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Dir>() {
        @Override
        public pt.tecnico.drivedb.domain.Dir getValue(pt.tecnico.drivedb.domain.Session o1) {
            return ((Session_Base.DO_State)o1.get$obj$state(false)).workingDir;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.Session o1, pt.tecnico.drivedb.domain.Dir o2) {
            ((Session_Base.DO_State)o1.get$obj$state(true)).workingDir = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Session> getInverseRole() {
            return pt.tecnico.drivedb.domain.Dir.role$$session;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.User> role$$currentUser = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.User>() {
        @Override
        public pt.tecnico.drivedb.domain.User getValue(pt.tecnico.drivedb.domain.Session o1) {
            return ((Session_Base.DO_State)o1.get$obj$state(false)).currentUser;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.Session o1, pt.tecnico.drivedb.domain.User o2) {
            ((Session_Base.DO_State)o1.get$obj$state(true)).currentUser = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Session> getInverseRole() {
            return pt.tecnico.drivedb.domain.User.role$$session;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.EnvironmentVar> role$$var = new pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.EnvironmentVar>() {
        public pt.ist.fenixframework.dml.runtime.RelationBaseSet<pt.tecnico.drivedb.domain.EnvironmentVar> getSet(pt.tecnico.drivedb.domain.Session o1) {
            return ((Session_Base)o1).get$rl$var();
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.EnvironmentVar,pt.tecnico.drivedb.domain.Session> getInverseRole() {
            return pt.tecnico.drivedb.domain.EnvironmentVar.role$$session;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Manager> role$$manager = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Manager>() {
        @Override
        public pt.tecnico.drivedb.domain.Manager getValue(pt.tecnico.drivedb.domain.Session o1) {
            return ((Session_Base.DO_State)o1.get$obj$state(false)).manager;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.Session o1, pt.tecnico.drivedb.domain.Manager o2) {
            ((Session_Base.DO_State)o1.get$obj$state(true)).manager = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Session> getInverseRole() {
            return pt.tecnico.drivedb.domain.Manager.role$$session;
        }
        
    };
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Session> getRelationSessionContainsDir() {
        return pt.tecnico.drivedb.domain.Dir.getRelationSessionContainsDir();
    }
    
    private final static class SessionContainsUser {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.User> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.User>(role$$currentUser, "SessionContainsUser");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.User> getRelationSessionContainsUser() {
        return SessionContainsUser.relation;
    }
    
    static {
        SessionContainsUser.relation.setRelationName("pt.tecnico.drivedb.domain.Session.SessionContainsUser");
    }
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.EnvironmentVar,pt.tecnico.drivedb.domain.Session> getRelationSessionContainsEnvironmentVar() {
        return pt.tecnico.drivedb.domain.EnvironmentVar.getRelationSessionContainsEnvironmentVar();
    }
    private static pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.EnvironmentVar> keyFunction$$var = new pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.EnvironmentVar>() { public Comparable<?> getKey(pt.tecnico.drivedb.domain.EnvironmentVar value) { return value.getOid(); } public boolean allowMultipleKeys() {return false; }};
    
    private final static class ManagerContainsSessions {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Manager> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Manager>(role$$manager, "ManagerContainsSessions");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Manager> getRelationManagerContainsSessions() {
        return ManagerContainsSessions.relation;
    }
    
    static {
        ManagerContainsSessions.relation.setRelationName("pt.tecnico.drivedb.domain.Session.ManagerContainsSessions");
    }
    
    // Slots
    
    // Role Slots
    private RelationList<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.EnvironmentVar> get$rl$var() {
        return get$$relationList("var", getRelationSessionContainsEnvironmentVar().getInverseRelation());
        
    }
    
    // Init Instance
    
    private void initInstance() {
        init$Instance(true);
    }
    
    @Override
    protected void init$Instance(boolean allocateOnly) {
        super.init$Instance(allocateOnly);
        
    }
    
    // Constructors
    protected  Session_Base() {
        super();
    }
    
    // Getters and Setters
    
    public long getToken() {
        return ((DO_State)this.get$obj$state(false)).token;
    }
    
    public void setToken(long token) {
        ((DO_State)this.get$obj$state(true)).token = token;
    }
    
    private long get$token() {
        long value = ((DO_State)this.get$obj$state(false)).token;
        return pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForlong(value);
    }
    
    private final void set$token(long value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).token = (long)(value);
    }
    
    public org.joda.time.DateTime getDate() {
        return ((DO_State)this.get$obj$state(false)).date;
    }
    
    public void setDate(org.joda.time.DateTime date) {
        ((DO_State)this.get$obj$state(true)).date = date;
    }
    
    private java.sql.Timestamp get$date() {
        org.joda.time.DateTime value = ((DO_State)this.get$obj$state(false)).date;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForDateTime(value);
    }
    
    private final void set$date(org.joda.time.DateTime value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).date = (org.joda.time.DateTime)((value == null) ? null : value);
    }
    
    // Role Methods
    
    public pt.tecnico.drivedb.domain.Dir getWorkingDir() {
        return ((DO_State)this.get$obj$state(false)).workingDir;
    }
    
    public void setWorkingDir(pt.tecnico.drivedb.domain.Dir workingDir) {
        getRelationSessionContainsDir().add(workingDir, (pt.tecnico.drivedb.domain.Session)this);
    }
    
    private java.lang.Long get$oidWorkingDir() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).workingDir;
        return (value == null) ? null : value.getOid();
    }
    
    public pt.tecnico.drivedb.domain.User getCurrentUser() {
        return ((DO_State)this.get$obj$state(false)).currentUser;
    }
    
    public void setCurrentUser(pt.tecnico.drivedb.domain.User currentUser) {
        getRelationSessionContainsUser().add((pt.tecnico.drivedb.domain.Session)this, currentUser);
    }
    
    private java.lang.Long get$oidCurrentUser() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).currentUser;
        return (value == null) ? null : value.getOid();
    }
    
    public void addVar(pt.tecnico.drivedb.domain.EnvironmentVar var) {
        getRelationSessionContainsEnvironmentVar().add(var, (pt.tecnico.drivedb.domain.Session)this);
    }
    
    public void removeVar(pt.tecnico.drivedb.domain.EnvironmentVar var) {
        getRelationSessionContainsEnvironmentVar().remove(var, (pt.tecnico.drivedb.domain.Session)this);
    }
    
    public java.util.Set<pt.tecnico.drivedb.domain.EnvironmentVar> getVarSet() {
        return get$rl$var();
    }
    
    public void set$var(OJBFunctionalSetWrapper var) {
        get$rl$var().setFromOJB(this, "var", var);
    }
    
    @Deprecated
    public java.util.Set<pt.tecnico.drivedb.domain.EnvironmentVar> getVar() {
        return getVarSet();
    }
    
    @Deprecated
    public int getVarCount() {
        return getVarSet().size();
    }
    
    public pt.tecnico.drivedb.domain.Manager getManager() {
        return ((DO_State)this.get$obj$state(false)).manager;
    }
    
    public void setManager(pt.tecnico.drivedb.domain.Manager manager) {
        getRelationManagerContainsSessions().add((pt.tecnico.drivedb.domain.Session)this, manager);
    }
    
    private java.lang.Long get$oidManager() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).manager;
        return (value == null) ? null : value.getOid();
    }
    
    
    protected void checkDisconnected() {
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.workingDir != null) handleAttemptToDeleteConnectedObject("WorkingDir");
        if (castedState.currentUser != null) handleAttemptToDeleteConnectedObject("CurrentUser");
        if (get$rl$var().size() > 0) handleAttemptToDeleteConnectedObject("Var");
        if (castedState.manager != null) handleAttemptToDeleteConnectedObject("Manager");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$token(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readlong(rs, "TOKEN"), state);
        set$date(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDateTime(rs, "DATE"), state);
        castedState.workingDir = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_WORKING_DIR");
        castedState.currentUser = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_CURRENT_USER");
        castedState.manager = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_MANAGER");
    }
    protected pt.ist.fenixframework.dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("var")) return getRelationSessionContainsEnvironmentVar().getInverseRelation();
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("var", getRelationSessionContainsEnvironmentVar().getInverseRelation());
        
    }
    protected static class DO_State extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State {
        private long token;
        private org.joda.time.DateTime date;
        private pt.tecnico.drivedb.domain.Dir workingDir;
        private pt.tecnico.drivedb.domain.User currentUser;
        private pt.tecnico.drivedb.domain.Manager manager;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.token = this.token;
            newCasted.date = this.date;
            newCasted.workingDir = this.workingDir;
            newCasted.currentUser = this.currentUser;
            newCasted.manager = this.manager;
            
        }
        
    }
    
}
