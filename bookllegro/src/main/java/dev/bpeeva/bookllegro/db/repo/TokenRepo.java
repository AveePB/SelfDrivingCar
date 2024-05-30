package dev.bpeeva.bookllegro.db.repo;

import dev.bpeeva.bookllegro.db.model.Token;
import dev.bpeeva.bookllegro.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {

    Optional<Token> findByRawForm(String rawForm);
    Optional<Token> findByUser(User user);
}
