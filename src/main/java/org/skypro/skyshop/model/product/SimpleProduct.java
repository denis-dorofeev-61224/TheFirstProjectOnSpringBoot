package org.skypro.skyshop.model.product;
import java.util.UUID; // ← ДОБАВЛЯЕМ ИМПОРТ

public class SimpleProduct extends Product {
    private final int simplePrice;

    // Модифицируем конструктор
    public SimpleProduct(UUID id, String nameOfProduct, int simplePrice) { // ← Добавляем id
        super(id, nameOfProduct, simplePrice); // ← Передаем id родителю

        // Проверка цены (должна быть > 0)
        if (simplePrice <= 0) {
            throw new IllegalArgumentException(
                    "Цена продукта должна быть больше 0! Получено: " + simplePrice
            );
        }

        this.simplePrice = simplePrice;
    }

    @Override
    public int getPriceOfProduct() {
        return simplePrice;
    }

    @Override
    public String toString() {
        return getNameOfProduct() + ": " + getPriceOfProduct() + " руб.";
    }
}