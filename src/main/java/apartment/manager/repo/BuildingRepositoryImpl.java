package apartment.manager.repo;

import apartment.manager.entity.Apartment;
import apartment.manager.entity.BaseEntity;
import apartment.manager.entity.Building;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public abstract class BuildingRepositoryImpl implements BuildingRepository {
    @Autowired
    private EntityManager entityManager;

    /**
     * Criteria API implementation
     * @param id
     * @return
     */
    @Override
    public Optional<Building> findById(@NotNull Long id){
       CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Building> query = criteriaBuilder.createQuery(Building.class);
        Root<Building> buildingRoot = query.from(Building.class);
        Join<Building, Apartment> apartmentJoin = buildingRoot.join(Apartment.APARTMENT_TABLE_NAME);
        query.multiselect(buildingRoot,criteriaBuilder.count(apartmentJoin.get(BaseEntity.ID_FIELD_NAME)).alias(Building.APARTMENT_COUNT_FIELD_NAME));
        query.where(criteriaBuilder.and(criteriaBuilder.equal(buildingRoot.get(BaseEntity.ID_FIELD_NAME), id)));
        TypedQuery<Building> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultStream().findFirst();
    }
}
