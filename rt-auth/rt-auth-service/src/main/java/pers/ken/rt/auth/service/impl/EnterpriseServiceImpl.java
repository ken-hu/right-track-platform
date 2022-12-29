package pers.ken.rt.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pers.ken.rt.common.utils.BeanMapper;
import pers.ken.rt.auth.dto.resp.EnterpriseListResp;
import pers.ken.rt.auth.entity.Enterprise;
import pers.ken.rt.auth.repository.EnterpriseRepository;
import pers.ken.rt.auth.service.EnterpriseService;

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
