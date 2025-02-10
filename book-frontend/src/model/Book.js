export default class Book{
    constructor(id,bookName,description,author,categorysId,isbnNo,language,price,img,category)
    {
        this.id=id;
        this.bookName=bookName;
        this.description=description;
        this.author=author;
        this.categorysId=categorysId;
        this.isbnNo=isbnNo;
        this.language=language;
        this.price=price;
        this.img=img;
        this.category=category;
    }
}