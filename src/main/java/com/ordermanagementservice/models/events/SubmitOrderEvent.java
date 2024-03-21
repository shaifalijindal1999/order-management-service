package com.ordermanagementservice.models.events;

import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.common.UserModels.User;

public class SubmitOrderEvent implements Event {
    String id;

    String orderId;
    EventType eventType;

    public SubmitOrderEvent(String orderId) {
        this.orderId = orderId;
    }


    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setType() {
        this.eventType = EventType.SUBMIT;
    }

    Product productInfo;
    User userInfo;

}
