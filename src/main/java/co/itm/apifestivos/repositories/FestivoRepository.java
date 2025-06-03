package co.itm.apifestivos.repositories;

import co.itm.apifestivos.entities.Festivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivoRepository extends JpaRepository<Festivo, Long> {
}
