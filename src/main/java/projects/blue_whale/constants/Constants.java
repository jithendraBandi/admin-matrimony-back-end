package projects.blue_whale.constants;

import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

//@Configuration
public class Constants {
    public static final String BUY = "BUY";
    public static final String SELL = "SELL";
    public static final String DUPLICATE_ERROR = "Duplicate Entry not allowed";
    public static final String ITEM_ASSOCIATION_ERROR = "One or more items are associated with this category. So, delete those items for deleting this category!";
    public static final String INVALID_TRADE_TYPE = "Invalid Trade Type";
}
