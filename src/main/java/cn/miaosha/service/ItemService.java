package cn.miaosha.service;

import cn.miaosha.error.BusinessException;
import cn.miaosha.service.model.ItemModel;

import java.util.List;

/**
 * Description:
 * User: mask-fish
 * Date: 2019/4/22 0022
 * Time: 21:44
 */
public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItem();

    //商品详情浏览
    ItemModel getItemById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    //商品销量增加
    void increaseSales(Integer itemId,Integer amount) throws BusinessException;

}
