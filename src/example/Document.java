package example;

import db.Entity;
import db.Trackable;
import java.util.Date;

public class Document extends  Entity implements Trackable{
    public static final int DOCUMENT_ENTITY_CODE = 15;
    private Date creationDate;
    private Date lastModificationDate;
    public String content;

    public Document(String content) {
        this.content = content;
    }
    @Override
    public Document copy() {
        Document copy = new Document(this.content);
        copy.id = this.id;

        if (this.creationDate != null) {
            copy.creationDate = new Date(this.creationDate.getTime());
        }
        if (this.lastModificationDate != null) {
            copy.lastModificationDate = new Date(this.lastModificationDate.getTime());
        }

        return copy;
    }

    @Override
    public int getEntityCode() {
        return DOCUMENT_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}

