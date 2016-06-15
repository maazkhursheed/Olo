package network;

import models.Category;
import models.MenusItem;
import models.OrderResponse;
import models.Orders;
import retrofit.Callback;
import retrofit.http.*;

import java.util.ArrayList;

/**
 * Created by Maaz on 5/13/2015.
 */
public interface ApiInterface {

    public static String URL_GET_CATEGORIES ="/categories";
    public static String URL_GET_ITEMS = "/menus";
    public static String URL_PLACE_ORDER = "/orders";
    public static String URL_SYNC ="/categories/sync";
    public static String URL_REGISTER_DEVICE= "/devices";
    public static String URL_GET_KEY="/user/get_api_key";
    public static String PARAM_PASSCODE = "passcode";

   // @Headers("Accept:application/json")
    @GET(URL_GET_CATEGORIES)
    void getCategories(Callback<ArrayList<Category>> callback);
   // @Headers("Accept:application/json")
    @GET(URL_GET_ITEMS)
    void getMenuItems(@Query("category_id") int categoryId ,
                  Callback<ArrayList<MenusItem>> callback);

    @POST(URL_PLACE_ORDER)
    void placeOrder(@Body Orders orders, Callback<OrderResponse> responseCallback);
//
//    @GET(URL_GET_CATEGORIES)
//    ArrayList<Category> getCategoriesSync();


}
