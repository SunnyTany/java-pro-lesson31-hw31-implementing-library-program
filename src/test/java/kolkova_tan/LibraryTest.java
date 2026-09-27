package kolkova_tan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;

    @BeforeEach
    public void setUp() {
        library = new Library();
    }

    // Вспомогательный метод для создания валидной книги, когда она нужна
    private Book createValidBook() {
        return new Book("Кобзарь", "Тарас Шевченко");
    }

    // === ТЕСТЫ ДЛЯ МЕТОДА addBook ===

    @Test
    public void addBookSuccess() {
        Book book = createValidBook();
        library.addBook(book);

        assertEquals(1, library.getBookCount());
        assertTrue(library.getBooks().contains(book));
    }

    @Test
    public void addBookThrowsExceptionWhenBookIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                library.addBook(null)
        );
        assertEquals("Книга не может быть null", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    public void addBookThrowsExceptionWhenTitleIsInvalid(String invalidTitle) {
        Book book = new Book(invalidTitle, "Тарас Шевченко");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                library.addBook(book)
        );
        assertEquals("Название книги не может быть пустым", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    public void addBookThrowsExceptionWhenAuthorIsInvalid(String invalidAuthor) {
        Book book = new Book("Кобзарь", invalidAuthor);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                library.addBook(book)
        );
        assertEquals("Автор книги не может быть пустым", exception.getMessage());
    }

    // === ТЕСТЫ ДЛЯ МЕТОДА removeBook ===

    @Test
    public void removeBookSuccess() {
        Book book = createValidBook();
        library.addBook(book);

        boolean isRemoved = library.removeBook(book);

        assertTrue(isRemoved);
        assertEquals(0, library.getBookCount());
    }

    @Test
    public void removeBookReturnsFalseWhenBookNotFound() {
        library.addBook(createValidBook());
        Book anotherBook = new Book("1984", "Джордж Оруэлл");

        boolean isRemoved = library.removeBook(anotherBook);

        assertFalse(isRemoved);
        assertEquals(1, library.getBookCount());
    }

    @Test
    public void removeBookReturnsFalseWhenBookIsNull() {
        library.addBook(createValidBook());

        boolean isRemoved = library.removeBook(null);

        assertFalse(isRemoved);
        assertEquals(1, library.getBookCount());
    }

    // === ТЕСТЫ ДЛЯ МЕТОДОВ getBooks и getBookCount ===

    @Test
    public void getBooksReturnsImmutableList() {
        library.addBook(createValidBook());
        List<Book> booksList = library.getBooks();

        assertThrows(UnsupportedOperationException.class, () ->
                booksList.add(new Book("Завещание", "Шевченко"))
        );
    }

    @Test
    public void getBookCountEmptyAndFilled() {
        assertEquals(0, library.getBookCount());

        library.addBook(createValidBook());
        library.addBook(new Book("Мастер и Маргарита", "Михаил Булгаков"));

        assertEquals(2, library.getBookCount());
    }
}