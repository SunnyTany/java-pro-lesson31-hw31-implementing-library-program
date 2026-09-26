package kolkova_tan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book validBook;

    @BeforeEach
    public void setUp() {
        library = new Library();
        validBook = new Book("Кобзарь", "Тарас Шевченко");
    }

    @Test
    public void testAddBookSuccess() {
        library.addBook(validBook);
        assertEquals(1, library.getBookCount());
        assertTrue(library.getBooks().contains(validBook));
    }

    @Test
    public void testAddBookThrowsExceptionWhenBookIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
        assertEquals("Книга не может быть null", exception.getMessage());
    }

    @Test
    public void testAddBookThrowsExceptionWhenTitleIsEmpty() {
        Book emptyTitleBook = new Book("", "Тарас Шевченко");
        assertThrows(IllegalArgumentException.class, () -> library.addBook(emptyTitleBook));
    }

    @Test
    public void testAddBookThrowsExceptionWhenAuthorIsNull() {
        Book nullAuthorBook = new Book("Кобзарь", null);
        assertThrows(IllegalArgumentException.class, () -> library.addBook(nullAuthorBook));
    }

    @Test
    public void testRemoveBookSuccess() {
        library.addBook(validBook);
        boolean isRemoved = library.removeBook(validBook);
        assertTrue(isRemoved);
        assertEquals(0, library.getBookCount());
    }

    @Test
    public void testRemoveBookReturnsFalseWhenBookNotFound() {
        Book anotherBook = new Book("1984", "Джордж Оруэлл");
        library.addBook(validBook);
        boolean isRemoved = library.removeBook(anotherBook);
        assertFalse(isRemoved);
        assertEquals(1, library.getBookCount());
    }

    @Test
    public void testRemoveBookReturnsFalseWhenBookIsNull() {
        library.addBook(validBook);
        boolean isRemoved = library.removeBook(null);
        assertFalse(isRemoved);
        assertEquals(1, library.getBookCount());
    }

    @Test
    public void testGetBooksReturnsImmutableList() {
        library.addBook(validBook);
        List<Book> booksList = library.getBooks();
        assertThrows(UnsupportedOperationException.class, () -> booksList.add(new Book("Тест", "Тест")));
    }

    @Test
    public void testGetBookCountEmptyAndFilled() {
        assertEquals(0, library.getBookCount());
        library.addBook(validBook);
        assertEquals(1, library.getBookCount());
    }
}