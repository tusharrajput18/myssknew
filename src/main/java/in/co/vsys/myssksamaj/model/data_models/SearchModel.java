package in.co.vsys.myssksamaj.model.data_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author abhijeetjadhav
 */
public class SearchModel implements Serializable {
    private String UniqueId;

    @SerializedName("Education In")
    private String EducationIn;

    public SearchModel(String uniqueId, String educationIn, String firstName, String stateid, String memberState, String marriedStatus, String gender, String cityid, String middleName, String memberCountry, String countryid, String mainProfilePhoto, String memberId, String DOB, String age, String motherTongue, String appLoginId, String memberHeight, String lastName, String memberCity, String memberInCome) {
        UniqueId = uniqueId;
        EducationIn = educationIn;
        FirstName = firstName;
        this.stateid = stateid;
        MemberState = memberState;
        MarriedStatus = marriedStatus;
        Gender = gender;
        this.cityid = cityid;
        MiddleName = middleName;
        MemberCountry = memberCountry;
        this.countryid = countryid;
        MainProfilePhoto = mainProfilePhoto;
        MemberId = memberId;
        this.DOB = DOB;
        Age = age;
        MotherTongue = motherTongue;
        AppLoginId = appLoginId;
        MemberHeight = memberHeight;
        LastName = lastName;
        MemberCity = memberCity;
        MemberInCome = memberInCome;
    }

    private String FirstName;

    private String stateid;

    private String MemberState;

    private String MarriedStatus;

    private String Gender;

    private String cityid;

    private String MiddleName;

    private String MemberCountry;

    private String countryid;

    private String MainProfilePhoto;

    private String MemberId;

    private String DOB;
    private String Age;

    private String MotherTongue;

    private String AppLoginId;

    private String MemberHeight;

    private String LastName;

    private String MemberCity;

    private String MemberInCome;

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }

    public String getEducationIn() {
        return EducationIn;
    }

    public void setEducationIn(String educationIn) {
        EducationIn = educationIn;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    public String getMemberState() {
        return MemberState;
    }

    public void setMemberState(String memberState) {
        MemberState = memberState;
    }

    public String getMarriedStatus() {
        return MarriedStatus;
    }

    public void setMarriedStatus(String marriedStatus) {
        MarriedStatus = marriedStatus;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getMemberCountry() {
        return MemberCountry;
    }

    public void setMemberCountry(String memberCountry) {
        MemberCountry = memberCountry;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getMainProfilePhoto() {
        return MainProfilePhoto;
    }

    public void setMainProfilePhoto(String mainProfilePhoto) {
        MainProfilePhoto = mainProfilePhoto;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getMotherTongue() {
        return MotherTongue;
    }

    public void setMotherTongue(String motherTongue) {
        MotherTongue = motherTongue;
    }

    public String getAppLoginId() {
        return AppLoginId;
    }

    public void setAppLoginId(String appLoginId) {
        AppLoginId = appLoginId;
    }

    public String getMemberHeight() {
        return MemberHeight;
    }

    public void setMemberHeight(String memberHeight) {
        MemberHeight = memberHeight;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMemberCity() {
        return MemberCity;
    }

    public void setMemberCity(String memberCity) {
        MemberCity = memberCity;
    }

    public String getMemberInCome() {
        return MemberInCome;
    }

    public void setMemberInCome(String memberInCome) {
        MemberInCome = memberInCome;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }
}