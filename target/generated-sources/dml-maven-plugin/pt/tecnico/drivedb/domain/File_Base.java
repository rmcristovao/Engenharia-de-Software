package pt.tecnico.drivedb.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class File_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.User> role$$owner = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.User>() {
        @Override
        public pt.tecnico.drivedb.domain.User getValue(pt.tecnico.drivedb.domain.File o1) {
            return ((File_Base.DO_State)o1.get$obj$state(false)).owner;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.File o1, pt.tecnico.drivedb.domain.User o2) {
            ((File_Base.DO_State)o1.get$obj$state(true)).owner = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.File> getInverseRole() {
            return pt.tecnico.drivedb.domain.User.role$$files;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.Dir> role$$dir = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.Dir>() {
        @Override
        public pt.tecnico.drivedb.domain.Dir getValue(pt.tecnico.drivedb.domain.File o1) {
            return ((File_Base.DO_State)o1.get$obj$state(false)).dir;
        }
        @Override
        public void setValue(pt.tecnico.drivedb.domain.File o1, pt.tecnico.drivedb.domain.Dir o2) {
            ((File_Base.DO_State)o1.get$obj$state(true)).dir = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.drivedb.domain.Dir,pt.tecnico.drivedb.domain.File> getInverseRole() {
            return pt.tecnico.drivedb.domain.Dir.role$$files;
        }
        
    };
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.User,pt.tecnico.drivedb.domain.File> getRelationUserContainsFiles() {
        return pt.tecnico.drivedb.domain.User.getRelationUserContainsFiles();
    }
    
    private final static class DirContainsFiles {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.Dir> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.Dir>(role$$dir, "DirContainsFiles");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.drivedb.domain.File,pt.tecnico.drivedb.domain.Dir> getRelationDirContainsFiles() {
        return DirContainsFiles.relation;
    }
    
    static {
        DirContainsFiles.relation.setRelationName("pt.tecnico.drivedb.domain.File.DirContainsFiles");
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
    protected  File_Base() {
        super();
    }
    
    // Getters and Setters
    
    public int getId() {
        return ((DO_State)this.get$obj$state(false)).id;
    }
    
    public void setId(int id) {
        ((DO_State)this.get$obj$state(true)).id = id;
    }
    
    private int get$id() {
        int value = ((DO_State)this.get$obj$state(false)).id;
        return pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$id(int value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).id = (int)(value);
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
    
    public org.joda.time.DateTime getLastModified() {
        return ((DO_State)this.get$obj$state(false)).lastModified;
    }
    
    public void setLastModified(org.joda.time.DateTime lastModified) {
        ((DO_State)this.get$obj$state(true)).lastModified = lastModified;
    }
    
    private java.sql.Timestamp get$lastModified() {
        org.joda.time.DateTime value = ((DO_State)this.get$obj$state(false)).lastModified;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForDateTime(value);
    }
    
    private final void set$lastModified(org.joda.time.DateTime value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).lastModified = (org.joda.time.DateTime)((value == null) ? null : value);
    }
    
    public java.lang.String getPermissions() {
        return ((DO_State)this.get$obj$state(false)).permissions;
    }
    
    public void setPermissions(java.lang.String permissions) {
        ((DO_State)this.get$obj$state(true)).permissions = permissions;
    }
    
    private java.lang.String get$permissions() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).permissions;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$permissions(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).permissions = (java.lang.String)((value == null) ? null : value);
    }
    
    // Role Methods
    
    public pt.tecnico.drivedb.domain.User getOwner() {
        return ((DO_State)this.get$obj$state(false)).owner;
    }
    
    public void setOwner(pt.tecnico.drivedb.domain.User owner) {
        getRelationUserContainsFiles().add(owner, (pt.tecnico.drivedb.domain.File)this);
    }
    
    private java.lang.Long get$oidOwner() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).owner;
        return (value == null) ? null : value.getOid();
    }
    
    public pt.tecnico.drivedb.domain.Dir getDir() {
        return ((DO_State)this.get$obj$state(false)).dir;
    }
    
    public void setDir(pt.tecnico.drivedb.domain.Dir dir) {
        getRelationDirContainsFiles().add((pt.tecnico.drivedb.domain.File)this, dir);
    }
    
    private java.lang.Long get$oidDir() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).dir;
        return (value == null) ? null : value.getOid();
    }
    
    
    protected void checkDisconnected() {
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.owner != null) handleAttemptToDeleteConnectedObject("Owner");
        if (castedState.dir != null) handleAttemptToDeleteConnectedObject("Dir");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$id(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readint(rs, "ID"), state);
        set$name(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "NAME"), state);
        set$lastModified(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDateTime(rs, "LAST_MODIFIED"), state);
        set$permissions(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "PERMISSIONS"), state);
        castedState.owner = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_OWNER");
        castedState.dir = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_DIR");
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
        private int id;
        private java.lang.String name;
        private org.joda.time.DateTime lastModified;
        private java.lang.String permissions;
        private pt.tecnico.drivedb.domain.User owner;
        private pt.tecnico.drivedb.domain.Dir dir;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.id = this.id;
            newCasted.name = this.name;
            newCasted.lastModified = this.lastModified;
            newCasted.permissions = this.permissions;
            newCasted.owner = this.owner;
            newCasted.dir = this.dir;
            
        }
        
    }
    
}
