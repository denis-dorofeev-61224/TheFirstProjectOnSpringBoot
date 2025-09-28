package org.skypro.skyshop.model;


import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

public class SearchResult {

    private final String id;
    private final String name;
    private final String contentType;

    // Конструктор
    private SearchResult(String id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    // Статический фабричный метод
    public static SearchResult fromSearchable(Searchable searchable) {
        return new SearchResult(
                searchable.getId().toString(), // преобразуем UUID в строку
                searchable.getName(),
                searchable.getContentType()
        );
    }

    // Геттеры
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }


}
