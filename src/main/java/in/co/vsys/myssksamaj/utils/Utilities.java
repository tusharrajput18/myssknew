package in.co.vsys.myssksamaj.utils;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.interfaces.ViewClickListener;
import in.co.vsys.myssksamaj.presenters.MainPresenter;

/**
 * @author Abhijeet.J
 */
public class Utilities {

    private static int screenHeight, screenWidth;
    private static int imageHeight, imageWidth;

    public static int getImageHeight() {
        return imageHeight;
    }

    public static void setImageHeight(int imageHeight) {
        Utilities.imageHeight = imageHeight;
    }

    public static int getImageWidth() {
        return imageWidth;
    }

    public static void setImageWidth(int imageWidth) {
        Utilities.imageWidth = imageWidth;
    }

    private void showUserReadableToast() {

    }

    public static void showLongToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showSmallToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showInternetErrorToast(Context context) {
        Toast.makeText(context, context.getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
    }

    public static void showUnsuccessfulToast(Context context) {
        Toast.makeText(context, context.getString(R.string.unsuccessful_upload_message), Toast.LENGTH_SHORT).show();
    }

    public static String getTextFromEditText(EditText editText) {


        String value = "" + editText.getText();
        if (value.trim().length() == 0) {
            value = "";
        } else {
            value = value.trim();
        }
        return value;
    }

    public static String getGeocodeFromText(EditText editText) {
        String value = "" + editText.getText();
        if (value.trim().length() == 0) {
            value = "0.0,0.0";
        } else {
            value = value.trim();
        }
        return value;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        else
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidNumber(CharSequence target) {
        if (target == null)
            return false;
        else
            return target.length() >= 10 && Patterns.PHONE.matcher(target).matches();
    }

    /**
     * When value has to checked whether it is number or email
     *
     * @param username
     * @return
     */
    public final static boolean validateEmailAndContact(String username) {

        if (username == null)
            return false;

        if (isValidEmail(username) || isValidNumber(username))
            return true;

        return false;
    }

    /**
     * If email is not null check if valid. If contact is not null check if valid
     *
     * @param email
     * @param contact
     * @return
     */
    public final static boolean validateEmailOrContact(String email, String contact) {

        if (email != null && isValidEmail(email)) {
            return true;
        } else if (contact != null && isValidNumber(contact)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isInternetAvailable(Context context, final InternetConnectionListener internetConnectionListener) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() == null) {
                internetConnectionListener.isInternetConnected(false);
                return false;
            }

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {

                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://www.google.com/");
                            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                            urlc.setRequestProperty("User-Agent", "test");
                            urlc.setRequestProperty("Connection", "close");
                            urlc.setConnectTimeout(1000); // mTimeout is in seconds
                            urlc.connect();
                            if (urlc.getResponseCode() == 200) {
                                internetConnectionListener.isInternetConnected(true);
                            } else {
                                internetConnectionListener.isInternetConnected(false);
                            }

                        } catch (Exception e) {
                            Log.i("warning", "Error checking internet connection", e);
                            internetConnectionListener.isInternetConnected(false);
                        }
                    }
                });
                thread.start();
            }
        } catch (Exception e) {
            internetConnectionListener.isInternetConnected(false);
            return false;
        }
        return false;
    }

    public static boolean isEmpty(EditText editText) {
        if (extractDataFromEditText(editText).trim().isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean isEmpty(TextView editText) {
        if (extractDataFromTextView(editText).trim().isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean isEmpty(String string) {
        if (string == null || string.isEmpty() || string.trim().isEmpty())
            return true;

        return false;
    }

    public static boolean isEmpty(String... strings) {
        for (String string : strings) {
            if (getString(string).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public static float getAmountValueFromText(EditText editText) {

        float amount = 0;
        if (isEmpty(editText))
            return amount;

        String edittextValue = getTextFromEditText(editText);
        if (isEmpty(edittextValue))
            return amount;

        amount = Float.parseFloat(edittextValue);
        return amount;
    }

    public static boolean isEmpty(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (getString(editText).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public interface InternetConnectionListener {
        void isInternetConnected(boolean isConnected);
    }

    public static String extractDataFromEditText(EditText editText) {
        if (editText == null)
            return "";
        String text = "" + editText.getText();

        return getString(text);
    }

    public static String extractDataFromTextView(TextView textView) {
        if (textView == null)
            return "";
        String text = "" + textView.getText();

        return text.trim();
    }

    public static boolean extractDataFromCheckedTextView(CheckedTextView checkedTextView) {
        if (checkedTextView == null)
            return false;

        return checkedTextView.isChecked();
    }

    public static boolean extractDataFromRadioButton(RadioButton radioButton) {
        if (radioButton == null)
            return false;

        return radioButton.isChecked();
    }

    public static void setDataToTextView(TextView textView, String text) {
        if (textView == null || text == null) {
            textView.setText("");
            return;
        }

        textView.setText(text);
    }

    public static void setText(final TextView textView, String text, final ViewClickListener viewClickListener) {
        if (textView == null || text == null) {
            textView.setText("");
            return;
        }
        textView.setText(text);
        if(viewClickListener == null)
            return;

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClickListener.onViewClick(textView);
            }
        });
    }

    public static void setDataAndVisibility(TextView textView, String text) {
        if (isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }

        textView.setText(text);
        textView.setVisibility(View.VISIBLE);
    }

    public static void setDataAndVisibility(TextView textView, String label, String text) {
        if (isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }

        textView.setText(label + ": " + text);
        textView.setVisibility(View.VISIBLE);
    }

    public static void setQuantityAndUnit(TextView textView, String quantity, String unit) {
        if (quantity == null || quantity.trim().length() == 0)
            return;

        textView.setText(quantity + " " + getString(unit));
        textView.setVisibility(View.VISIBLE);
    }

    public static void clearText(TextView textView) {
        if (textView == null)
            return;

        textView.setText("");
    }

    public static String getString(String text) {
        if (text == null)
            return "";

        return text.trim();
    }

    public static String getString(EditText textView) {

        return extractDataFromEditText(textView).trim();
    }

    public static String getAmountString(TextView textView) {

        return extractDataFromTextView(textView).replace(Constants.RUPEE_SYMBOL, "").trim();
    }

    public static String getString(TextView textView) {

        return extractDataFromTextView(textView).trim();
    }

    public static String getString(int intValue) {
        return "" + intValue;
    }

    public static String getString(Object text) {
        if (text == null)
            return "";

        return "" + text;
    }

    public static void showDistance(TextView textView, float distance) {
        if (distance > 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            textView.setText(decimalFormat.format(distance) + " Kms");
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public static String showPercentage(double amount) {
        if (amount == 0)
            return "";

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(amount) + " %";
    }

    public static double calculateTax(double amount, double taxPercent) {
        if (amount == 0)
            return amount;

        amount = (amount * taxPercent) / 100;
        return amount;
    }

    public static String showFormattedPrice(double price) {

        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(price);
    }

    public static float convertDistance(float distance) {
        return distance / 1000;
    }

    public static boolean isListEmpty(List<?> list) {
        if (list == null || list.size() == 0)
            return true;

        return false;
    }

    public static String getImagePath(String imageUrl) {
        if (imageUrl == null)
            return "";

        return imageUrl.replace(" ", "%20");
    }

    public static void setText(View view, String text) {
        if (view == null || text == null)
            return;

        if (view instanceof EditText)
            ((EditText) view).setText(text);
        if (view instanceof TextView)
            ((TextView) view).setText(text);
        if (view instanceof CheckBox)
            ((TextView) view).setText(text);
    }

    public static void setText(TextView textView, String... strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            if (isEmpty(string))
                continue;

            if (stringBuilder.length() > 0)
                stringBuilder.append(" ");

            stringBuilder.append(getString(string));
        }

        textView.setText(stringBuilder.toString());
    }

    public static void setScreenDimensions(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void showQuantityAndUnit(TextView textView, String quantity, String unit) {
        if (textView == null || isEmpty(quantity) || isEmpty(unit)) {
            textView.setVisibility(View.GONE);
            return;
        } else {
            textView.setText(quantity + " " + unit);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public static int getInt(String string) {
        if (isEmpty(string))
            return 0;

        return Integer.parseInt(string);
    }

//    public static void setPagerIndicatorColor(CirclePageIndicator circlePageIndicator) {
//        circlePageIndicator.setFillColor(R.color.colorAccentDark);
//        circlePageIndicator.setStrokeColor(R.color.gray);
//    }

    public static float getFloatFromEdittext(EditText editText) {
        float val = 0;
        val = Float.parseFloat(extractDataFromEditText(editText));
        return val;
    }

    public static int getIntegerFromEdittext(EditText editText) {
        int val = 0;
        val = Integer.parseInt(extractDataFromEditText(editText));
        return val;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String escapeAllChars(String text) {
        text = text.replace("<br />", "\n");
        text = text.replace("&nbsp;", "");
        text = text.replace("t/s", "t's");

        text = text.replace("'", "''");
        text = text.replace(".trim", "");
        text = Html.fromHtml(text).toString();

        return text.trim();
    }

    public static boolean isIntentPresent(Intent intent) {
        if (intent == null)
            return false;
        if (intent.getExtras() == null)
            return false;

        return true;
    }

    public static boolean isIntentPresent(Bundle bundle) {
        if (bundle == null)
            return false;

        return true;
    }

    public static int getAge(String dob) {
        if(isEmpty(dob)){
            return 0;
        }
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] dob1 = dob.split("/");
        String dateOfBirth = dob1[2];
        return (year - Integer.parseInt(dateOfBirth));
    }

    public static String partiallyHideContact(String mobileNumber) {
        StringBuilder hiddenNumber = new StringBuilder();
        hiddenNumber.append(mobileNumber.substring(0, 2));
        hiddenNumber.append("XX");
        hiddenNumber.append(mobileNumber.substring(4, 6));
        hiddenNumber.append("XXXX");
        return hiddenNumber.toString();
    }

    public static String partiallyHideEmail(String email) {
        StringBuilder hiddenEmail = new StringBuilder();
        for (int i = 0; i < email.indexOf("@"); i++) {
            hiddenEmail.append("X");
        }
        hiddenEmail.append("@");

        int iStartIndex = email.indexOf("@");
        int iEndIndex = email.indexOf(".");

        for (int i = 0; i < (iEndIndex - iStartIndex) - 1; i++) {
            hiddenEmail.append("X");
        }

        hiddenEmail.append(email.substring(iEndIndex));

        return hiddenEmail.toString();
    }

    public static void saveContactNumber(Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, "My SSK Samaj");
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, Constants.CONTACT_NUMBER);
        int phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType);

        Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
// Get the newly created contact raw id.
        long ret = ContentUris.parseId(rawContactUri);
    }

    public static String getContactByNumber(Context context, String phoneNumber) {
        String contactId = null;
        try {
            if (phoneNumber != null && phoneNumber.length() > 0) {
                ContentResolver contentResolver = context.getContentResolver();

                Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));

                String[] projection = new String[]{ContactsContract.PhoneLookup._ID};

                Cursor cursor = contentResolver.query(uri, projection, null, null, null);

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
                    }
                    cursor.close();
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return contactId;
    }

    public static void deleteContactById(Context context, long id) {
        ContentResolver resolver = context.getContentResolver();
        try {
            Cursor cur = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + "="
                    + id, null, null);
            if (cur != null) {
                while (cur.moveToNext()) {
                    try {
                        String lookupKey = cur.getString(cur
                                .getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                                lookupKey);
                        resolver.delete(uri, ContactsContract.Contacts._ID + "=" + id, null);
                    } catch (Exception e) {
                        Log.e("Utilities", "deleteContactById: ", e);
                    }
                }
                cur.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkContactExist(Context context) {
        String id = getContactByNumber(context, Constants.CONTACT_NUMBER);
//        if (!Utilities.isEmpty(id)) {
////            deleteContactById(context, Long.parseLong(id));
////            checkContactExist(context);
//            return true;
//        }
        return !Utilities.isEmpty(id);
    }

    public static void addContact(Context context) {
        // Check whether contact exists
        if (checkContactExist(context))
            return;

        ArrayList<ContentProviderOperation> operationList = new ArrayList<ContentProviderOperation>();
        operationList.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // first and last names
        operationList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, "Second Name")
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, "My SSK Samaj")
                .build());

        operationList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, Constants.CONTACT_NUMBER)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());
        try {
            ContentProviderResult[] results = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operationList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showContactNumber(Context context, TextView textView, String contact) {
        if(Utilities.isEmpty(contact))
            return;

        if (!MainPresenter.getInstance().isPremiumMember(context))
            contact = Utilities.partiallyHideContact(contact);

        Utilities.setDataToTextView(textView, contact);
    }

    public static void showEmail(Context context, TextView textView, String email) {
        if(Utilities.isEmpty(email))
            return;

        if (!MainPresenter.getInstance().isPremiumMember(context))
            email = Utilities.partiallyHideEmail(email);
        Utilities.setDataToTextView(textView, email);
    }
}