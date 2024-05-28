package dev.bpeeva.bookllegro.db.repo;

import dev.bpeeva.bookllegro.db.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {

}
