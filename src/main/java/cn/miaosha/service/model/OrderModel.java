package cn.miaosha.service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Description: 用户下单的交易模型(最简单的模型：只考虑一个订单只购买一个商品，并支付一次的情况)
 * User: mask-fish
 * Date: 2019/4/24 0024
 * Time: 21:44
 */
@Setter
@Getter
public class OrderModel {
    //交易号：2019042400000000（企业级每一位都有特定的含义）
    private String id;

    //购买的用户id
    private Integer userId;

    //购买的商品id
    private Integer itemId;

    //若非空，则表示以秒杀商品方式下单
    private Integer promoId;

    //购买商品的单价(保证商品表的价格变化时，交易模型的价格不变)；若非空，则表示以秒杀商品价格下单
    private BigDecimal itemPrice;

    //购买数量
    private Integer amount;

    //购买金额  若非空，则表示以秒杀商品价格下单
    private BigDecimal orderPrice;
}
