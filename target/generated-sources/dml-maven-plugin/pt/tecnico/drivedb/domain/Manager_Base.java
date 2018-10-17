package pt.tecnico.drivedb.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class Manager_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Dir> role$$base = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Dir>() {
        @Override
        public pt.tecnico.drivedb.domain.Dir getValue(pt.tecnico.drivedb.domain.Manager o1) {
            return ((Manager_Base.DO_State)o1.get$obj$state(false)).base;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.Manager o1, pt.tecnico.drivedb.domain.Dir o2) {
            ((Manager_Base.DO_State)o1.get$obj$state(true)).base = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Manager> getInverseRole() {
            return pt.tecnico.drivedb.domain.Dir.role$$manager;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.User> role$$users = new pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.User>() {
        public pt.ist.fenixframework.dml.runtime.RelationBaseSet<pt.tecnico.drivedb.domain.User> getSet(pt.tecnico.drivedb.domain.Manager o1) {
            return ((Manager_Base)o1).get$rl$users();
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Manager> getInverseRole() {
            return pt.tecnico.drivedb.domain.User.role$$manager;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Session> role$$session = new pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Session>() {
        public pt.ist.fenixframework.dml.runtime.RelationBaseSet<pt.tecnico.drivedb.domain.Session> getSet(pt.tecnico.drivedb.domain.Manager o1) {
            return ((Manager_Base)o1).get$rl$session();
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Manager> getInverseRole() {
            return pt.tecnico.drivedb.domain.Session.role$$manager;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Manager,pt.ist.fenixframework.DomainRoot> role$$root = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Manager,pt.ist.fenixframework.DomainRoot>() {
        @Override
        public pt.ist.fenixframework.DomainRoot getValue(pt.tecnico.drivedb.domain.Manager o1) {
            return ((Manager_Base.DO_State)o1.get$obj$state(false)).root;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.Manager o1, pt.ist.fenixframework.DomainRoot o2) {
            ((Manager_Base.DO_State)o1.get$obj$state(true)).root = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.ist.fenixframework.DomainRoot,pt.tecnico.drivedb.domain.Manager> getInverseRole() {
            return pt.ist.fenixframework.DomainRoot.role$$manager;
        }
        
    };
    
    private final static class ManagerContainsDir {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Dir> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Dir>(role$$base, "ManagerContainsDir");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Dir> getRelationManagerContainsDir() {
        return ManagerContainsDir.relation;
    }
    
    static {
        ManagerContainsDir.relation.setRelationName("pt.tecnico.drivedb.domain.Manager.ManagerContainsDir");
    }
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Manager> getRelationManagerContainsUsers() {
        return pt.tecnico.drivedb.domain.User.getRelationManagerContainsUsers();
    }
    private static pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.User> keyFunction$$users = new pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.User>() { public Comparable<?> getKey(pt.tecnico.drivedb.domain.User value) { return value.getOid(); } public boolean allowMultipleKeys() {return false; }};
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Manager> getRelationManagerContainsSessions() {
        return pt.tecnico.drivedb.domain.Session.getRelationManagerContainsSessions();
    }
    private static pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.Session> keyFunction$$session = new pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.Session>() { public Comparable<?> getKey(pt.tecnico.drivedb.domain.Session value) { return value.getOid(); } public boolean allowMultipleKeys() {return false; }};
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.ist.fenixframework.DomainRoot,pt.tecnico.drivedb.domain.Manager> getRelationDomainRootHasDriveDBApplication() {
        return pt.ist.fenixframework.DomainRoot.getRelationDomainRootHasDriveDBApplication();
    }
    
    // Slots
    
    // Role Slots
    private RelationList<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.User> get$rl$users() {
        return get$$relationList("users", getRelationManagerContainsUsers().getInverseRelation());
        
    }
    private RelationList<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Session> get$rl$session() {
        return get$$relationList("session", getRelationManagerContainsSessions().getInverseRelation());
        
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
    protected  Manager_Base() {
        super();
    }
    
    // Getters and Setters
    
    public int getCounter() {
        return ((DO_State)this.get$obj$state(false)).counter;
    }
    
    public void setCounter(int counter) {
        ((DO_State)this.get$obj$state(true)).counter = counter;
    }
    
    private int get$counter() {
        int value = ((DO_State)this.get$obj$state(false)).counter;
        return pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$counter(int value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).counter = (int)(value);
    }
    
    // Role Methods
    
    public pt.tecnico.drivedb.domain.Dir getBase() {
        return ((DO_State)this.get$obj$state(false)).base;
    }
    
    public void setBase(pt.tecnico.drivedb.domain.Dir base) {
        getRelationManagerContainsDir().add((pt.tecnico.drivedb.domain.Manager)this, base);
    }
    
    private java.lang.Long get$oidBase() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).base;
        return (value == null) ? null : value.getOid();
    }
    
    public void addUsers(pt.tecnico.drivedb.domain.User users) {
        getRelationManagerContainsUsers().add(users, (pt.tecnico.drivedb.domain.Manager)this);
    }
    
    public void removeUsers(pt.tecnico.drivedb.domain.User users) {
        getRelationManagerContainsUsers().remove(users, (pt.tecnico.drivedb.domain.Manager)this);
    }
    
    public java.util.Set<pt.tecnico.drivedb.domain.User> getUsersSet() {
        return get$rl$users();
    }
    
    public void set$users(OJBFunctionalSetWrapper users) {
        get$rl$users().setFromOJB(this, "users", users);
    }
    
    @Deprecated
    public java.util.Set<pt.tecnico.drivedb.domain.User> getUsers() {
        return getUsersSet();
    }
    
    @Deprecated
    public int getUsersCount() {
        return getUsersSet().size();
    }
    
    public void addSession(pt.tecnico.drivedb.domain.Session session) {
        getRelationManagerContainsSessions().add(session, (pt.tecnico.drivedb.domain.Manager)this);
    }
    
    public void removeSession(pt.tecnico.drivedb.domain.Session session) {
        getRelationManagerContainsSessions().remove(session, (pt.tecnico.drivedb.domain.Manager)this);
    }
    
    public java.util.Set<pt.tecnico.drivedb.domain.Session> getSessionSet() {
        return get$rl$session();
    }
    
    public void set$session(OJBFunctionalSetWrapper session) {
        get$rl$session().setFromOJB(this, "session", session);
    }
    
    @Deprecated
    public java.util.Set<pt.tecnico.drivedb.domain.Session> getSession() {
        return getSessionSet();
    }
    
    @Deprecated
    public int getSessionCount() {
        return getSessionSet().size();
    }
    
    public pt.ist.fenixframework.DomainRoot getRoot() {
        return ((DO_State)this.get$obj$state(false)).root;
    }
    
    public void setRoot(pt.ist.fenixframework.DomainRoot root) {
        getRelationDomainRootHasDriveDBApplication().add(root, (pt.tecnico.drivedb.domain.Manager)this);
    }
    
    private java.lang.Long get$oidRoot() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).root;
        return (value == null) ? null : value.getOid();
    }
    
    
    protected void checkDisconnected() {
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.base != null) handleAttemptToDeleteConnectedObject("Base");
        if (get$rl$users().size() > 0) handleAttemptToDeleteConnectedObject("Users");
        if (get$rl$session().size() > 0) handleAttemptToDeleteConnectedObject("Session");
        if (castedState.root != null) handleAttemptToDeleteConnectedObject("Root");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$counter(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readint(rs, "COUNTER"), state);
        castedState.base = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_BASE");
        castedState.root = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_ROOT");
    }
    protected pt.ist.fenixframework.dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("users")) return getRelationManagerContainsUsers().getInverseRelation();
        if (attrName.equals("session")) return getRelationManagerContainsSessions().getInverseRelation();
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("users", getRelationManagerContainsUsers().getInverseRelation());
        get$$relationList("session", getRelationManagerContainsSessions().getInverseRelation());
        
    }
    protected static class DO_State extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State {
        private int counter;
        private pt.tecnico.drivedb.domain.Dir base;
        private pt.ist.fenixframework.DomainRoot root;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.counter = this.counter;
            newCasted.base = this.base;
            newCasted.root = this.root;
            
        }
        
    }
    
}
