package com.homethy.domain;

/**
 * Created by leifeifei on 18-3-14.
 */
public class LeadRsyncStatus {
  private String domain;

  private int isComplete;

  private int fullLeadSuccess;

  private int fullLeadFail;

  private int fullLeadEmailExists;

  private int partialLeadSuccess;

  private int partialLeadFail;

  private int partialLeadEmailExists;

  private int buildSiteCompleted;

  private String siteBuildNotes;

  private String crmAccount;

  private String chimeDomain;

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public int getIsComplete() {
    return isComplete;
  }

  public void setIsComplete(int isComplete) {
    this.isComplete = isComplete;
  }

  public int getFullLeadSuccess() {
    return fullLeadSuccess;
  }

  public void setFullLeadSuccess(int fullLeadSuccess) {
    this.fullLeadSuccess = fullLeadSuccess;
  }

  public int getFullLeadFail() {
    return fullLeadFail;
  }

  public void setFullLeadFail(int fullLeadFail) {
    this.fullLeadFail = fullLeadFail;
  }

  public int getPartialLeadSuccess() {
    return partialLeadSuccess;
  }

  public void setPartialLeadSuccess(int partialLeadSuccess) {
    this.partialLeadSuccess = partialLeadSuccess;
  }

  public int getPartialLeadFail() {
    return partialLeadFail;
  }

  public void setPartialLeadFail(int partialLeadFail) {
    this.partialLeadFail = partialLeadFail;
  }

  public int getFullLeadEmailExists() {
    return fullLeadEmailExists;
  }

  public void setFullLeadEmailExists(int fullLeadEmailExists) {
    this.fullLeadEmailExists = fullLeadEmailExists;
  }

  public int getBuildSiteCompleted() {
    return buildSiteCompleted;
  }

  public void setBuildSiteCompleted(int buildSiteCompleted) {
    this.buildSiteCompleted = buildSiteCompleted;
  }

  public String getSiteBuildNotes() {
    return siteBuildNotes;
  }

  public void setSiteBuildNotes(String siteBuildNotes) {
    this.siteBuildNotes = siteBuildNotes;
  }

  public String getCrmAccount() {
    return crmAccount;
  }

  public void setCrmAccount(String crmAccount) {
    this.crmAccount = crmAccount;
  }

  public String getChimeDomain() {
    return chimeDomain;
  }

  public void setChimeDomain(String chimeDomain) {
    this.chimeDomain = chimeDomain;
  }

  public int getPartialLeadEmailExists() {
    return partialLeadEmailExists;
  }

  public void setPartialLeadEmailExists(int partialLeadEmailExists) {
    this.partialLeadEmailExists = partialLeadEmailExists;
  }

}
