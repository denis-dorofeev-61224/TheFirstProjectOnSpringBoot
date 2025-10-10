package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    // 1. Тест когда в хранилище пусто
    @Test
    void search_WhenStorageEmpty_ReturnsEmptyList() {
        // Given - когда хранилище пустое
        when(storageService.getAllSearchable()).thenReturn(Collections.emptyList());

        // When - выполняем поиск
        var result = searchService.search("anything");

        // Then - получаем пустой список
        assertTrue(result.isEmpty());
        verify(storageService).getAllSearchable();
    }

    // 2. Тест когда объекты есть, но нет подходящих
    @Test
    void search_WhenNoMatchingProducts_ReturnsEmptyList() {
        // Given - создаем тестовые данные которые НЕ содержат "phone"
        var searchableItem = mock(Searchable.class);
        when(searchableItem.getSearchTerm()).thenReturn("Сумка женская");

        when(storageService.getAllSearchable()).thenReturn(List.of(searchableItem));

        // When - ищем "phone" (чего нет в данных)
        var result = searchService.search("phone");

        // Then - получаем пустой список
        assertTrue(result.isEmpty());
        verify(storageService).getAllSearchable();
    }

    // 3. Тест когда есть подходящий объект
    @Test
    void search_WhenMatchingProductExists_ReturnsSearchResult() {
        // Given - создаем товар который СОДЕРЖИТ "сумка"
        var matchingProduct = mock(Searchable.class);
        when(matchingProduct.getSearchTerm()).thenReturn("Женская сумка ShellBlack");
        when(matchingProduct.getName()).thenReturn("Женская сумка ShellBlack");
        when(matchingProduct.getContentType()).thenReturn("PRODUCT");
        when(matchingProduct.getId()).thenReturn(UUID.randomUUID());

        when(storageService.getAllSearchable()).thenReturn(List.of(matchingProduct));

        // When - ищем "сумка"
        var result = searchService.search("сумка");

        // Then - получаем непустой список с результатом
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(storageService).getAllSearchable();
    }

    // 4. Дополнительный тест - проверка регистронезависимости
    @Test
    void search_IsCaseInsensitive_ReturnsResults() {
        // Given - товар в нижнем регистре
        var searchableItem = mock(Searchable.class);
        when(searchableItem.getSearchTerm()).thenReturn("женская сумка");
        when(searchableItem.getName()).thenReturn("женская сумка");
        when(searchableItem.getContentType()).thenReturn("PRODUCT");
        when(searchableItem.getId()).thenReturn(UUID.randomUUID());

        when(storageService.getAllSearchable()).thenReturn(List.of(searchableItem));

        // When - ищем в ВЕРХНЕМ регистре
        var result = searchService.search("ЖЕНСКАЯ");

        // Then - все равно находим (case insensitive)
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}