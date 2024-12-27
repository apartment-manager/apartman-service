package apartment.manager.entity.utils;

import apartment.manager.Utilities.JwtAuthenticationFilter;
import apartment.manager.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class BaseEntityListener {

    @Autowired
    HttpSession session;

    @PrePersist
    public void prePersist(BaseEntity baseEntity) {
        Date now = new Date();
        baseEntity.setCreateDate(now);
        baseEntity.setModifiedDate(now);  // When the entity was first created
        baseEntity.setCreatedBy((Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE));
    }

    @PreUpdate
    public void preUpdate(BaseEntity baseEntity) {
        baseEntity.setModifiedDate(new Date());
    }
}
