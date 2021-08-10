package com.roninswdstudio.ronin_app.entity.response;


public class DeleteImageResponse {

    private long id;
    private boolean deleted = false;

    public DeleteImageResponse() {}

    public DeleteImageResponse(long id, boolean deleted) {
        this.id = id;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
