package com.tj720.processRule;

import com.tj720.model.common.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义参与者规则示例
 * @Author: 程荣凯
 * @Date: 2018/9/29 14:43
 */
public class ParticipantRuleImpl implements ParticipantRule{
    @Override
    public List<Participant> getParticipant() {
        Participant participant = new Participant();
        participant.setParticipantId("zhangsan");
        participant.setParticipantName("张三");
        participant.setParticipantType("emp");
        List<Participant> participantList = new ArrayList<Participant>();
        participantList.add(participant);
        return participantList;
    }
}
