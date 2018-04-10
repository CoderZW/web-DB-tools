package com.homethy.constant;

public enum AgentInfoEnum {
  Kaiyu("kaiyu.chime.me", "xiaoxue.wang@renren-inc.com", "e10adc3949ba59abbe56e057f20f883e", "chime.me");

  private String domain;
  private String account;
  private String password;
  private String crmSite;

  AgentInfoEnum(String domain, String account, String password, String crmSite) {
    this.domain = domain;
    this.account = account;
    this.password = password;
    this.crmSite = crmSite;
  }

  public static AgentInfoEnum getAgentInfoEnumByDomain(String domain) {
    for (AgentInfoEnum agentInfoEnum : AgentInfoEnum.values()) {
      if (domain.equals(agentInfoEnum.getDomain())) {
        return agentInfoEnum;
      }
    }
    return null;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCrmSite() {
    return crmSite;
  }

  public void setCrmSite(String crmSite) {
    this.crmSite = crmSite;
  }

}
