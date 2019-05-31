package cn.miaosha.service.impl;

import cn.miaosha.dao.PromoDOMapper;
import cn.miaosha.dataobject.PromoDO;
import cn.miaosha.service.PromoService;
import cn.miaosha.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: mask-fish
 * Date: 2019/4/26 0026
 * Time: 21:07
 */
@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        //dataobject -> model
        PromoModel promoModel = converFromDataObject(promoDO);
        if (promoModel == null) {
            return null;
        }
        //判断当前时间是否秒杀活动即将开始或正在进行
        DateTime now = new DateTime();
        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1);
        } else if (promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(3);
        }else {
            promoModel.setStatus(2);
        }

        return promoModel;
    }

    private PromoModel converFromDataObject(PromoDO promoDO) {
        if (promoDO == null) {
           return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }

//    public static void main(String[] args) {
//        //精确到毫秒级
//        DateTime now = new DateTime();
//        //DateTime time = new DateTime(2019,4,27,11,11,11,0);
//        DateTime time = new DateTime("2019-04-27T11:11:11");
//        System.out.println(time);
//        System.out.println(now);
//        System.out.println(time.isBeforeNow());
//        System.out.println(time.isAfterNow());
//    }
}
