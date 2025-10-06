package org.skypro.skyshop.controller;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

import org.skypro.skyshop.model.SearchResult;
import org.skypro.skyshop.service.SearchService;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collection;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.skypro.skyshop.model.basket.UserBasket;

@RestController
public class ShopController {

    private final StorageService storageService;
    private final SearchService searchService;
    private final BasketService basketService; // ← ДОБАВЛЯЕМ

    // Конструктор (Spring автоматически передаст StorageService)
    // Обновляем конструктор
    public ShopController(StorageService storageService, SearchService searchService, BasketService basketService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService=basketService;
    }

    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProducts();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticles();
    }

    //добавляем метод для поиска
    @GetMapping("/search")
    public Collection<SearchResult> search(@RequestParam String pattern) {
        return searchService.search(pattern);
    }

    // ★★★ НОВЫЕ МЕТОДЫ ДЛЯ КОРЗИНЫ ★★★

    // 1. Метод добавления продукта в корзину
    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        basketService.addProduct(id);
        return "Продукт успешно добавлен";
    }

    // 2. Метод отображения корзины
    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }

    //Добавил этот флаг т.к.нет
    @GetMapping("/")
    public String home() {
        return "Добро пожаловать в SkyShop! Используйте /products, /articles, /basket";
    }
}

