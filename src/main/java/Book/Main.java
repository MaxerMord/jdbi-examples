package Book;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:testdb");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()){
            BookDao bookDao = handle.attach(BookDao.class);
            bookDao.creatTable();
            bookDao.insert(new Book("9780007498772","Doris Lessing",
                    "The Golden Notebook", Book.Format.PAPERBACK,
                    "HarperCollins Publishers", LocalDate.parse("2013-01-17"),
                    576, true));
            bookDao.insert(new Book("9780261102385", "J. R. R. Tolkien",
                    "The Lord of the Rings : Boxed Set", Book.Format.PAPERBACK,
                    "HarperCollins Publishers", LocalDate.parse("2008-04-01"),
                    1216, true));
            bookDao.insert(new Book("testisbn13","test author","test title",
                    Book.Format.PAPERBACK, "test publisher",LocalDate.parse("2000-02-02"),
                    111, false));

            System.out.println("------Books in the repository------");
            bookDao.findAll().forEach(System.out::println);

            Book book = bookDao.find("testisbn13").get();

            bookDao.delete(book);

            System.out.println("------Books in the repository------");
            bookDao.findAll().forEach(System.out::println);
        }
    }
}
