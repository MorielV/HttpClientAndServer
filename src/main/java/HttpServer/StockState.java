package HttpServer;

import java.io.Serializable;

public class StockState implements Serializable {
    Stock stock;
    double value;

    StockState(Stock stock, double value){
        this.stock=stock;
        this.value=value;
    }
}
