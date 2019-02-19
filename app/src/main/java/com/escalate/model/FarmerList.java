package com.escalate.model;

public class FarmerList {

    public String getFarmerServerId() {
        return farmerServerId;
    }

    public void setFarmerServerId(String farmerServerId) {
        this.farmerServerId = farmerServerId;
    }

    private String farmerServerId;
    private String farmerCode;
    private String farmerName;
    private String farmerPhoneNo;
    private String farmerAddress;
    private String farmerDeleteStatus;
    private String farmerSyncStatus;
    private String farmerTimeStamp;
    private byte[] farmerImage;

    public FarmerList() {

    }

    public FarmerList(String farmerName) {
        this.farmerCode = farmerName;
        this.farmerName = farmerName;
    }

    public String getCode() {
        return farmerCode;
    }

    public void setCode(String code) {
        this.farmerCode = code;
      //  Log.e("FarmerList............", "FarmerList: "+farmerCode );
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {

      this.farmerName = farmerName;
    }

    public String getFarmerPhoneNo() {
        return farmerPhoneNo;
    }

    public void setFarmerPhoneNo(String farmerPhoneNo) {
        this.farmerPhoneNo = farmerPhoneNo;
    }

    public String getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(String farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public String getFarmerSyncStatus() {

        return farmerSyncStatus;
    }

    public void setFarmerSyncStatus(String farmerSyncStatus) {
        this.farmerSyncStatus = farmerSyncStatus;
    }

    public String getFarmerDeleteStatus() {
        return farmerDeleteStatus;
    }

    public void setFarmerDeleteStatus(String farmerDeleteStatus) {
        this.farmerDeleteStatus = farmerDeleteStatus;
    }

    public String getFarmerTimeStamp() {
        return farmerTimeStamp;
    }

    public void setFarmerTimeStamp(String farmerTimeStamp) {
        this.farmerTimeStamp = farmerTimeStamp;
    }

    public byte[] getFarmerImage() {
        return farmerImage;
    }

    public void setFarmerImage(byte[] farmerImage) {
        this.farmerImage = farmerImage;
    }
}
