package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 07/12/2017.
 */

public class RecentlyJointModel {

    private String SenderBlocked;
    private String ispremium;


    private int userMemberId;
    private String uniqueId;
    private String profileCreatedBy;
    private String profileManagedBy;
    private String firstName;
    private String lastName;
    private String Gender;
    private String DOB;
    private String userAge;
    private String userHeight;
    private String userCountry;
    private String userState;
    private String userCity;
    private String motherTongue;

    private String marriedStatus;
    private String eduction;
    private String eductionIn;
    private String workWith;
    private String workAs;
    private String userIncome;
    private String familyType;
    private String fatherStatus;
    private String fatherCompany;
    private String fatherPost;
    private String motherstatus;
    private String motherCompany;
    private String motherPost;
    private String noOfBrothers;
    private String noOfBrotherMarried;
    private String noOfSisters;
    private String noOfSistersMarried;
    private String birthTime;
    private String birthPlace;
    private String gotra;
    private String rashi;
    private String nakstra;
    private String charan;
    private String naaddi;
    private String gan;
    private String manglik;
    private String kundliPhoto;
    private String userProfileUrl;
    private String selfIntroduction;
    private String shortlistedFlag;
    private String invitedFlag;
    private String foodType;
    private String drinkingHabit;
    private String smokingHabit;
    private String complexion;
    private String bodyType;
    private String physicalChallege;
    private String onlieTime;
    private String offlineTime;
    private boolean isOnline;
    private String tokenId;
    private String inviteChatFlag;
    private String inviteReceivedFlag;
    private String onlinestatus;

    public String getSenderBlocked() {
        return SenderBlocked;
    }

    public void setSenderBlocked(String senderBlocked) {
        SenderBlocked = senderBlocked;
    }

    public String getIspremium() {
        return ispremium;
    }

    public void setIspremium(String ispremium) {
        this.ispremium = ispremium;
    }

    public String getOnlinestatus() {
        return onlinestatus;
    }

    public void setOnlinestatus(String onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public RecentlyJointModel() {
    }

    private String caste;
    private String subCaste;

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getSubCaste() {
        return subCaste;
    }

    public void setSubCaste(String subCaste) {
        this.subCaste = subCaste;
    }

    public String getInviteReceivedFlag() {
        return inviteReceivedFlag;
    }

    public void setInviteReceivedFlag(String inviteReceivedFlag) {
        this.inviteReceivedFlag = inviteReceivedFlag;
    }

    public String getProfileManagedBy() {
        return profileManagedBy;
    }

    public void setProfileManagedBy(String profileManagedBy) {
        this.profileManagedBy = profileManagedBy;
    }

    public String getInviteChatFlag() {
        return inviteChatFlag;
    }

    public void setInviteChatFlag(String inviteChatFlag) {
        this.inviteChatFlag = inviteChatFlag;
    }

    public RecentlyJointModel(int userMemberId, String uniqueId, String DOB, String userHeight, String userCountry, String userCity, String motherTongue, String userIncome, String userProfileUrl) {
        this.userMemberId = userMemberId;
        this.uniqueId = uniqueId;
        this.DOB = DOB;
        this.userHeight = userHeight;
        this.userCountry = userCountry;
        this.userCity = userCity;
        this.motherTongue = motherTongue;
        this.userIncome = userIncome;
        this.userProfileUrl = userProfileUrl;
    }

    public RecentlyJointModel(int userMemberId, String uniqueId, String userAge, String userHeight, String userIncome, String userCity, String motherTongue, String userProfileUrl, String marriedStatus, String userCountry, String eductionIn) {

        this.userMemberId = userMemberId;
        this.uniqueId = uniqueId;
        this.userAge = userAge;
        this.userHeight = userHeight;
        this.userIncome = userIncome;
        this.userCity = userCity;
        this.motherTongue = motherTongue;
        this.userProfileUrl = userProfileUrl;
        this.marriedStatus = marriedStatus;
        this.userCountry = userCountry;
        this.eductionIn = eductionIn;
    }


    public RecentlyJointModel(int userMemberId, String uniqueId, String userAge, String userHeight, String userIncome, String userCity, String motherTongue, String userProfileUrl, String marriedStatus, String userCountry) {
        this.userMemberId = userMemberId;
        this.uniqueId = uniqueId;
        this.userAge = userAge;
        this.userHeight = userHeight;
        this.userIncome = userIncome;
        this.userCity = userCity;
        this.motherTongue = motherTongue;
        this.userProfileUrl = userProfileUrl;
        this.marriedStatus = marriedStatus;
        this.userCountry = userCountry;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getOnlieTime() {
        return onlieTime;
    }

    public void setOnlieTime(String onlieTime) {
        this.onlieTime = onlieTime;
    }

    public String getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(String offlineTime) {
        this.offlineTime = offlineTime;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getDrinkingHabit() {
        return drinkingHabit;
    }

    public void setDrinkingHabit(String drinkingHabit) {
        this.drinkingHabit = drinkingHabit;
    }

    public String getSmokingHabit() {
        return smokingHabit;
    }

    public void setSmokingHabit(String smokingHabit) {
        this.smokingHabit = smokingHabit;
    }

    public String getComplexion() {
        return complexion;
    }

    public void setComplexion(String complexion) {
        this.complexion = complexion;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getPhysicalChallege() {
        return physicalChallege;
    }

    public void setPhysicalChallege(String physicalChallege) {
        this.physicalChallege = physicalChallege;
    }

    public String getManglik() {
        return manglik;
    }

    public void setManglik(String manglik) {
        this.manglik = manglik;
    }

    public String getShortlistedFlag() {
        return shortlistedFlag;
    }

    public void setShortlistedFlag(String shortlistedFlag) {
        this.shortlistedFlag = shortlistedFlag;
    }

    public String getInvitedFlag() {
        return invitedFlag;
    }

    public void setInvitedFlag(String invitedFlag) {
        this.invitedFlag = invitedFlag;
    }

    public String getProfileCreatedBy() {
        return profileCreatedBy;
    }

    public void setProfileCreatedBy(String profileCreatedBy) {
        this.profileCreatedBy = profileCreatedBy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getMarriedStatus() {
        return marriedStatus;
    }

    public void setMarriedStatus(String marriedStatus) {
        this.marriedStatus = marriedStatus;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getMotherTongue() {
        return motherTongue;
    }

    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public int getUserMemberId() {
        return userMemberId;
    }

    public void setUserMemberId(int userMemberId) {
        this.userMemberId = userMemberId;
    }

    public String getUserIncome() {
        return userIncome;
    }

    public void setUserIncome(String userIncome) {
        this.userIncome = userIncome;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEduction() {
        return eduction;
    }

    public void setEduction(String eduction) {
        this.eduction = eduction;
    }

    public String getEductionIn() {
        return eductionIn;
    }

    public void setEductionIn(String eductionIn) {
        this.eductionIn = eductionIn;
    }

    public String getWorkWith() {
        return workWith;
    }

    public void setWorkWith(String workWith) {
        this.workWith = workWith;
    }

    public String getWorkAs() {
        return workAs;
    }

    public void setWorkAs(String workAs) {
        this.workAs = workAs;
    }

    public String getFamilyType() {
        return familyType;
    }

    public void setFamilyType(String familyType) {
        this.familyType = familyType;
    }

    public String getFatherStatus() {
        return fatherStatus;
    }

    public void setFatherStatus(String fatherStatus) {
        this.fatherStatus = fatherStatus;
    }

    public String getFatherCompany() {
        return fatherCompany;
    }

    public void setFatherCompany(String fatherCompany) {
        this.fatherCompany = fatherCompany;
    }

    public String getFatherPost() {
        return fatherPost;
    }

    public void setFatherPost(String fatherPost) {
        this.fatherPost = fatherPost;
    }

    public String getMotherstatus() {
        return motherstatus;
    }

    public void setMotherstatus(String motherstatus) {
        this.motherstatus = motherstatus;
    }

    public String getMotherCompany() {
        return motherCompany;
    }

    public void setMotherCompany(String motherCompany) {
        this.motherCompany = motherCompany;
    }

    public String getMotherPost() {
        return motherPost;
    }

    public void setMotherPost(String motherPost) {
        this.motherPost = motherPost;
    }

    public String getNoOfBrothers() {
        return noOfBrothers;
    }

    public void setNoOfBrothers(String noOfBrothers) {
        this.noOfBrothers = noOfBrothers;
    }

    public String getNoOfBrotherMarried() {
        return noOfBrotherMarried;
    }

    public void setNoOfBrotherMarried(String noOfBrotherMarried) {
        this.noOfBrotherMarried = noOfBrotherMarried;
    }

    public String getNoOfSisters() {
        return noOfSisters;
    }

    public void setNoOfSisters(String noOfSisters) {
        this.noOfSisters = noOfSisters;
    }


    public String getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getGotra() {
        return gotra;
    }

    public void setGotra(String gotra) {
        this.gotra = gotra;
    }

    public String getRashi() {
        return rashi;
    }

    public void setRashi(String rashi) {
        this.rashi = rashi;
    }

    public String getNakstra() {
        return nakstra;
    }

    public void setNakstra(String nakstra) {
        this.nakstra = nakstra;
    }

    public String getCharan() {
        return charan;
    }

    public void setCharan(String charan) {
        this.charan = charan;
    }

    public String getNaaddi() {
        return naaddi;
    }

    public void setNaaddi(String naaddi) {
        this.naaddi = naaddi;
    }

    public String getGan() {
        return gan;
    }

    public void setGan(String gan) {
        this.gan = gan;
    }

    public String getKundliPhoto() {
        return kundliPhoto;
    }

    public void setKundliPhoto(String kundliPhoto) {
        this.kundliPhoto = kundliPhoto;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getNoOfSistersMarried() {
        return noOfSistersMarried;
    }

    public void setNoOfSistersMarried(String noOfSistersMarried) {
        this.noOfSistersMarried = noOfSistersMarried;
    }
}
