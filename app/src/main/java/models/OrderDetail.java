package models;

/**
 * Created by attribe on 6/9/16.
 */
public class OrderDetail {

    private Integer menuId;
    private String itemName;
    private Integer quantity;
    private double itemPrice;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }



    public OrderDetail(Integer menuId, String itemName, Integer quantity, Integer itemPrice) {
        this.menuId = menuId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }



}
