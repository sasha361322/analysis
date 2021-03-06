package security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cinimex.scheduler.security.entity.Authority;
import ru.cinimex.scheduler.security.entity.AuthorityName;

@Repository
public interface AuthorityRepository  extends JpaRepository<Authority, String > {
    @Query("select d from Authority d where d.code=:code")
    Authority getAuthorityByCode(@Param("code") AuthorityName code);
}
