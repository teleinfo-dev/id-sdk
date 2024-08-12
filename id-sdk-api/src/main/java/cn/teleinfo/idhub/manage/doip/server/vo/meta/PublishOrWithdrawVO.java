package cn.teleinfo.idhub.manage.doip.server.vo.meta;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 谢珍
 * @version 1.0
 * @date Created in 2024/5/6
 * @description 发布或撤回返回的data结构
 */
@Data
public class PublishOrWithdrawVO {

        public String metaHandle;
        public String state;
        public String reason;
}
