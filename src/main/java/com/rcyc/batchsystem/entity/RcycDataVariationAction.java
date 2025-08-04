package com.rcyc.batchsystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "rcyc_data_variation_actions")
public class RcycDataVariationAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action")
    private Integer idAction;

    @Column(name = "id_variation", nullable = false)
    private Integer idVariation;

    @Column(name = "action_condition", length = 100)
    private String actionCondition;

    @Column(name = "action_abort", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean actionAbort = true;

    @Column(name = "action_send_mail", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean actionSendMail = true;

    // --- Getters and Setters ---
    
    public Integer getIdAction() {
        return idAction;
    }

    public void setIdAction(Integer idAction) {
        this.idAction = idAction;
    }

    public Integer getIdVariation() {
        return idVariation;
    }

    public void setIdVariation(Integer idVariation) {
        this.idVariation = idVariation;
    }

    public String getActionCondition() {
        return actionCondition;
    }

    public void setActionCondition(String actionCondition) {
        this.actionCondition = actionCondition;
    }

    public Boolean getActionAbort() {
        return actionAbort;
    }

    public void setActionAbort(Boolean actionAbort) {
        this.actionAbort = actionAbort;
    }

    public Boolean getActionSendMail() {
        return actionSendMail;
    }

    public void setActionSendMail(Boolean actionSendMail) {
        this.actionSendMail = actionSendMail;
    }
}
