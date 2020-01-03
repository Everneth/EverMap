package com.everneth.evermap.models;

import java.sql.SQLException;

import com.everneth.evermap.App;

import org.jetbrains.annotations.Nullable;

import co.aikar.idb.DB;

public class MarkerModel {
  private String id;
  private String label;
  private EMIPlayer owned_by;
  private MarkerType type;
  private Boolean verified;
  private EMIPlayer verified_by;

  public MarkerModel(String id, String label, EMIPlayer owned_by, MarkerType type) {
    this.id = id;
    this.label = label;
    this.owned_by = owned_by;
    this.type = type;
  }

  public MarkerModel(String id, String label, EMIPlayer owned_by, MarkerType type, Boolean verified,
      @Nullable EMIPlayer verified_by) {
    this.id = id;
    this.label = label;
    this.owned_by = owned_by;
    this.type = type;
    this.verified = verified;
    this.verified_by = verified_by;
  }

  public void Insert() {
    try {
      DB.executeInsert(
          "INSERT INTO markers (id, label, owned_by, type, verified, verified_by) \n" + "VALUES(?,?,?,?,?,?)", id,
          label, owned_by.getId(), type.getValue(), verified, verified_by.getId());
    } catch (SQLException e) {
      App.getPlugin().getLogger().warning(e.getMessage());
    }
  }

  public String getID() {
    return id;
  }

  public void setID(String id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public EMIPlayer getOwner() {
    return owned_by;
  }

  public void setOwner(EMIPlayer owner) {
    this.owned_by = owner;
  }

  public MarkerType getType() {
    return type;
  }

  public void setType(MarkerType type) {
    this.type = type;
  }

  public Boolean getVerified() {
    return this.verified;
  }

  public void setVerified(Boolean verified) {
    this.verified = verified;
  }

  public EMIPlayer getVerifiedBy() {
    return this.verified_by;
  }

  public void setVerifiedBy(EMIPlayer verifier) {
    this.verified_by = verifier;
  }
}