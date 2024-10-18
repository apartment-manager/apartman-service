package apartment.manager.entity.utils;

import apartment.manager.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Date;

public class BaseEntityListener {

    @PrePersist
    public void prePersist(BaseEntity baseEntity) {
        Date now = new Date();
        baseEntity.setCreateDate(now);
        baseEntity.setModifiedDate(now);  // When the entity is first created
        baseEntity.setUserId(1L);  // TODO: customize the logic for setting userId
    }

    @PreUpdate
    public void preUpdate(BaseEntity baseEntity) {
        baseEntity.setModifiedDate(new Date());
    }
}
