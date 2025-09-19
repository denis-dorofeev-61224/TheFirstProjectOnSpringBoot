package org.skypro.skyshop.service;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.springframework.stereotype.Service;

import org.skypro.skyshop.model.search.Searchable;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> productStorage = new HashMap<>();
    private final Map<UUID, Article> articleStorage = new HashMap<>();

    public StorageService() {
        fillStorageWithTestData(); // Заполняем хранилище тестовыми данными
    }

    private void fillStorageWithTestData() {
        // Создаем тестовые продукты (как в старом main)
        Product bag1 = new SimpleProduct(
                UUID.randomUUID(),
                "Женская сумка ShellBlack",
                9900
        );
        Product bag2 = new DiscountedProduct(
                UUID.randomUUID(),
                "Женская сумка HoboBeige",
                8500,
                15
        );
        Product bag3 = new SimpleProduct(
                UUID.randomUUID(),
                "Сумка для телефона NipperBlue",
                8000
        );

        // Добавляем продукты в хранилище
        productStorage.put(bag1.getId(), bag1);
        productStorage.put(bag2.getId(), bag2);
        productStorage.put(bag3.getId(), bag3);

        // Создаем тестовые статьи (как в старом main)
        Article article1 = new Article(
                UUID.randomUUID(),
                "Новинка!",
                "Встречаем сумку-ракушку со дна океана!!!"
        );
        Article article2 = new Article(
                UUID.randomUUID(),
                "Новинка!",
                "Встречаем сумку-лукошко. Для любителей торб."
        );

        // Добавляем статьи в хранилище
        articleStorage.put(article1.getId(), article1);
        articleStorage.put(article2.getId(), article2);
    }

    // Метод для получения всех продуктов
    public Collection<Product> getAllProducts() {
        return productStorage.values();
    }

    // Метод для получения всех статей
    public Collection<Article> getAllArticles() {
        return articleStorage.values();
    }

    // НОВЫЙ МЕТОД для получения всех Searchable
    public Collection<Searchable> getAllSearchable() {
        return Stream.concat(
                productStorage.values().stream(),
                articleStorage.values().stream()
        ).collect(Collectors.toList());
    }
}