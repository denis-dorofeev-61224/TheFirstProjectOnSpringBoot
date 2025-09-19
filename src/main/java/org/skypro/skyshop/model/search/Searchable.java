package org.skypro.skyshop.model.search;
import java.util.UUID;//библиотека м классом для генерации идентификаторов

public interface Searchable {

    // Возвращает текст для поиска (например: "Название товара" или "Название статьи Текст статьи")
    String getSearchTerm();

    // Возвращает тип контента ("PRODUCT" или "ARTICLE")
    String getContentType();

    // Возвращает имя объекта (например: название товара/статьи)
    String getName();

    // Дефолтная реализация
    default String getStringRepresentation() {
        return getName() + " — " + getContentType();
    }

     //добавляем новый метод
     UUID getId(); // Добавляем этот метод

}
