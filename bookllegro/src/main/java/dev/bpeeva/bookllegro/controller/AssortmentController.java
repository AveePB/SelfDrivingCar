package dev.bpeeva.bookllegro.controller;

import dev.bpeeva.bookllegro.db.model.Assortment;
import dev.bpeeva.bookllegro.db.model.User;
import dev.bpeeva.bookllegro.service.AssortmentService;
import dev.bpeeva.bookllegro.service.UserService;
import dev.bpeeva.bookllegro.util.dto.AssortmentDTO;
import dev.bpeeva.bookllegro.util.dto.request.UpdateAssortmentAmountReq;
import dev.bpeeva.bookllegro.util.dto.request.UpdateAssortmentPriceReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/assortment")
@RequiredArgsConstructor
public class AssortmentController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final AssortmentService assortmentService;

    @GetMapping
    private ResponseEntity<List<AssortmentDTO>> getAssortment() {

        return ResponseEntity.ok(assortmentService.getAssortments());
    }

    @PatchMapping("/change-price")
    private ResponseEntity<String> updateAssortmentPrice(@RequestBody UpdateAssortmentPriceReq req) {
        userService.loadUserByUsername(req.sellerName());
        User seller = userService.getUser(req.sellerName()).get();

        boolean isPriceUpdated = assortmentService.updatePrice(req.title(), req.price(), seller);
        if (!isPriceUpdated) //Failed to update
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/change-amount")
    private ResponseEntity<String> updateAssortmentAmount(@RequestBody UpdateAssortmentAmountReq req) {
        userService.loadUserByUsername(req.sellerName());
        User seller = userService.getUser(req.sellerName()).get();

        boolean isAmountUpdated = assortmentService.updateAmount(req.title(), req.amount(), seller);
        if (!isAmountUpdated) //Failed to update
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.ok().build();
    }

    @PostMapping
    private ResponseEntity<String> addAssortment(@RequestBody AssortmentDTO req) {
        userService.loadUserByUsername(req.sellerName());
        User seller = userService.getUser(req.sellerName()).get();

        boolean isAssortmentAdded = assortmentService.saveAssortment(Assortment.builder()
                .title(req.title())
                .price(req.price())
                .amount(req.amount())
                .seller(seller)
                .build());

        if (!isAssortmentAdded) //Failed to save
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
