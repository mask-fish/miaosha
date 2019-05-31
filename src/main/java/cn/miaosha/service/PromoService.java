package cn.miaosha.service;

import cn.miaosha.service.model.PromoModel;

/**
 * Description:
 * User: mask-fish
 * Date: 2019/4/26 0026
 * Time: 21:05
 */
public interface PromoService {
    //根据itemId获取即将进行的或正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
