from abc import ABC, abstractmethod

class Book(ABC):
    def get_title(self) -> str:
        pass

    def get_author(self) -> str:
        pass

class ConcreteBook(Book):
    def __init__(self, title: str, author: str):
        self.title = title
        self.author = author

    def get_title(self) -> str:
        return self.title
    
    def get_author(self) -> str:
        return self.author
    
    def __str__(self):
        return f'Book[title: {self.title}, author: {self.author}]'

class Iterator(ABC):

    @abstractmethod
    def has_next(self) -> bool:
        pass

    @abstractmethod
    def next(self):
        pass

class BookIterator(Iterator):
    def __init__(self, books):
        self._books = books
        self._index = 0

    def has_next(self) -> bool:
        return self._index < len(self._books)
    
    def next(self):
        if not self.has_next():
            return
        book = self._books[self._index]
        self._index += 1
        return book
    
class BookShelf(ABC):
    @abstractmethod
    def createIterator(self) -> Iterator:
        pass

class ConcreteBookShelf(BookShelf):
    def __init__(self):
        self._books: list = []
    
    def add_book(self, book: Book):
        self._books.append(book)

    def createIterator(self) -> BookIterator:
        return BookIterator(self._books)
    
bookShelf = ConcreteBookShelf()
bookShelf.add_book(ConcreteBook('Java Design Patterns', 'Sven Walter'))
bookShelf.add_book(ConcreteBook('Clean Code', 'Robert C. Martin'))

iterator = bookShelf.createIterator()
while iterator.has_next():
    book = iterator.next()
    print(book)
    