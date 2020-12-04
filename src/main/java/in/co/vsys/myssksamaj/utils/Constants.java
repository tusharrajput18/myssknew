package in.co.vsys.myssksamaj.utils;

import android.os.Environment;

/**
 * Created by abhijeet on 29/9/15.
 */
public interface Constants {
    static final String APP_NAME = "MY SSK SAMAJ";
    String CONTACT_NUMBER = "9923449493";
    String RUPEE_SYMBOL = "₹ ";
    String DOLLAR_SYMBOL = "$ ";
    String VENDOR_ID = "1000";
    String PLATFORM = "Android";
    String CALLBACK_INTENT = "Callback";
    String SCHEDULE_INTENT = "Schedule";
    String LOGOUT = "Logout";
    String ACCESS_TOKEN = "B4BAFE77-7834-4ED4-B040-CCC615C2DC0B20190216101731";



    int TYPE_NUMBER = 1;
    int TYPE_DECIMAL = 2;
    int TYPE_CLEAR = 3;

    String MEDIA_REQUEST_INTENT = "MediaRequest";

    String MEMBER_ID = "memberId";
    String PACKAGE_ID = "packageId";
    String EMAIL = "email";
    String CONTACT_NO = "contactNo";

    public static final String NEWCANDIDATE ="newjointmodel" ;

    interface Receivers {
        String NETWORK_PERMISSION = "android.net.conn.CONNECTIVITY_CHANGE";
        String WIFI_PERMISSION = "android.net.conn.WIFI_STATE_CHANGED";

        String START_TRACKING = "com.axelbuzz.customlocationtracking.START_TRACKING";
        String STOP_TRACKING = "com.axelbuzz.customlocationtracking.STOP_TRACKING";
        String WHERE_ARE_YOU = "com.axelbuzz.customlocationtracking.WHERE_ARE_YOU";
    }

    interface ChatMessageState {
        String SEND = "1";
        String DELIVERD = "2";
        String READ = "3";
    }


    interface SCREEN {

        String CHAT_LIST = "ChatList";
    }

    interface ShareableIntents {

        String Screen = "screen";
        String IS_ONLINE = "isOnline";
        String MIN_AGE = "minAge";
        String MAX_AGE = "maxAge";
        String MIN_HEIGHT = "minHeight";
        String MAX_HEIGHT = "maxHeight";
        String MOTHER_TONGUE = "motherTongue";
        String MARRIED_STATUS = "marriedStatus";
        String COUNTRY_ID = "countryId";
        String STATE_ID = "stateId";
        String CITY_ID = "cityId";
        String GENDER = "gender";
        String INCOME_ID = "incomeId";
        String OCCUPATION_ID = "occupationId";
        String PHYSICALLY_DISABLE = "physicallyChallenged";
        String MANGLIK = "manglik";
        String USER_DETAILS = "UserDetails";
    }

    interface InstamojoCreds {
        public static String CLIENT_ID = "aozcso0lAjIR2GHnLN6H0XjFQReAY5a5t3E2ODK9";
        public static String CLIENT_SEARCH = "nmdKY4T0LCZPmWWtoOYGwL9zjgSJY8kZAiKmNo6yrCkRbIluYEJIsPbEyZ2bc4eI4cDe25i4NMmbocCIkzJU2xN2it5BVWasUEDEME3cAjOlX14iO2fAZr2O1znsk5Q7";
    }

    interface UpdateCustomerDetails {
        String Contact = "1";
        String Email = "2";
        String Address = "3";
        String Gender = "4";
        String Ethnicity = "5";
        String DOB = "6";
    }

    interface SERVICE_ACTION {
        String DELIVERD_MSG = "3";
        String READ_MSG = "4";

    }

    class CONTACT_TYPES {
        public static final int Mobile = 1;
        public static final int Landline = 2;
        public static final int Email = 3;
        public static final int Website = 4;
    }

    interface NotificationExtras {
        String HOME = "HomeFr";
//        String HOME = "HomeFr";
    }

    interface Duration {
        int TODAY = 1;
        int YESTERDAY = 2;
        int THIS_WEEK = 3;
        int THIS_MONTH = 4;
        int THIS_YEAR = 5;
        int CUSTOM_DATE = 6;
    }

    interface UserTypes {
        int Manufacturer = 1;
        int Supplier = 2;
        int Agency = 3;
        int Distributor = 4;
        int Retailer = 5;
        int Logistics = 6;
        int Marketting = 7;
        int Operations = 8;
        int Customer = 9;
        int Employee = 10;
    }

    interface AddressTypes {
        int HOME = 1;
        int BUSINESS = 2;
        int OTHER = 3;
    }

    interface EntityTypes {
        int VENDOR = 1;
        int CUSTOMER = 2;
        int ORDER = 3;
        int EVENT = 4;
        int STORY = 5;
        int MENU = 6;
        int SERVICE = 7;
        int PACKAGE = 8;
        int COMMENT = 9;
        int FACILITY = 10;
        int CUISINE = 11;
        int PRODUCT = 12;
        int INTEREST = 13;
        int VARIETY = 24;

        int RATE = 25;
        int OPERATION = 26;
        int IMP_DAY = 27;
        int PROFILE = 28;
        int SOCIETY = 29;
        int GEOLOCATION = 31;
        int MESSAGE = 32;
        int ATTRACTIONS = 33;
        int MENU_TYPE = 34;
        int CONTACT = 35;
        int ADDRESS = 37;
        int CATEGORY = 38;
        int FUNCTIONS = 39;
        int TAG = 40;
        int STALL_SIZE = 41;
        int SCHEDULE = 42;
        int MEETING = 43;
        int EVENT_VENDOR = 44;
        int MEDIA = 50;
        int SALES = 51;
        int PAYMENT = 52;
    }

    interface MediaType {
        int IMAGE = 1;
        int VIDEO = 2;
        int THUMBNAIL = 3;
        int COVER_PHOTO = 4;
        int CARD = 5;
        int BROCHURE = 6;
    }

    interface MediaCategory {
        int PROFILE = 1;
        int GALLERY = 2;
        int PACKAGE = 3;
        int DISCOUNT = 4;
        int OFFER = 5;
        int EVENT = 6;
        int CUISINE = 7;
        int MENU = 8;
        int PRODUCT_TYPE = 33;
    }

    interface FRAGMENT {
        String HOME = "HomeFr";
        String BOOKINGS = "My Orders";
        String SHORTLISTED = "Shortlisted";
        String REWARD_POINTS = "Reward points";
        String PAYMENTS = "Payments";
        String SETTINGS = "Settings";
        String MY_ACCOUNT = "My Account";
        String REQUEST_VISIT = "Request Visit";
        String SUPPORT = "Support";
        String REFER = "Refer";
        String FEEDBACK = "Feedback";
        String SERVICE_REQUESTS = "Service requests";
        String LOGOUT = "Logout";
        String CREATE_INVITE = "Create Invite";
        String PRODUCTS = "Products";
        String MACHINES = "Machines";
        String MY_ORDERS = "My orders";
    }

    interface OrderStatus {
        int Order_Placed = 1;
        int In_Progress = 2;
        int Completed = 3;
        int Dispatched = 4;
        int Closed = 5;
        int Cancelled = 6;
        int Rescheduled = 7;
    }

    interface UserModel {
        String UserId = "UserId";
        String Username = "Username";
        String Password = "Password";
        String DisplayName = "DisplayName";
        String ImageUrl = "ImageUrl";
        String LoginType = "LoginType";
        String SocialType = "SocialType";
        String SocialId = "SocialId";
        String FCM_TOKEN = "FCM_TOKEN";
        String AUTH_TOKEN = "AUTH_TOKEN";
    }

    interface TYPE {
        String TOTAL = "TOTAL";
        String GST = "GST";
        String VAT = "VAT";
    }

    interface LOGIN_STAGE {
        int SIGN_UP = 0;
        int LOGIN = 1;
        int ENTER_PASSWORD = 2;
    }

    interface LoginTypes {
        int Contact = 1;
        int Email = 2;
        int Google = 3;
        int Facebook = 4;
    }

    interface UNIT {
        public static final String PERCENT = "Percent";
        public static final String AMOUNT = "Amount";
    }

    class CURRENCY {
        public static final String INR = "INR";
        public static final String USD = "USD";
        public static final String Percent = "%";
    }

    String BOOKING_STATUS_INTENT = "BookingStatus";

    class BookingStatus {
        public static final int DEFAULT = 0;
        public static final int APPROVED = 1;
        public static final int DECLINED = 2;
    }

    String PAYMENT_RESULT = "PaymentResult";
    String PAYMENT_AMOUNT = "PaymentAmount";

    class PAYMENT_RESULT_TYPE {
        public static final int APPROVED = 1122;
        public static final int DECLINED = 1123;
    }

    interface PaymentTypes {
        int Inward = 1;
        int Outward = 2;
    }

    interface PaymentStatus {
        int Paid = 1;
        int Pending = 2;
    }

    class SocialMediaParams {
        public static final String EntityTypeId = "EntityTypeId";
        public static final String EntityId = "EntityId";
        public static final String CommentId = "CommentId";
    }

    String USERTYPE_VENDOR = "1";
    String USERTYPE_CUSTOMER = "2";
    String CALENDAR_INTENT = "Calendar";
    String BOOKING_ID_INTENT = "BookingId";
    String TRANSACTION_ID_INTENT = "TransactionId";
    String BOOKING_INTENT = "Booking";
    String LOCATION_INTENT = "Location";
    String PACKAGE_INCLUSIONS_INTENT = "PackageInclusions";
    String MENU_INTENT = "Menu";
    String MENU_TYPE_INTENT = "MenuType";
    String PRODUCT_INTENT = "Product";
    String PRODUCT_ORDER_INTENT = "ProductOrder";
    String CART_INTENT = "CartIntent";
    String ORDER_PRODUCT_INTENT = "OrderProduct";
    String ORDER_INTENT = "Order";
    String PRODUCT_TYPE_INTENT = "ProductType";
    String VARIETY_INTENT = "Variety";
    String SERVICE_INTENT = "Service";
    String RATE_INTENT = "Rate";
    String SALES_ORDER_INTENT = "SalesOrder";
    String QUICK_SELECT_INTENT = "QuickSelect";
    String OPERATIONS_INTENT = "Operations";
    String TAG_INTENT = "Tag";
    String FUNCTION_INTENT = "Function";
    String ENTITY_TYPE_ID_INTENT = "EntityTypeId";
    String ENTITY_ID_INTENT = "EntityId";
    String UNITS_INTENT = "Units";
    String MIN_QUANTITY_INTENT = "MinQuantity";
    String ATTRIBUTE_INTENT = "Attributes";
    String NOTIFICATION_INTENT = "Notification";
    String CONTACT_INTENT = "Contact";

    String MEDIA_SOURCE = "MediaSource";
    String MEDIA_TYPE = "MediaType";
    String MEDIA_CATEGORY = "MediaCategory";
    String MEDIA_UPLOAD = "MediaUpload";

//    String IMAGES_FOLDER = Environment.getExternalStorageDirectory()
//            + "/myssksamaj/images/";

    public static final String IMAGES_FOLDER = Environment.getExternalStorageDirectory()
            + "/data/data/in.co.vsys.myssksamaj/images/";

    String DOCUMENT_FOLDER = Environment.getExternalStorageDirectory()
            + "/myssksamaj/documents/";

    int CAMERA_REQUEST = 1, GALLERY_REQUEST = 2, PROFILE_REQUEST = 3;

    int VIDEO_REQUEST = 44;

    int MAX_IMAGE_SIZE = 5;
    int MAX_NO_OF_IMAGES = 5;
    int IMAGE_QUALITY = 50;

    /* API's/URLS */
    String UPDATE_IMAGES_API = "updateImage";
    String IMAGE_UPLOAD_API = "/upload_image.php";
    String ROUTE_FRAGMENT_TAG = "route_fragment";

    //    SHARING INTENTS
    String MEDIA_INTENT = "Media";
    String LIST_INDEX = "Index";
    String VENDOR_INTENT = "Vendor";
    String DATE_INTENT = "DateIntent";
    String ADDRESS_INTENT = "Address";
    String ADDRESS_TITLE_INTENT = "AddressTitle";
    String PACKAGE_INTENT = "Package";
    String EVENT_INTENT = "EventIntent";
    String EVENT_ID_INTENT = "EventIdIntent";
    String CATEGORY_INTENT = "Category";
    String STORY_INTENT = "Story";
    String PAYMENT_INTENT = "Payment";

    String candidate = "In my own words";

    class USER_TYPE {
        String CUSTOMER = "customer";
        String EMPLOYEE = "Employee";
    }


    class ERROR_HANDLING {
        public static final String NULL = "Null";
        public static final String NO_DATA_FOUND = "No data found";
        public static final String NO_RESULTS_FOUND = "No results found. Please try again";
        public static final String NO_NETWORK1 = "No Network. Please try again";
        public static final String NO_NETWORK2 = "Unable to resolve host \"www.functionam.com\": No address associated with hostname";
        public static final String SERVER_ERROR = "Server error";
    }

    String[] EventTypes = {"General",
            "Exclusive",
            "Featured",
            "Rated",
            "Promotional",
            "Occasional",
            "Festive"};
}