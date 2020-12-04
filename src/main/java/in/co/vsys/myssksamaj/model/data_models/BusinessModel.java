package in.co.vsys.myssksamaj.model.data_models;

/**
 * Created by Vysys on 04/04/2018.
 */

public class BusinessModel {

    private int businessId;
    private String businessName;
    private String businessCity;
    private String contactNo;
    private String businessType;
    private String businessUrl;
    private String businessAddress;
    private String mobileNumber;
    private String services;
    private String aboutUs;
    private String longitude;
    private String latitude;
    private String personName;
    private String imageOne;
    private String imageTwo;
    private String imageThree;
    private String imageFour;
    private String businessStatus;
    private String emailId;
    private String country;
    private String state;
    private String city;
    private String mondayHours;
    private String tuesdayHours;
    private String wednsdayHours;
    private String thusdayHours;
    private String fridayHours;
    private String saturdayHours;
    private String sundayHours;


    public BusinessModel() {
    }


    public BusinessModel(int businessId, String businessName, String businessWebUrl, String businessCity, String contactNo, String imageOne, String businessType) {
        this.businessId = businessId;
        this.businessName = businessName;
        this.businessUrl = businessWebUrl;
        this.businessCity = businessCity;
        this.contactNo = contactNo;
        this.imageOne = imageOne;
        this.businessType = businessType;
    }


    public BusinessModel(int businessId, String businessName, String businessCity, String contactNo, String businessType, String businessUrl, String businessAddress, String mobileNumber, String services, String aboutUs, String longitude, String latitude, String personName, String imageOne, String imageTwo, String imageThree, String imageFour, String businessStatus, String emailId, String country, String state, String mondayHours, String tuesdayHours, String wednsdayHours, String thusdayHours, String fridayHours, String saturdayHours, String sundayHours) {
        this.businessId = businessId;
        this.businessName = businessName;
        this.businessCity = businessCity;
        this.contactNo = contactNo;
        this.businessType = businessType;
        this.businessUrl = businessUrl;
        this.businessAddress = businessAddress;
        this.mobileNumber = mobileNumber;
        this.services = services;
        this.aboutUs = aboutUs;
        this.longitude = longitude;
        this.latitude = latitude;
        this.personName = personName;
        this.imageOne = imageOne;
        this.imageTwo = imageTwo;
        this.imageThree = imageThree;
        this.imageFour = imageFour;
        this.businessStatus = businessStatus;
        this.emailId = emailId;
        this.country = country;
        this.state = state;
        this.mondayHours = mondayHours;
        this.tuesdayHours = tuesdayHours;
        this.wednsdayHours = wednsdayHours;
        this.thusdayHours = thusdayHours;
        this.fridayHours = fridayHours;
        this.saturdayHours = saturdayHours;
        this.sundayHours = sundayHours;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


    public String getBusinessCity() {
        return businessCity;
    }

    public void setBusinessCity(String businessCity) {
        this.businessCity = businessCity;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getBusinessUrl() {
        return businessUrl;
    }

    public void setBusinessUrl(String businessUrl) {
        this.businessUrl = businessUrl;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getImageOne() {
        return imageOne;
    }

    public void setImageOne(String imageOne) {
        this.imageOne = imageOne;
    }

    public String getImageTwo() {
        return imageTwo;
    }

    public void setImageTwo(String imageTwo) {
        this.imageTwo = imageTwo;
    }

    public String getImageThree() {
        return imageThree;
    }

    public void setImageThree(String imageThree) {
        this.imageThree = imageThree;
    }

    public String getImageFour() {
        return imageFour;
    }

    public void setImageFour(String imageFour) {
        this.imageFour = imageFour;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMondayHours() {
        return mondayHours;
    }

    public void setMondayHours(String mondayHours) {
        this.mondayHours = mondayHours;
    }

    public String getTuesdayHours() {
        return tuesdayHours;
    }

    public void setTuesdayHours(String tuesdayHours) {
        this.tuesdayHours = tuesdayHours;
    }

    public String getWednsdayHours() {
        return wednsdayHours;
    }

    public void setWednsdayHours(String wednsdayHours) {
        this.wednsdayHours = wednsdayHours;
    }

    public String getThusdayHours() {
        return thusdayHours;
    }

    public void setThusdayHours(String thusdayHours) {
        this.thusdayHours = thusdayHours;
    }

    public String getFridayHours() {
        return fridayHours;
    }

    public void setFridayHours(String fridayHours) {
        this.fridayHours = fridayHours;
    }

    public String getSaturdayHours() {
        return saturdayHours;
    }

    public void setSaturdayHours(String saturdayHours) {
        this.saturdayHours = saturdayHours;
    }

    public String getSundayHours() {
        return sundayHours;
    }

    public void setSundayHours(String sundayHours) {
        this.sundayHours = sundayHours;
    }
}
