package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.basket.ProductBasket;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    // 1. Добавление несуществующего товара → исключение
    @Test
    void addProduct_WhenProductNotExists_ThrowsException() {
        // Given
        UUID productId = UUID.randomUUID();
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        // When & Then
        // assertThrows() - проверяем что метод кидает исключение
        assertThrows(NoSuchProductException.class, () -> {
            basketService.addProduct(productId);
        });

        verify(storageService).getProductById(productId);
        // verifyNoInteractions() - проверяем что мок НЕ вызывался
        verifyNoInteractions(productBasket);
    }

    // 2. Добавление существующего товара → вызывается addProduct
    @Test
    void addProduct_WhenProductExists_CallsAddProduct() {
        // Given
        UUID productId = UUID.randomUUID();
        var product = new SimpleProduct(productId, "Test Product", 1000);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        // When
        basketService.addProduct(productId);

        // Then
        verify(storageService).getProductById(productId);
        // verify() - проверяем что мок вызывался с правильными параметрами
        verify(productBasket).addProduct(productId);
    }

    // 3. getUserBasket с пустой корзиной → пустая корзина
    @Test
    void getUserBasket_WhenBasketEmpty_ReturnsEmptyBasket() {
        // Given
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        // When
        UserBasket result = basketService.getUserBasket();

        // Then
        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotal());
        verify(productBasket).getProducts();
        verifyNoInteractions(storageService);
    }

    // 4. getUserBasket с товарами → возвращает корзину
    @Test
    void getUserBasket_WhenBasketHasProducts_ReturnsFilledBasket() {
        // Given
        UUID productId = UUID.randomUUID();
        // Создаем реальные Product для теста с корзиной
        var product = new SimpleProduct(productId, "Test Product", 1000);

        Map<UUID, Integer> basketMap = new HashMap<>();
        basketMap.put(productId, 2); // 2 штуки товара

        when(productBasket.getProducts()).thenReturn(basketMap);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        // When
        UserBasket result = basketService.getUserBasket();

        // Then
        assertFalse(result.getItems().isEmpty());
        assertEquals(1, result.getItems().size());
        // Проверяем расчет общей суммы в корзине
        assertEquals(2000, result.getTotal()); // 2 * 1000 = 2000
        assertEquals("Test Product", result.getItems().get(0).getProduct().getNameOfProduct());
        assertEquals(2, result.getItems().get(0).getQuantity());

        verify(productBasket).getProducts();
        verify(storageService).getProductById(productId);
    }
}
