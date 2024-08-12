package cn.teleinfo.idhub.manage.doip.server.vo.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <Description>
 *
 * @author 梅冬屹
 * @date 2022/12/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaInfoVO {

    private String classifyCode;

    private String classifyName;

    private String metaHandle;

    private String metaName;

    private String metaCode;

    private String industry;

    private String industryCategory;

    private String industrySpecific;

    private String industryTrade;

    private String industrySubclass;

    private Integer metaState;

    private String metaContributor;


    /**
     * 依据标准
     */
    private String standard;

    /**
     * 元数据描述
     */
    private String metaDesc;

    private  String createdBy ;

    private LocalDateTime createdTime ;

    private LocalDateTime updatedTime ;

    private List<MetaItemVO> itemVOS;
}
