package in.co.vsys.myssksamaj.model;

/**
 * Created by Vysys on 23/03/2018.
 */

public class MatchesModel {

    private int userMemberId;
    private String uniqueId;
    private String profileCreatedBy;
    private String profileManagedBy;
    private String firstName;
    private String lastName;
    private String gender;
    private String userAge;
    private String userHeight;
    private String userCountry;
    private String userState;
    private String userCity;
    private String motherTongue;
    private String eduction;
    private String annualIncome;
    private String eductionIn;
    private String workingWith;
    private String workingAs;
    private String userProfileUrl;
    private String marriedStatus;
    private String profession;
    private String salary_details;
    private String family_type;
    private String father_status;
    private String father_company;
    private String father_post;
    private String mother_status;
    private String mother_company;
    private String mother_post;
    private String noOfBrothers;
    private String noOfSisters;
    private String noOfBrotherMarried;
    private String noOfSistersMarried;
    private String dateOfBirth;
    private String birthTime;
    private String birthPlae;
    private String gotra;
    private String rashi;
    private String nakshatra;
    private String naadi;
    private String gan;
    private String charan;
    private String manglik;
    private String partner_introduction;
    private String partner_introduction_video;
    private String kundli_photo;
    private String partner_min_age;
    private String partner_max_age;
    private String partner_min_height;
    private String partner_max_height;
    private String partner_mother_tounge;
    private String partner_married_status;
    private String partner_physical_status;
    private String partner_eating_habits;
    private String partner_smoking_habits;
    private String partner_drinking_habits;
    private String partner_eduction;
    private String partner_eduction_In;
    private String partner_working_with;
    private String partner_working_as;
    private String partner_income;
    private String partner_country;
    private String partner_state;
    private String partner_city;
    private String body_type;
    private int matchminAge;
    private int matchMaxAge;
    private int matchminHeight;
    private int matchMaxHeight;
    private int matchMStatus;
    private int matchFoodType;
    private int matchDrinkHabit;
    private int matchSmokeHabit;
    private String foodType;
    private String drinkingHabit;
    private String smokingHabit;
    private String complexion;
    private String bodyType;
    private String physicalChallege;
    private String shortlistedFlag;
    private String invitedFlag;
    private String onlieTime;
    private String offlineTime;
    private boolean isOnline;
    private String tokenId;
    private String inviteChatFlag;
    private String inviteReceivedFlag;
    private String onlinestatus;

    private String caste;
    private String subCaste;

    private String SenderBlocked;
    private String ispremium;

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

    public MatchesModel() {
    }

    public String getInviteReceivedFlag() {
        return inviteReceivedFlag;
    }

    public void setInviteReceivedFlag(String inviteReceivedFlag) {
        this.inviteReceivedFlag = inviteReceivedFlag;
    }

    public String getInviteChatFlag() {
        return inviteChatFlag;
    }

    public void setInviteChatFlag(String inviteChatFlag) {
        this.inviteChatFlag = inviteChatFlag;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getProfileManagedBy() {
        return profileManagedBy;
    }

    public void setProfileManagedBy(String profileManagedBy) {
        this.profileManagedBy = profileManagedBy;
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

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public int getUserMemberId() {
        return userMemberId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getProfileCreatedBy() {
        return profileCreatedBy;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public String getUserState() {
        return userState;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getMotherTongue() {
        return motherTongue;
    }

    public String getEduction() {
        return eduction;
    }

    public String getEductionIn() {
        return eductionIn;
    }

    public String getWorkingWith() {
        return workingWith;
    }

    public String getWorkingAs() {
        return workingAs;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public String getMarriedStatus() {
        return marriedStatus;
    }

    public String getProfession() {
        return profession;
    }

    public String getSalary_details() {
        return salary_details;
    }

    public String getFamily_type() {
        return family_type;
    }

    public String getFather_status() {
        return father_status;
    }

    public String getFather_company() {
        return father_company;
    }

    public String getFather_post() {
        return father_post;
    }

    public String getMother_status() {
        return mother_status;
    }

    public String getMother_company() {
        return mother_company;
    }

    public String getMother_post() {
        return mother_post;
    }

    public String getNoOfBrothers() {
        return noOfBrothers;
    }

    public String getNoOfSisters() {
        return noOfSisters;
    }

    public String getNoOfBrotherMarried() {
        return noOfBrotherMarried;
    }

    public String getNoOfSistersMarried() {
        return noOfSistersMarried;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public String getBirthPlae() {
        return birthPlae;
    }

    public String getGotra() {
        return gotra;
    }

    public String getRashi() {
        return rashi;
    }

    public String getNakshatra() {
        return nakshatra;
    }

    public String getNaadi() {
        return naadi;
    }

    public String getGan() {
        return gan;
    }

    public String getCharan() {
        return charan;
    }

    public String getPartner_introduction() {
        return partner_introduction;
    }

    public String getPartner_introduction_video() {
        return partner_introduction_video;
    }

    public String getKundli_photo() {
        return kundli_photo;
    }

    public String getPartner_min_age() {
        return partner_min_age;
    }

    public String getPartner_max_age() {
        return partner_max_age;
    }

    public String getPartner_min_height() {
        return partner_min_height;
    }

    public String getPartner_max_height() {
        return partner_max_height;
    }

    public String getPartner_mother_tounge() {
        return partner_mother_tounge;
    }

    public String getPartner_married_status() {
        return partner_married_status;
    }

    public String getPartner_physical_status() {
        return partner_physical_status;
    }

    public String getPartner_eating_habits() {
        return partner_eating_habits;
    }

    public String getPartner_smoking_habits() {
        return partner_smoking_habits;
    }

    public String getPartner_drinking_habits() {
        return partner_drinking_habits;
    }

    public String getPartner_eduction() {
        return partner_eduction;
    }

    public String getPartner_eduction_In() {
        return partner_eduction_In;
    }

    public String getPartner_working_with() {
        return partner_working_with;
    }

    public String getPartner_working_as() {
        return partner_working_as;
    }

    public String getPartner_income() {
        return partner_income;
    }

    public String getPartner_country() {
        return partner_country;
    }

    public String getPartner_state() {
        return partner_state;
    }

    public String getPartner_city() {
        return partner_city;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setUserMemberId(int userMemberId) {
        this.userMemberId = userMemberId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setProfileCreatedBy(String profileCreatedBy) {
        this.profileCreatedBy = profileCreatedBy;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

    public void setEduction(String eduction) {
        this.eduction = eduction;
    }

    public void setEductionIn(String eductionIn) {
        this.eductionIn = eductionIn;
    }

    public void setWorkingWith(String workingWith) {
        this.workingWith = workingWith;
    }

    public void setWorkingAs(String workingAs) {
        this.workingAs = workingAs;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public void setMarriedStatus(String marriedStatus) {
        this.marriedStatus = marriedStatus;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setSalary_details(String salary_details) {
        this.salary_details = salary_details;
    }

    public void setFamily_type(String family_type) {
        this.family_type = family_type;
    }

    public void setFather_status(String father_status) {
        this.father_status = father_status;
    }

    public void setFather_company(String father_company) {
        this.father_company = father_company;
    }

    public void setFather_post(String father_post) {
        this.father_post = father_post;
    }

    public void setMother_status(String mother_status) {
        this.mother_status = mother_status;
    }

    public void setMother_company(String mother_company) {
        this.mother_company = mother_company;
    }

    public void setMother_post(String mother_post) {
        this.mother_post = mother_post;
    }

    public void setNoOfBrothers(String noOfBrothers) {
        this.noOfBrothers = noOfBrothers;
    }

    public void setNoOfSisters(String noOfSisters) {
        this.noOfSisters = noOfSisters;
    }

    public void setNoOfBrotherMarried(String noOfBrotherMarried) {
        this.noOfBrotherMarried = noOfBrotherMarried;
    }

    public void setNoOfSistersMarried(String noOfSistersMarried) {
        this.noOfSistersMarried = noOfSistersMarried;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public void setBirthPlae(String birthPlae) {
        this.birthPlae = birthPlae;
    }

    public void setGotra(String gotra) {
        this.gotra = gotra;
    }

    public void setRashi(String rashi) {
        this.rashi = rashi;
    }

    public void setNakshatra(String nakshatra) {
        this.nakshatra = nakshatra;
    }

    public void setNaadi(String naadi) {
        this.naadi = naadi;
    }

    public void setGan(String gan) {
        this.gan = gan;
    }

    public void setCharan(String charan) {
        this.charan = charan;
    }

    public void setPartner_introduction(String partner_introduction) {
        this.partner_introduction = partner_introduction;
    }

    public void setPartner_introduction_video(String partner_introduction_video) {
        this.partner_introduction_video = partner_introduction_video;
    }

    public void setKundli_photo(String kundli_photo) {
        this.kundli_photo = kundli_photo;
    }

    public void setPartner_min_age(String partner_min_age) {
        this.partner_min_age = partner_min_age;
    }

    public void setPartner_max_age(String partner_max_age) {
        this.partner_max_age = partner_max_age;
    }

    public void setPartner_min_height(String partner_min_height) {
        this.partner_min_height = partner_min_height;
    }

    public void setPartner_max_height(String partner_max_height) {
        this.partner_max_height = partner_max_height;
    }

    public void setPartner_mother_tounge(String partner_mother_tounge) {
        this.partner_mother_tounge = partner_mother_tounge;
    }

    public void setPartner_married_status(String partner_married_status) {
        this.partner_married_status = partner_married_status;
    }

    public void setPartner_physical_status(String partner_physical_status) {
        this.partner_physical_status = partner_physical_status;
    }

    public void setPartner_eating_habits(String partner_eating_habits) {
        this.partner_eating_habits = partner_eating_habits;
    }

    public void setPartner_smoking_habits(String partner_smoking_habits) {
        this.partner_smoking_habits = partner_smoking_habits;
    }

    public void setPartner_drinking_habits(String partner_drinking_habits) {
        this.partner_drinking_habits = partner_drinking_habits;
    }

    public void setPartner_eduction(String partner_eduction) {
        this.partner_eduction = partner_eduction;
    }

    public void setPartner_eduction_In(String partner_eduction_In) {
        this.partner_eduction_In = partner_eduction_In;
    }

    public void setPartner_working_with(String partner_working_with) {
        this.partner_working_with = partner_working_with;
    }

    public void setPartner_working_as(String partner_working_as) {
        this.partner_working_as = partner_working_as;
    }

    public void setPartner_income(String partner_income) {
        this.partner_income = partner_income;
    }

    public void setPartner_country(String partner_country) {
        this.partner_country = partner_country;
    }

    public void setPartner_state(String partner_state) {
        this.partner_state = partner_state;
    }

    public void setPartner_city(String partner_city) {
        this.partner_city = partner_city;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public int getMatchminAge() {
        return matchminAge;
    }

    public void setMatchminAge(int matchminAge) {
        this.matchminAge = matchminAge;
    }

    public int getMatchMaxAge() {
        return matchMaxAge;
    }

    public void setMatchMaxAge(int matchMaxAge) {
        this.matchMaxAge = matchMaxAge;
    }

    public int getMatchMStatus() {
        return matchMStatus;
    }

    public void setMatchMStatus(int matchMStatus) {
        this.matchMStatus = matchMStatus;
    }

    public int getMatchFoodType() {
        return matchFoodType;
    }

    public void setMatchFoodType(int matchFoodType) {
        this.matchFoodType = matchFoodType;
    }

    public int getMatchDrinkHabit() {
        return matchDrinkHabit;
    }

    public void setMatchDrinkHabit(int matchDrinkHabit) {
        this.matchDrinkHabit = matchDrinkHabit;
    }

    public int getMatchSmokeHabit() {
        return matchSmokeHabit;
    }

    public void setMatchSmokeHabit(int matchSmokeHabit) {
        this.matchSmokeHabit = matchSmokeHabit;
    }

    public int getMatchminHeight() {
        return matchminHeight;
    }

    public void setMatchminHeight(int matchminHeight) {
        this.matchminHeight = matchminHeight;
    }

    public int getMatchMaxHeight() {
        return matchMaxHeight;
    }

    public void setMatchMaxHeight(int matchMaxHeight) {
        this.matchMaxHeight = matchMaxHeight;
    }
}
