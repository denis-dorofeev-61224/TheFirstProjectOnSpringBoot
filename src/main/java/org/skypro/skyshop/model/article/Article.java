package org.skypro.skyshop.model.article;
import org.skypro.skyshop.model.search.Searchable;
import java.util.UUID; // ← ДОБАВЛЯЕМ ИМПОРТ
import java.util.Objects;


public class Article implements Searchable {
    //добавляем поля по условию п.1
    private final String articleTitle;
    private final String textOfArticle;
    private final UUID id; // ← НОВОЕ ПОЛЕ

    //модифицируем конструктор
    public Article(UUID id, String articleTitle, String textOfArticle) { // ← Добавляем id
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null!");
        }

        this.id = id;
        this.articleTitle = articleTitle;
        this.textOfArticle = textOfArticle;
    }

    //задаём геттеры
    //новый геттер

    @Override
    public UUID getId() {
        return id;
    }
    public String getArticleTitle(){
        return articleTitle;
    }
    public String getTextOfArticle(){
        return textOfArticle;
    }

    // Добавим toString() по заданию
    @Override
    public String toString() {
        return articleTitle + "\n" + textOfArticle;
    }


    // Реализация методов интерфейса
    @Override
    public String getSearchTerm() {
        return toString(); // Как указано в задании: "можно просто возвращать строку из toString"
    }

    @Override
    public String getContentType() {
        return "ARTICLE"; // Как требуется в задании
    }

    @Override
    public String getName() {
        return articleTitle; // Возвращаем название статьи как имя
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(articleTitle, article.articleTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleTitle);
    }

}
