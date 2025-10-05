package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        Product product = storageService.getProductById(id)
                .orElseThrow(() -> new NoSuchProductException(
                        "Товар с ID " + id + " снят с производства и распродан"
                ));
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        var basketMap = productBasket.getProducts();

        List<BasketItem> items = basketMap.entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();

                    Product product = storageService.getProductById(productId)
                            .orElseThrow(() -> new NoSuchProductException(
                                    "Товар с ID " + productId + " снят с производства и распродан"
                            ));

                    return new BasketItem(product, quantity);
                })
                .collect(Collectors.toList());

        return new UserBasket(items);
    }
}