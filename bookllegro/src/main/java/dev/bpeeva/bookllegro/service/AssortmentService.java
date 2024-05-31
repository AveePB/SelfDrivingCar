package dev.bpeeva.bookllegro.service;

import dev.bpeeva.bookllegro.db.model.Assortment;
import dev.bpeeva.bookllegro.db.model.User;
import dev.bpeeva.bookllegro.db.repo.AssortmentRepo;
import dev.bpeeva.bookllegro.util.dto.AssortmentDTO;
import dev.bpeeva.bookllegro.util.enums.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssortmentService {

    @Autowired
    private final AssortmentRepo assortmentRepo;

    /**
     * Returns all assortments in the bookllegro
     * @return list object
     */
    public List<AssortmentDTO> getAssortments() {

        return assortmentRepo.findAll().stream()
                .map(a -> new AssortmentDTO(
                        a.getTitle(),
                        a.getPrice(),
                        a.getAmount(),
                        a.getSeller().getUsername()
                )).toList();
    }

    /**
     * Returns all assortments offered by the seller
     * @param seller the seller
     * @return list object
     */
    public List<AssortmentDTO> getAssortments(User seller) {

        return assortmentRepo.findAllBySeller(seller).stream()
                .map(a -> new AssortmentDTO(
                        a.getTitle(),
                        a.getPrice(),
                        a.getAmount(),
                        seller.getUsername()
                )).toList();
    }

    /**
     * Updates the price of offered assortment
     * @param title book title
     * @param newPrice new price
     * @param seller the seller
     * @return boolean value
     */
    public boolean updatePrice(String title, Double newPrice, User seller) {
        if (newPrice <= 0) return false;

        Optional<Assortment> assortment = assortmentRepo.findByTitleAndSeller(title, seller);
        if (assortment.isEmpty()) return false;

        assortment.get().setPrice(newPrice);
        Assortment savedAssortment = assortmentRepo.save(assortment.get());
        if (savedAssortment == null) return false;

        return true;
    }

    /**
     * Updates the price of offered assortment
     * @param title book title
     * @param amount new amount
     * @param seller the seller
     * @return boolean value
     */
    public boolean updateAmount(String title, Integer amount, User seller) {
        if (amount < 0) return false;

        Optional<Assortment> assortment = assortmentRepo.findByTitleAndSeller(title, seller);
        if (assortment.isEmpty()) return false;

        assortment.get().setAmount(amount);
        Assortment savedAssortment = assortmentRepo.save(assortment.get());
        if (savedAssortment == null) return false;

        return true;
    }

    /**
     * Saves a new assortment object to the database
     * @param assortment assortment
     * @return optional object
     */
    public boolean saveAssortment(Assortment assortment) {
        //Book title is taken
        if (assortmentRepo.findByTitleAndSeller(assortment.getTitle(),
                assortment.getSeller()).isPresent())
            return false;

        if (assortment.getPrice() <= 0) return false;
        if (assortment.getAmount() < 0) return false;

        Assortment savedAssortment = assortmentRepo.save(assortment);
        if (savedAssortment == null) return false;

        return true;
    }

    /**
     * Removes products from the bookllegro
     * @param title book title
     * @param seller the seller
     */
    public void remove(String title, User seller) {
        Optional<Assortment> assortment = assortmentRepo.findByTitleAndSeller(title, seller);
        if (assortment.isEmpty()) return;

        assortmentRepo.delete(assortment.get());
    }
}
