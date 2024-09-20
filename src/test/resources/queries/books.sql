##get all books from Books

select * from books;


##get books categories
SELECT * from book_categories;

##get book by isnb
select isbn from books;

select name, isbn, year, author, book_category_id, description
from books
where id = 1;




