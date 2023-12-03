package com.codfish.bikeSalesAndService.util;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToBuyEntity;
import lombok.experimental.UtilityClass;


import java.math.BigDecimal;

@UtilityClass
public class EntityFixtures {

    public static BikeToBuyEntity someBike1() {
        return BikeToBuyEntity.builder()
                .serial("serial")
                .category("category1")
                .subcategory("subcategory1")
                .brand("brand1")
                .model("model1")
                .year(2000)
                .color("color1")
                .price(new BigDecimal("10000.00"))
                .build();
    }

    public static BikeToBuyEntity someBike2() {
        return BikeToBuyEntity.builder()
                .serial("seria2l")
                .category("category2")
                .subcategory("subcategory2")
                .brand("brand2")
                .model("model2")
                .year(2000)
                .color("color2")
                .price(new BigDecimal("20000.00"))
                .build();
    }

    public static BikeToBuyEntity someBike3() {
        return BikeToBuyEntity.builder()
                .serial("serial2")
                .category("category3")
                .subcategory("subcategory3")
                .brand("brand3")
                .model("model3")
                .year(2000)
                .color("color3")
                .price(new BigDecimal("30000.20"))
                .build();
    }
}
