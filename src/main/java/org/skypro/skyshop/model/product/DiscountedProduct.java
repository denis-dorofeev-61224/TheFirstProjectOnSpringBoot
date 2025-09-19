package org.skypro.skyshop.model.product;
import java.util.UUID; // ← ДОБАВЛЯЕМ ИМПОРТ

public class DiscountedProduct extends Product {
    private final int simplePrice;
    private final int discountInPercent;

    //модифицируем конструктор
    public DiscountedProduct(UUID id, String nameOfProduct, int simplePrice, int discountInPercent) {
        super(id, nameOfProduct, simplePrice); // ← Передаем id родителю
        // Проверка базовой цены
        if (simplePrice <= 0) {
            throw new IllegalArgumentException(
                    "Базовая цена должна быть больше 0! Получено: " + simplePrice
            );
        }

        // Проверка процента скидки (0-100 включительно)
        if (discountInPercent < 0 || discountInPercent > 100) {
            throw new IllegalArgumentException(
                    "Процент скидки должен быть от 0 до 100! Получено: " + discountInPercent
            );
        }

        this.simplePrice = simplePrice;
        this.discountInPercent = discountInPercent;
    }

    @Override
    public int getPriceOfProduct() {
        return simplePrice - (simplePrice * discountInPercent / 100);
    }

    @Override
    public String toString() {
        return String.format("%s: %d руб. (скидка %d%%)",
                getNameOfProduct(),
                getPriceOfProduct(),
                discountInPercent
        );
    }
}