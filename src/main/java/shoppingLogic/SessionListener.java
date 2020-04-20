package shoppingLogic;

import util.ConstantValues;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();


             //   session.setAttribute("shoppingCart", shoppingCartBean);

    }
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();

        String userId = (String) session.getAttribute("user_id");
        if(userId != null && userId.length() > 0) {
            //CartsDAO.handleCartDestroy(userId, cartProductsMap);
            //shoppingCartBean.remove();
           // boolean goFurther = OrderDAO.returnUnfinishedOrderContentToStock(userId);

            //if(!goFurther) {
           //     System.out.println("Coś poszło nie tak przy zwracaniu zawartości zamówienia do tabeli products Order.doPost().OrdersDAO.returnUnfinishedOrderContentToStock()");
            //} else {
                // If data has been returned from orders_content to products, than you can delete data about orders
            //    goFurther = OrderDAO.removeUnfinishedOrderContent(userId);
           ///     if(!goFurther) {
            //        System.out.println("Coś poszło nie tak przy czyszczeniu zawartości zamówienia Order.doPost().OrdersDAO.removeUnfinishedOrderContent()");
            //    } else {
                    // Finally prepare order from given cart. Book items and create order in database.
           //     }
           // }
        }
    }
}
