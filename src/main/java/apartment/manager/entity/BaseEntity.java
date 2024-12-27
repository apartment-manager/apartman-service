package apartment.manager.entity;

import apartment.manager.entity.utils.BaseEntityListener;
import jakarta.persistence.*;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.Date;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
@FilterDef(name = "userFilter", defaultCondition = BaseEntity.CREATED_BY_DATABASE_PROPERTY + "  = :createdBy", parameters = @ParamDef(name = "createdBy", type = Long.class))
public class BaseEntity {
    public static final String ID_FIELD_NAME = "id";
    public static final String CREATED_BY_DATABASE_PROPERTY = "created_by";
    public static final String CREATED_BY_PROPERTY = "createdBy";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column
    Date createDate;
    @Column
    Date modifiedDate;
    @Column(name = CREATED_BY_DATABASE_PROPERTY, nullable = false)
    Long createdBy;
    @Version
    Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
