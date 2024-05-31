package dev.bpeeva.bookllegro.service;

import dev.bpeeva.bookllegro.db.model.Assortment;
import dev.bpeeva.bookllegro.db.model.User;
import dev.bpeeva.bookllegro.db.repo.AssortmentRepo;
import dev.bpeeva.bookllegro.db.repo.UserRepo;
import dev.bpeeva.bookllegro.service.AssortmentService;
import dev.bpeeva.bookllegro.util.dto.AssortmentDTO;
import dev.bpeeva.bookllegro.util.enums.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AssortmentServiceTests {

    @Autowired
    private final UserRepo userRepo = null;
    @Autowired
    private final AssortmentRepo assortmentRepo = null;

    @Autowired
    private final AssortmentService assortmentService = null;

    private User tolkienCorporation, lemCorporation;
    private Assortment hobbit, lordOfTheRings;

    @BeforeEach
    void setUp() {
        tolkienCorporation = new User(null, "tolkien corp.", "", Account.SELLER, true, null, null);
        tolkienCorporation = userRepo.save(tolkienCorporation);

        lemCorporation = new User(null, "LEM corp.", "", Account.SELLER, true, null, null);
        lemCorporation = userRepo.save(lemCorporation);

        hobbit = new Assortment(null, "Hobbit", 12.4, 230, tolkienCorporation);
        hobbit = assortmentRepo.save(hobbit);

        lordOfTheRings = new Assortment(null, "Lord Of the Rings", 45.9, 1300, tolkienCorporation);
        lordOfTheRings = assortmentRepo.save(lordOfTheRings);
    }

    @Test
    void shouldSuccessfullyGetAllAssortments() {
        //Act
        List<AssortmentDTO> assortmentDTOList = assortmentService.getAssortments();

        //Assert
        assertThat(assortmentDTOList.size()).isEqualTo(2);
    }

    @Test
    void shouldSuccessfullyGetAllAssortmentsByTheSeller() {
        //Act
        List<AssortmentDTO> tolkienBooks = assortmentService.getAssortments(tolkienCorporation);
        List<AssortmentDTO> lemBooks = assortmentService.getAssortments(lemCorporation);

        //Assert
        assertThat(tolkienBooks.size()).isEqualTo(2);
        assertThat(lemBooks.size()).isEqualTo(0);
    }

    @Test
    void shouldSuccessfullyUpdateTheBookPrice() {
        //Act
        boolean isHobbitPriceUpdated = assortmentService.updatePrice(hobbit.getTitle(), 15.73, tolkienCorporation);
        boolean isLotrPriceUpdated = assortmentService.updatePrice(lordOfTheRings.getTitle(), 123.43, tolkienCorporation);

        //Assert
        assertThat(isHobbitPriceUpdated).isTrue();
        assertThat(isLotrPriceUpdated).isTrue();
    }

    @Test
    void shouldFailToUpdateTheBookPrice() {
        //Act
        boolean isHobbitPriceUpdated = assortmentService.updatePrice(hobbit.getTitle(), 15.73, lemCorporation);
        boolean isLotrPriceUpdated = assortmentService.updatePrice("lotr", 123.43, tolkienCorporation);
        boolean isLotrPriceUpdated2 = assortmentService.updatePrice(lordOfTheRings.getTitle(), -123.43, tolkienCorporation);

        //Assert
        assertThat(isHobbitPriceUpdated).isFalse();
        assertThat(isLotrPriceUpdated).isFalse();
        assertThat(isLotrPriceUpdated2).isFalse();
    }

    @Test
    void shouldSuccessfullyUpdateTheBookAmount() {
        //Act
        boolean isHobbitAmountUpdated = assortmentService.updateAmount(hobbit.getTitle(), 15, tolkienCorporation);
        boolean isLotrAmountUpdated = assortmentService.updateAmount(lordOfTheRings.getTitle(), 120, tolkienCorporation);

        //Assert
        assertThat(isHobbitAmountUpdated).isTrue();
        assertThat(isLotrAmountUpdated).isTrue();
    }

    @Test
    void shouldFailToUpdateTheBookAmount() {
        //Act
        boolean isHobbitAmountUpdated = assortmentService.updateAmount(hobbit.getTitle(), 15, lemCorporation);
        boolean isLotrAmountUpdated = assortmentService.updateAmount("lotr", 123, tolkienCorporation);
        boolean isLotrAmountUpdated2 = assortmentService.updateAmount(lordOfTheRings.getTitle(), -123, tolkienCorporation);

        //Assert
        assertThat(isHobbitAmountUpdated).isFalse();
        assertThat(isLotrAmountUpdated).isFalse();
        assertThat(isLotrAmountUpdated2).isFalse();
    }

    @Test
    void shouldSuccessfullySaveAnAssortment() {
        //Arrange
        Assortment silmarillion = new Assortment(null, "Silmarillion", 43.5, 23, tolkienCorporation);

        //Act
        boolean isSilmarillionSaved = assortmentService.saveAssortment(silmarillion);

        //Assert
        assertThat(isSilmarillionSaved).isTrue();
    }

    @Test
    void shouldFailToSaveAnAssortment() {
        //Arrange
        Assortment hobbitCopy = new Assortment(null, hobbit.getTitle(), 23.24, 123, tolkienCorporation);
        Assortment solaris = new Assortment(null, "Solaris", -23.321, 32, lemCorporation);
        Assortment kongresFuturologiczny = new Assortment(null, "Kongres Futurologiczny", 18.4, -10, lemCorporation);

        //Act
        boolean isHobbitSaved = assortmentService.saveAssortment(hobbitCopy);
        boolean isSolarisSaved = assortmentService.saveAssortment(solaris);
        boolean isKongresFuturologicznySaved = assortmentService.saveAssortment(kongresFuturologiczny);

        //Assert
        assertThat(isHobbitSaved).isFalse();
        assertThat(isSolarisSaved).isFalse();
        assertThat(isKongresFuturologicznySaved).isFalse();
    }

    @Test
    void shouldSuccessfullyRemoveAnAssortment() {
        //Act
        assortmentService.remove(hobbit.getTitle(), tolkienCorporation);
        assortmentService.remove(lordOfTheRings.getTitle(), tolkienCorporation);

        //Assert
        assertThat(assortmentRepo.count()).isEqualTo(0);
    }


    @Test
    void shouldTryToDeleteANonExistingBook() {
        //Act
        assortmentService.remove("Kongres Futurologiczny", lemCorporation);
        assortmentService.remove("Silmarillion", tolkienCorporation);
        
        //Assert
        assertThat(assortmentRepo.count()).isEqualTo(2);
    }
}
