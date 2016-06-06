package cart;

import models.MenusItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by attribe on 6/6/16.
 */
public class ItemCart {



    public static List<MenusItem> orderableItems;
    private static ItemCart itemCart ;

    private ItemCart(){

        //empty private constructor
    }

    public static synchronized ItemCart getInstance(){

        if(itemCart == null||orderableItems==null){


            itemCart = new ItemCart();
            orderableItems=new ArrayList<MenusItem>();

        }

        return itemCart;

    }


    public void addItem(MenusItem item){

      // orderableItems=new ArrayList<>();
        orderableItems.add(item);
    }


    public void removeItem(MenusItem item){


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
}
