package com.everneth.evermap.models;

import java.security.Timestamp;

enum MarkerType {
  SHOP, BASE
}

public class Marker {
  private String id;
  private String label;
  private EMIPlayer owned_by;
  private MarkerType type;
  private Boolean verified;
  private EMIPlayer verified_by;
  private Timestamp verified_at;
  private Timestamp updated_at;

  public Marker(String id, String label, EMIPlayer owned_by, MarkerType type, Timestamp verified_at,
      Timestamp updated_at) {
    this.id = id;
    this.label = label;
    this.owned_by = owned_by;
    this.type = type;
    this.verified_at = verified_at;
    this.updated_at = updated_at;
  }

  public Marker(String id, String label, EMIPlayer owned_by, MarkerType type, Boolean verified, EMIPlayer verified_by,
      Timestamp verified_at, Timestamp updated_at) {
    this.id = id;
    this.label = label;
    this.owned_by = owned_by;
    this.type = type;
    this.verified = verified;
    this.verified_by = verified_by;
    this.verified_at = verified_at;
    this.updated_at = updated_at;
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