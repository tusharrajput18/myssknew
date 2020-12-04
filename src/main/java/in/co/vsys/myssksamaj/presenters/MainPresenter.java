package in.co.vsys.myssksamaj.presenters;

import android.content.Context;
import android.net.Uri;

import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.PageSelectionListener;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.views.MainView;

/**
 * @author abhijeetjadhav
 */
public class MainPresenter {

    private static MainPresenter instance;
    private String memberName, emailId, phone, uniqueId;
    private int userType = -1, memberId;
    private Uri profileImage = null, idProofImage = null;
    private String contactsCredits = "";
    private MainView mainView;
    private PageSelectionListener pageSelectionListener;

    private MainPresenter() {
    }

    public static MainPresenter getInstance() {
        if (instance == null)
            instance = new MainPresenter();
        return instance;
    }

    public void setPageSelectionListener(PageSelectionListener pageSelectionListener) {
        this.pageSelectionListener = pageSelectionListener;
    }

    public int getMemberId(Context context) {
        if (Utilities.isEmpty(memberName))
            memberId = SharedPrefsHelper.getInstance(context).getIntVal(SharedPrefsHelper.MEMBER_ID);
        return memberId;
    }

    public void setMemberId(Context context, int memberId) {
        SharedPrefsHelper.getInstance(context).saveIntVal(SharedPrefsHelper.MEMBER_ID, memberId);
        this.memberId = memberId;
    }

    public void setPageTitle(String title){
        pageSelectionListener.onPageSelected(title);
    }

    public String getEmailId(Context context) {
        if (emailId == null)
            emailId = SharedPrefsHelper.getInstance(context).getStringVal(SharedPrefsHelper.EMAIL_ID);

        if (Utilities.isEmpty(emailId))
            emailId = "test@gmail.com";

        return emailId;
    }

    public void setEmailId(Context context, String emailId) {
        SharedPrefsHelper.getInstance(context).saveStringVal(SharedPrefsHelper.EMAIL_ID, emailId);
        this.emailId = emailId;
    }

    public String getPhone(Context context) {
        if (phone == null)
            phone = SharedPrefsHelper.getInstance(context).getStringVal(SharedPrefsHelper.MOBILE);

        return phone;
    }

    public void setPhone(Context context, String phone) {
        SharedPrefsHelper.getInstance(context).saveStringVal(SharedPrefsHelper.MOBILE, phone);
        this.phone = phone;
    }

    public String getMemberName(Context context) {
        if (Utilities.isEmpty(memberName))
            memberName = SharedPrefsHelper.getInstance(context).getStringVal(SharedPrefsHelper.MEMBER_NAME);

        return memberName;
    }

    public void setMemberName(Context context, String memberName) {
        SharedPrefsHelper.getInstance(context).saveStringVal(SharedPrefsHelper.MEMBER_NAME, memberName);
        this.memberName = memberName;
    }

    public boolean isPremiumMember(Context context) {
//        if (userType == -1)
//            userType = SharedPrefsHelper.getInstance(context).getIntVal(SharedPrefsHelper.USERTYPE);
//
//        return userType != 0;
        return true;
    }

    public void setUserType(Context context, int userType) {
        try {
            SharedPrefsHelper.getInstance(context).saveIntVal(SharedPrefsHelper.USERTYPE, userType);
            this.userType = userType;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUniqueId(Context context) {
        if (Utilities.isEmpty(uniqueId))
            SharedPrefsHelper.getInstance(context).getStringVal(SharedPrefsHelper.UNIQUE_ID);
        return uniqueId;
    }

    public void setUniqueId(Context context, String uniqueId) {
        SharedPrefsHelper.getInstance(context).saveStringVal(SharedPrefsHelper.UNIQUE_ID, uniqueId);
        this.uniqueId = uniqueId;
    }

    public Uri getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Uri profileImage) {
        this.profileImage = profileImage;
    }

    public Uri getIdProofImage() {
        return idProofImage;
    }

    public void setIdProofImage(Uri idProofImage) {
        this.idProofImage = idProofImage;
    }

    public String getContactsCredits(Context context) {
        contactsCredits = SharedPrefsHelper.getInstance(context).getStringVal(SharedPrefsHelper.CONTACTS_VIEWED);
        return contactsCredits;
    }

    public void setContactsCredits(Context context, String contactsCredits) {
        SharedPrefsHelper.getInstance(context).saveStringVal(SharedPrefsHelper.CONTACTS_VIEWED, contactsCredits);
        this.contactsCredits = contactsCredits;
    }

    public MainView getMainView() {
        return mainView;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
}