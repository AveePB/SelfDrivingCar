package dev.bpeeva.bookllegro.service;

import dev.bpeeva.bookllegro.db.model.User;
import dev.bpeeva.bookllegro.db.repo.UserRepo;
import dev.bpeeva.bookllegro.service.UserService;
import dev.bpeeva.bookllegro.util.enums.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTests {

    @Autowired
    private final UserRepo userRepo = null;

    @Autowired
    private final UserService userService = null;

    private User adam, ania;

    @BeforeEach
    void setUp() {
        adam = userRepo.save(new User(null, "adam234@gmail.com", "passwd", Account.CUSTOMER, true, null, null));
        ania = userRepo.save(new User(null, "an14@gmail.com", "dwssap", Account.SELLER, true, null, null));
    }

    @Test
    void shouldSuccessfullyFetchAUser() {
        //Arrange
        String username = "adam234@gmail.com";
        String username2 = "an14@gmail.com";

        //Act
        Optional<User> fetchedUser = userService.getUser(username);
        Optional<User> fetchedUser2 = userService.getUser(username2);

        //Assert
        assertThat(fetchedUser.isPresent()).isTrue();
        assertThat(fetchedUser2.isPresent()).isTrue();
    }

    @Test
    void shouldFailToFetchAUser() {
        //Arrange
        String username = "zofi4xD@gmail.com";
        String username2 = null;

        //Act
        Optional<User> fetchedUser = userService.getUser(username);
        Optional<User> fetchedUser2 = userService.getUser(username2);

        //Assert
        assertThat(fetchedUser.isPresent()).isFalse();
        assertThat(fetchedUser2.isPresent()).isFalse();
    }

    @Test
    void shouldSuccessfullySaveAUser() {
        //Arrange
        User user = new User(null, "x4v1er@onet.pl", "XDFOHIW#", Account.SELLER, true, null, null);
        User user2 = new User(null, "zv1er@wp.pl", "Xagsgasdg#$asHIW#", Account.CUSTOMER, true, null, null);

        //Act
        Optional<User> savedUser = userService.saveUser(user);
        Optional<User> savedUser2 = userService.saveUser(user2);

        //Assert
        assertThat(savedUser.isPresent()).isTrue();
        assertThat(savedUser2.isPresent()).isTrue();
    }

    @Test
    void shouldFailToSaveAUser() {
        //Arrange
        User user =  new User(null, "adam234@gmail.com", "Xagsgasdg#$asHIW#", Account.CUSTOMER, true, null, null);
        User user2 = new User(null, "zv1erwp.pl", "Xagsgasdg#$asHIW#", Account.CUSTOMER, true, null, null);

        //Act
        Optional<User> savedUser = userService.saveUser(user);
        Optional<User> savedUser2 = userService.saveUser(user2);

        //Assert
        assertThat(savedUser.isPresent()).isFalse();
        assertThat(savedUser2.isPresent()).isFalse();
    }

    @Test
    void shouldSuccessfullyUpdateAUsername() {
        //Arrange
        String newUsername1 = "grzegorz@wp.pl", newUsername2 = "zosik4@gmail.com";

        //Act
        userService.updateUsername(adam.getUsername(), newUsername1);
        userService.updateUsername(ania.getUsername(), newUsername2);

        //Assert
        assertThat(userRepo.findByUsername(newUsername1).isPresent()).isTrue();
        assertThat(userRepo.findByUsername(newUsername2).isPresent()).isTrue();
    }

    @Test
    void shouldFailToUpdateAUsername() {
        //Arrange
        String newUsername1 = "grzegorzwp.pl", newUsername2 = "adam234@gmail.com";

        //Act
        userService.updateUsername(adam.getUsername(), newUsername1);
        userService.updateUsername(ania.getUsername(), newUsername2);

        //Assert
        assertThat(userRepo.findByUsername(newUsername1).isPresent()).isFalse();
        assertThat(userRepo.findByUsername(ania.getUsername()).isPresent()).isTrue();
    }

    @Test
    void shouldSuccessfullyUpdateAUserPassword() {
        //Arrange
        String newPassword1 = "#$)G#*G$#)G", newPassword2 = "G)GTFD&*) TGFD";

        //Act
        userService.updatePassword(adam.getUsername(), newPassword1);
        userService.updatePassword(ania.getUsername(), newPassword2);

        String adamPassword = userRepo.findByUsername(adam.getUsername()).get().getPassword();
        String aniaPassword = userRepo.findByUsername(ania.getUsername()).get().getPassword();

        //Assert
        assertThat(adamPassword).isEqualTo(newPassword1);
        assertThat(aniaPassword).isEqualTo(newPassword2);
    }

    @Test
    void shouldFailToUpdateAUserPassword() {
        //Arrange
        String newPassword1 = "", newPassword2 = "";

        //Act
        userService.updatePassword(adam.getUsername(), newPassword1);
        userService.updatePassword(ania.getUsername(), newPassword2);

        String adamPassword = userRepo.findByUsername(adam.getUsername()).get().getPassword();
        String aniaPassword = userRepo.findByUsername(ania.getUsername()).get().getPassword();

        //Assert
        assertThat(adamPassword).isNotEqualTo(newPassword1);
        assertThat(aniaPassword).isNotEqualTo(newPassword2);
    }

    @Test
    void shouldSuccessfullyDeactivateAUser() {
        //Act
        userService.deactivate(adam.getUsername());
        userService.deactivate(ania.getUsername());

        boolean isAdamActive = userRepo.findByUsername(adam.getUsername()).get().isActive();
        boolean isAniaActive = userRepo.findByUsername(ania.getUsername()).get().isActive();

        //Assert
        assertThat(isAdamActive).isFalse();
        assertThat(isAniaActive).isFalse();
    }

    @Test
    void shouldFailToDeactivateAUser() {
        //Act
        userService.deactivate("zof1a@gmail.com");
        userService.deactivate("xanz@onet.pl");

        boolean isAdamActive = userRepo.findByUsername(adam.getUsername()).get().isActive();
        boolean isAniaActive = userRepo.findByUsername(ania.getUsername()).get().isActive();

        //Assert
        assertThat(isAdamActive).isTrue();
        assertThat(isAniaActive).isTrue();
    }

    @Test
    void shouldSuccessfullyLoadAUser() {
        //Act
        UserDetails adamDetails = userService.loadUserByUsername(adam.getUsername());
        UserDetails aniaDetails = userService.loadUserByUsername(ania.getUsername());

        //Assert
        assertThat(adamDetails.getUsername()).isEqualTo(adam.getUsername());
        assertThat(aniaDetails.getUsername()).isEqualTo(ania.getUsername());
    }

    @Test
    void shouldFailToLoadAUser() throws Exception {
        //Arrange
        String grzegorz = "grzegorzwp.pl", antek = "antek234@gmail.com";

        //Act
        try {
            UserDetails adamDetails = userService.loadUserByUsername(antek);
            throw new Exception("Should've thrown an error!!!");
        }
        //Assert
        catch (UsernameNotFoundException ex) {
            //Have thrown an error :D
        }

        //Act
        try {
            UserDetails aniaDetails = userService.loadUserByUsername(grzegorz);
            throw new Exception("Should've thrown an error!!!");
        }
        //Assert
        catch (UsernameNotFoundException ex) {
            //Have thrown an error :D
        }
    }
}
