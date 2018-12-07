package com.tj720.processRule;

import com.tj720.model.common.Participant;

import java.util.List;

/**
 * 自定义参与者规则接口
 * @Author: 程荣凯
 * @Date: 2018/9/29 14:35
 */
public interface ParticipantRule {
    /**
     * 获取参与者对象
     * @return
     */
    List<Participant> getParticipant();
}
