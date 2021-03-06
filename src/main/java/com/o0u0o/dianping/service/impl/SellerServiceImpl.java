package com.o0u0o.dianping.service.impl;

import com.o0u0o.dianping.commom.enums.BusinessErrorEnum;
import com.o0u0o.dianping.commom.exception.BusinessException;
import com.o0u0o.dianping.dal.SellerModelMapper;
import com.o0u0o.dianping.model.SellerModel;
import com.o0u0o.dianping.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author aiuiot
 * @Date 2020/3/6 9:56 上午
 * @Descripton: 商家服务实现
 **/
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerModelMapper sellerModelMapper;


    @Override
    @Transactional
    public SellerModel create(SellerModel sellerModel) {
        sellerModel.setCreatedAt(new Date());
        sellerModel.setUpdatedAt(new Date());
        sellerModel.setRemarkScore(new BigDecimal(0));
        sellerModel.setDisabledFlag(0);
        sellerModelMapper.insertSelective(sellerModel);
        return get(sellerModel.getId());
    }

    @Override
    public SellerModel get(Integer id) {
        return sellerModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SellerModel> selectAll() {
        return sellerModelMapper.selectAll();
    }

    @Override
    public SellerModel changeStatus(Integer id, Integer disabledFlag) throws BusinessException {
        SellerModel sellerModel = get(id);
        if(sellerModel == null){
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR);
        }
        sellerModel.setDisabledFlag(disabledFlag);
        sellerModelMapper.updateByPrimaryKeySelective(sellerModel);
        return sellerModel;
    }

    /**
     * 统计商家数
     * @return
     */
    @Override
    public Integer countAllSeller() {
        return sellerModelMapper.countAllSeller();
    }
}
