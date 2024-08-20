package enigma.estore.repository;

import enigma.estore.model.Auditable;
import enigma.estore.utils.specification.NonDiscardedSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends Auditable, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    @Override
    @NonNull
    default List<T> findAll() {
        return findAll(NonDiscardedSpecification.notDiscarded());
    }

    @Override
    @NonNull
    default Page<T> findAll(@NonNull Pageable pageable) {
        return findAll(NonDiscardedSpecification.notDiscarded(), pageable);
    }

    @Override
    @NonNull
    default Optional<T> findById(@NonNull ID id) {
        return findOne((Specification<T>) NonDiscardedSpecification.<T>notDiscarded().and((root, query, cb) ->
                cb.equal(root.get("id"), id)));
    }

    @Override
    default void deleteById(@NonNull ID id) {
        softDelete(id);
    }

    @Modifying
    @Query("UPDATE #{#entityName} e SET e.discardedAt = :discardedAt WHERE e.id = :id")
    void softDelete(@Param("id") ID id, @Param("discardedAt") LocalDateTime discardedAt);

    default void softDelete(ID id) {
        softDelete(id, LocalDateTime.now());
    }
}
