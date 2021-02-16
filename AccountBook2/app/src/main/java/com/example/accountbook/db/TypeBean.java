package com.example.accountbook.db;

public class TypeBean {
    int id;
    String typename;
    int imagrId;//未被选中图片id
    int simageId;//被选中图片id
    int kind;//收入-1，支出-0

    public TypeBean(int id, String typename, int imagrId, int simageId, int kind) {
        this.id = id;
        this.typename = typename;
        this.imagrId = imagrId;
        this.simageId = simageId;
        this.kind = kind;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public void setImagrId(int imagrId) {
        this.imagrId = imagrId;
    }

    public void setSimageId(int simageId) {
        this.simageId = simageId;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public String getTypename() {
        return typename;
    }

    public int getImagrId() {
        return imagrId;
    }

    public int getSimageId() {
        return simageId;
    }

    public int getKind() {
        return kind;
    }
}
