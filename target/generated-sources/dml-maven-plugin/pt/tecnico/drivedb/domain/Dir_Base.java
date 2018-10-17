package pt.tecnico.drivedb.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class Dir_Base extends pt.tecnico.drivedb.domain.File {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Manager> role$$manager = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Manager>() {
        @Override
        public pt.tecnico.drivedb.domain.Manager getValue(pt.tecnico.drivedb.domain.Dir o1) {
            return ((Dir_Base.DO_State)o1.get$obj$state(false)).manager;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.Dir o1, pt.tecnico.drivedb.domain.Manager o2) {
            ((Dir_Base.DO_State)o1.get$obj$state(true)).manager = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Dir> getInverseRole() {
            return pt.tecnico.drivedb.domain.Manager.role$$base;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Session> role$$session = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Session>() {
        @Override
        public pt.tecnico.drivedb.domain.Session getValue(pt.tecnico.drivedb.domain.Dir o1) {
            return ((Dir_Base.DO_State)o1.get$obj$state(false)).session;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.Dir o1, pt.tecnico.drivedb.domain.Session o2) {
            ((Dir_Base.DO_State)o1.get$obj$state(true)).session = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.Dir> getInverseRole() {
            return pt.tecnico.drivedb.domain.Session.role$$workingDir;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.File> role$$files = new pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.File>() {
        public pt.ist.fenixframework.dml.runtime.RelationBaseSet<pt.tecnico.drivedb.domain.File> getSet(pt.tecnico.drivedb.domain.Dir o1) {
            return ((Dir_Base)o1).get$rl$files();
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.Dir> getInverseRole() {
            return pt.tecnico.drivedb.domain.File.role$$dir;
        }
        
    };
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.Dir> getRelationManagerContainsDir() {
        return pt.tecnico.drivedb.domain.Manager.getRelationManagerContainsDir();
    }
    
    private final static class SessionContainsDir {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Session> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Session>(role$$session, "SessionContainsDir");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.Session> getRelationSessionContainsDir() {
        return SessionContainsDir.relation;
    }
    
    static {
        SessionContainsDir.relation.setRelationName("pt.tecnico.drivedb.domain.Dir.SessionContainsDir");
    }
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.Dir> getRelationDirContainsFiles() {
        return pt.tecnico.drivedb.domain.File.getRelationDirContainsFiles();
    }
    private static pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.File> keyFunction$$files = new pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.File>() { public Comparable<?> getKey(pt.tecnico.drivedb.domain.File value) { return value.getOid(); } public boolean allowMultipleKeys() {return false; }};
    
    // Slots
    
    // Role Slots
    private RelationList<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.File> get$rl$files() {
        return get$$relationList("files", getRelationDirContainsFiles().getInverseRelation());
        
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
    protected  Dir_Base() {
        super();
    }
    
    // Getters and Setters
    
    // Role Methods
    
    public pt.tecnico.drivedb.domain.Manager getManager() {
        return ((DO_State)this.get$obj$state(false)).manager;
    }
    
    public void setManager(pt.tecnico.drivedb.domain.Manager manager) {
        getRelationManagerContainsDir().add(manager, (pt.tecnico.drivedb.domain.Dir)this);
    }
    
    private java.lang.Long get$oidManager() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).manager;
        return (value == null) ? null : value.getOid();
    }
    
    public pt.tecnico.drivedb.domain.Session getSession() {
        return ((DO_State)this.get$obj$state(false)).session;
    }
    
    public void setSession(pt.tecnico.drivedb.domain.Session session) {
        getRelationSessionContainsDir().add((pt.tecnico.drivedb.domain.Dir)this, session);
    }
    
    private java.lang.Long get$oidSession() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).session;
        return (value == null) ? null : value.getOid();
    }
    
    public void addFiles(pt.tecnico.drivedb.domain.File files) {
        getRelationDirContainsFiles().add(files, (pt.tecnico.drivedb.domain.Dir)this);
    }
    
    public void removeFiles(pt.tecnico.drivedb.domain.File files) {
        getRelationDirContainsFiles().remove(files, (pt.tecnico.drivedb.domain.Dir)this);
    }
    
    public java.util.Set<pt.tecnico.drivedb.domain.File> getFilesSet() {
        return get$rl$files();
    }
    
    public void set$files(OJBFunctionalSetWrapper files) {
        get$rl$files().setFromOJB(this, "files", files);
    }
    
    @Deprecated
    public java.util.Set<pt.tecnico.drivedb.domain.File> getFiles() {
        return getFilesSet();
    }
    
    @Deprecated
    public int getFilesCount() {
        return getFilesSet().size();
    }
    
    
    protected void checkDisconnected() {
        super.checkDisconnected();
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.manager != null) handleAttemptToDeleteConnectedObject("Manager");
        if (castedState.session != null) handleAttemptToDeleteConnectedObject("Session");
        if (get$rl$files().size() > 0) handleAttemptToDeleteConnectedObject("Files");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        super.readStateFromResultSet(rs, state);
        DO_State castedState = (DO_State)state;
        castedState.manager = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_MANAGER");
        castedState.session = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_SESSION");
    }
    protected pt.ist.fenixframework.dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("files")) return getRelationDirContainsFiles().getInverseRelation();
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("files", getRelationDirContainsFiles().getInverseRelation());
        
    }
    protected static class DO_State extends pt.tecnico.drivedb.domain.File.DO_State {
        private pt.tecnico.drivedb.domain.Manager manager;
        private pt.tecnico.drivedb.domain.Session session;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.manager = this.manager;
            newCasted.session = this.session;
            
        }
        
    }
    
}
