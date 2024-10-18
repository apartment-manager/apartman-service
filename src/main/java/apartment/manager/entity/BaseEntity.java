package apartment.manager.entity;

import apartment.manager.entity.utils.BaseEntityListener;
import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {

    public static final String ID_FIELD_NAME = "id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;
    @Column
    Date createDate;
    @Column
    Date modifiedDate;
    @Column
    Long userId;
    @Version // TODO: learn how to use it
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
