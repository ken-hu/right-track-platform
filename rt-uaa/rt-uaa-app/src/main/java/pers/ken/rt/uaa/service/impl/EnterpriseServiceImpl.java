package pers.ken.rt.uaa.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.common.utils.BeanMapper;
import pers.ken.rt.uaa.dto.resp.EnterpriseListResp;
import pers.ken.rt.uaa.entity.Enterprise;
import pers.ken.rt.uaa.repository.EnterpriseRepository;
import pers.ken.rt.uaa.service.EnterpriseService;

import java.util.List;

/**
 * <code> EnterpriseServiceImpl </code>
 * <desc> EnterpriseServiceImpl </desc>
 * <b>Creation Time:</b> 2022/2/27 12:43.
 *
 * @author _Ken.Hu
 */
@Service
@AllArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {
    private EnterpriseRepository enterpriseRepository;

    @Override
    public List<EnterpriseListResp> listAll() {
        List<Enterprise> enterprises = enterpriseRepository.findAll();
        return BeanMapper.copyListProperties(enterprises, EnterpriseListResp.class);
    }
}
