package org.skypro.skyshop.model.product;
import org.skypro.skyshop.model.search.Searchable;
import java.util.UUID; // ← ДОБАВЛЯЕМ ИМПОРТ

import java.util.Objects;//добавляем импорт без него не работает))


public class Product implements Searchable {
    private final String nameOfProduct;
    private final int priceOfProduct;
    private final UUID id; // ← НОВОЕ ПОЛЕ

    // Модифицированный конструктор с валидацией
    public Product(UUID id, String nameOfProduct, int priceOfProduct) { // ← Добавляем id
        // Валидация
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null!");
        }
        if (nameOfProduct == null || nameOfProduct.isBlank()) {
            throw new IllegalArgumentException("Название продукта не может быть null или пустым!");
        }

        this.id = id;
        this.nameOfProduct = nameOfProduct;
        this.priceOfProduct = priceOfProduct;
    }
    // НОВЫЙ ГЕТТЕР
    @Override
    public UUID getId() {
        return id;
    }




    // Геттеры остаются без изменений
    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public int getPriceOfProduct() {
        return priceOfProduct;
    }

    // Реализация методов интерфейса Searchable
    @Override
    public String getSearchTerm() {
        return nameOfProduct;
    }

    @Override
    public String getContentType() {
        return "PRODUCT";
    }

    @Override
    public String getName() {
        return nameOfProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(nameOfProduct, product.nameOfProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfProduct);
    }
}

