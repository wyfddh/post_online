package com.tj720.model.common;

/**
 * 参与者对象
 * @Author: 程荣凯
 * @Date: 2018/9/29 14:37
 */
public class Participant {
    /**
     * 参与者ID
     */
    private String participantId;
    /**
     * 参与者名称
     */
    private String participantName;
    /**
     * 参与者类型(示例:参与者类型为emp，表示参与者对象是员工;参与者类型为org,表示参与者对象是机构;参与者类型为role,表示参与者对象是角色)
     */
    private String participantType;
    /**
     * 备注
     */
    private String remark;

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
