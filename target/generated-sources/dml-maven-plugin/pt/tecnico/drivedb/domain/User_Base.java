package pt.tecnico.drivedb.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class User_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Manager> role$$manager = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Manager>() {
        @Override
        public pt.tecnico.drivedb.domain.Manager getValue(pt.tecnico.drivedb.domain.User o1) {
            return ((User_Base.DO_State)o1.get$obj$state(false)).manager;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.User o1, pt.tecnico.drivedb.domain.Manager o2) {
            ((User_Base.DO_State)o1.get$obj$state(true)).manager = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Manager,pt.tecnico.drivedb.domain.User> getInverseRole() {
            return pt.tecnico.drivedb.domain.Manager.role$$users;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Session> role$$session = new pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Session>() {
        public pt.ist.fenixframework.dml.runtime.RelationBaseSet<pt.tecnico.drivedb.domain.Session> getSet(pt.tecnico.drivedb.domain.User o1) {
            return ((User_Base)o1).get$rl$session();
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.User> getInverseRole() {
            return pt.tecnico.drivedb.domain.Session.role$$currentUser;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.File> role$$files = new pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.File>() {
        public pt.ist.fenixframework.dml.runtime.RelationBaseSet<pt.tecnico.drivedb.domain.File> getSet(pt.tecnico.drivedb.domain.User o1) {
            return ((User_Base)o1).get$rl$files();
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.User> getInverseRole() {
            return pt.tecnico.drivedb.domain.File.role$$owner;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Extension> role$$extension = new pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Extension>() {
        public pt.ist.fenixframework.dml.runtime.RelationBaseSet<pt.tecnico.drivedb.domain.Extension> getSet(pt.tecnico.drivedb.domain.User o1) {
            return ((User_Base)o1).get$rl$extension();
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Extension,pt.tecnico.drivedb.domain.User> getInverseRole() {
            return pt.tecnico.drivedb.domain.Extension.role$$user;
        }
        
    };
    
    private final static class ManagerContainsUsers {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Manager> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Manager>(role$$manager, "ManagerContainsUsers");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Manager> getRelationManagerContainsUsers() {
        return ManagerContainsUsers.relation;
    }
    
    static {
        ManagerContainsUsers.relation.setRelationName("pt.tecnico.drivedb.domain.User.ManagerContainsUsers");
    }
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.Session,pt.tecnico.drivedb.domain.User> getRelationSessionContainsUser() {
        return pt.tecnico.drivedb.domain.Session.getRelationSessionContainsUser();
    }
    private static pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.Session> keyFunction$$session = new pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.Session>() { public Comparable<?> getKey(pt.tecnico.drivedb.domain.Session value) { return value.getOid(); } public boolean allowMultipleKeys() {return false; }};
    
    private final static class UserContainsFiles {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.File> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.File>(role$$files, "UserContainsFiles");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.File> getRelationUserContainsFiles() {
        return UserContainsFiles.relation;
    }
    
    static {
        UserContainsFiles.relation.setRelationName("pt.tecnico.drivedb.domain.User.UserContainsFiles");
    }
    private static pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.File> keyFunction$$files = new pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.File>() { public Comparable<?> getKey(pt.tecnico.drivedb.domain.File value) { return value.getOid(); } public boolean allowMultipleKeys() {return false; }};
    
    private final static class UserHasExtension {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Extension> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Extension>(role$$extension, "UserHasExtension");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Extension> getRelationUserHasExtension() {
        return UserHasExtension.relation;
    }
    
    static {
        UserHasExtension.relation.setRelationName("pt.tecnico.drivedb.domain.User.UserHasExtension");
    }
    private static pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.Extension> keyFunction$$extension = new pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.drivedb.domain.Extension>() { public Comparable<?> getKey(pt.tecnico.drivedb.domain.Extension value) { return value.getOid(); } public boolean allowMultipleKeys() {return false; }};
    
    // Slots
    
    // Role Slots
    private RelationList<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Session> get$rl$session() {
        return get$$relationList("session", getRelationSessionContainsUser().getInverseRelation());
        
    }
    private RelationList<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.File> get$rl$files() {
        return get$$relationList("files", getRelationUserContainsFiles());
        
    }
    private RelationList<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.Extension> get$rl$extension() {
        return get$$relationList("extension", getRelationUserHasExtension());
        
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
    protected  User_Base() {
        super();
    }
    
    // Getters and Setters
    
    public java.lang.String getUsername() {
        return ((DO_State)this.get$obj$state(false)).username;
    }
    
    public void setUsername(java.lang.String username) {
        ((DO_State)this.get$obj$state(true)).username = username;
    }
    
    private java.lang.String get$username() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).username;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$username(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).username = (java.lang.String)((value == null) ? null : value);
    }
    
    public java.lang.String getPassword() {
        return ((DO_State)this.get$obj$state(false)).password;
    }
    
    public void setPassword(java.lang.String password) {
        ((DO_State)this.get$obj$state(true)).password = password;
    }
    
    private java.lang.String get$password() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).password;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$password(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).password = (java.lang.String)((value == null) ? null : value);
    }
    
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
    
    public java.lang.String getMask() {
        return ((DO_State)this.get$obj$state(false)).mask;
    }
    
    public void setMask(java.lang.String mask) {
        ((DO_State)this.get$obj$state(true)).mask = mask;
    }
    
    private java.lang.String get$mask() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).mask;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$mask(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).mask = (java.lang.String)((value == null) ? null : value);
    }
    
    public java.lang.String getPathHome() {
        return ((DO_State)this.get$obj$state(false)).pathHome;
    }
    
    public void setPathHome(java.lang.String pathHome) {
        ((DO_State)this.get$obj$state(true)).pathHome = pathHome;
    }
    
    private java.lang.String get$pathHome() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).pathHome;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$pathHome(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).pathHome = (java.lang.String)((value == null) ? null : value);
    }
    
    // Role Methods
    
    public pt.tecnico.drivedb.domain.Manager getManager() {
        return ((DO_State)this.get$obj$state(false)).manager;
    }
    
    public void setManager(pt.tecnico.drivedb.domain.Manager manager) {
        getRelationManagerContainsUsers().add((pt.tecnico.drivedb.domain.User)this, manager);
    }
    
    private java.lang.Long get$oidManager() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).manager;
        return (value == null) ? null : value.getOid();
    }
    
    public void addSession(pt.tecnico.drivedb.domain.Session session) {
        getRelationSessionContainsUser().add(session, (pt.tecnico.drivedb.domain.User)this);
    }
    
    public void removeSession(pt.tecnico.drivedb.domain.Session session) {
        getRelationSessionContainsUser().remove(session, (pt.tecnico.drivedb.domain.User)this);
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
    
    public void addFiles(pt.tecnico.drivedb.domain.File files) {
        getRelationUserContainsFiles().add((pt.tecnico.drivedb.domain.User)this, files);
    }
    
    public void removeFiles(pt.tecnico.drivedb.domain.File files) {
        getRelationUserContainsFiles().remove((pt.tecnico.drivedb.domain.User)this, files);
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
    
    public void addExtension(pt.tecnico.drivedb.domain.Extension extension) {
        getRelationUserHasExtension().add((pt.tecnico.drivedb.domain.User)this, extension);
    }
    
    public void removeExtension(pt.tecnico.drivedb.domain.Extension extension) {
        getRelationUserHasExtension().remove((pt.tecnico.drivedb.domain.User)this, extension);
    }
    
    public java.util.Set<pt.tecnico.drivedb.domain.Extension> getExtensionSet() {
        return get$rl$extension();
    }
    
    public void set$extension(OJBFunctionalSetWrapper extension) {
        get$rl$extension().setFromOJB(this, "extension", extension);
    }
    
    @Deprecated
    public java.util.Set<pt.tecnico.drivedb.domain.Extension> getExtension() {
        return getExtensionSet();
    }
    
    @Deprecated
    public int getExtensionCount() {
        return getExtensionSet().size();
    }
    
    
    protected void checkDisconnected() {
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.manager != null) handleAttemptToDeleteConnectedObject("Manager");
        if (get$rl$session().size() > 0) handleAttemptToDeleteConnectedObject("Session");
        if (get$rl$files().size() > 0) handleAttemptToDeleteConnectedObject("Files");
        if (get$rl$extension().size() > 0) handleAttemptToDeleteConnectedObject("Extension");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$username(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "USERNAME"), state);
        set$password(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "PASSWORD"), state);
        set$name(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "NAME"), state);
        set$mask(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "MASK"), state);
        set$pathHome(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "PATH_HOME"), state);
        castedState.manager = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_MANAGER");
    }
    protected pt.ist.fenixframework.dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("session")) return getRelationSessionContainsUser().getInverseRelation();
        if (attrName.equals("files")) return getRelationUserContainsFiles();
        if (attrName.equals("extension")) return getRelationUserHasExtension();
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("session", getRelationSessionContainsUser().getInverseRelation());
        get$$relationList("files", getRelationUserContainsFiles());
        get$$relationList("extension", getRelationUserHasExtension());
        
    }
    protected static class DO_State extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State {
        private java.lang.String username;
        private java.lang.String password;
        private java.lang.String name;
        private java.lang.String mask;
        private java.lang.String pathHome;
        private pt.tecnico.drivedb.domain.Manager manager;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.username = this.username;
            newCasted.password = this.password;
            newCasted.name = this.name;
            newCasted.mask = this.mask;
            newCasted.pathHome = this.pathHome;
            newCasted.manager = this.manager;
            
        }
        
    }
    
}
