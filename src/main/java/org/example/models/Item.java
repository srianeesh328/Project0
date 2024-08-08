package org.example.models;

public class Item {

    private int item_id;
    private String item_title;
    private String item_description;

    private User user;
    private int user_id_fk;

    public Item()
    {

    }

    public Item(int item_id, String item_title, String item_description, User user)
    {
        this.item_id = item_id;
        this.item_title = item_title;
        this.item_description = item_description;
        this.user = user;
    }

    public Item(String item_title, String item_description, int user_id_fk)
    {
        this.item_title = item_title;
        this.item_description = item_description;
        this.user_id_fk = user_id_fk;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUser_id_fk() {
        return user_id_fk;
    }

    public void setUser_id_fk(int user_id_fk) {
        this.user_id_fk = user_id_fk;
    }

    @Override
    public String toString() {
        return "Item{" +
                "item_id=" + item_id +
                ", item_title='" + item_title + '\'' +
                ", item_description='" + item_description + '\'' +
                ", user=" + user +
                ", user_id_fk=" + user_id_fk +
                '}';
    }
}
