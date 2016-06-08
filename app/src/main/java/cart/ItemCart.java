package cart;

import models.MenusItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by attribe on 6/6/16.
 */
public class ItemCart {



    public static List<MenusItem> orderableItems;
    private static ItemCart itemCart ;


    private ItemCart(){

    }

    public static synchronized ItemCart getInstance(){

        if(itemCart == null){

            itemCart = new ItemCart();
            orderableItems=new ArrayList<MenusItem>();
        }
        return itemCart;
    }

    public void addItem(MenusItem item){
      // orderableItems=new ArrayList<>();
        orderableItems.add(item);
    }


    /**This method adds item in cart,
     * in case item already exists in the cart, it simply
     * updates its quantity
     *
     * @param item
     */
    public void addOrUpdateItem(MenusItem item){

        for(MenusItem iterator : orderableItems){
            //Item Update Block
            if(iterator.getId() == item.getId()){    //Check if item exixts in cart
                iterator.setDesiredQuantity(item.getDesiredQuantity());
                break;
            }
            //Item Insertion Block
            else{
                orderableItems.add(item);
            }
        }
    }

    public void removeItem(MenusItem item) {
        for(MenusItem iterator : orderableItems) {

            //Item remove Block
            if (iterator.getId() == item.getId()) {    //Check if item exixts in cart
                orderableItems.remove(item);
                break;
            }
        }
    }


    public double getTotal(){
        double totalItemPrice = 0;
        double cartTotal=0;

        for(MenusItem item : orderableItems){

            totalItemPrice =item.getDesiredQuantity() * item.getPrice();
            cartTotal += totalItemPrice;
        }

        return cartTotal;
    }

    public double getAllTotal(){

        double allTotalPrice = 0;
        int deliveryFee = 90;
        double serviceFee = 0;

        allTotalPrice = getTotal()+ deliveryFee + serviceFee;
        return allTotalPrice;
    }


    public static List<MenusItem> getOrderableItems() {
        return orderableItems;
    }


}
