package com.example.root.intouchsmsapp.Fragments;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.root.intouchsmsapp.Activities.MessageLogSummary;
import com.example.root.intouchsmsapp.Adapters.SenderNamesAdapter;
import com.example.root.intouchsmsapp.ApiData.smsData;
import com.example.root.intouchsmsapp.CheckConnection.ConnectivityReceiver;
import com.example.root.intouchsmsapp.Models.senderNames;
import com.example.root.intouchsmsapp.R;
import com.example.root.intouchsmsapp.StartActivity;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.ACCOUNT_ROLE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class MessagingFragment extends Fragment {

    private View v;
    private MenuItem searchItem;
    private String token;
    private String role;
    private EditText msg, recipients, customsendername;
    private Spinner spinnner_sender_names;
    private ArrayList<senderNames> senderNameItems;
    private String selected_sendername;

    private Button sendSmsButton;
    private CardView customsendername1;
    private LinearLayout customsendername2;
    private boolean retrivedSenderNames = false;
    private ImageButton uploadcontactsbyphone, uploadcontactsbyexcel;

    private Uri fileUri;
    private String filePath;

    private static final int PERMISSION_REQUEST_CODE = 1;


    public static final int PICKFILE_RESULT_CODE = 1;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private TextView messagecounter;


    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // for the floating add button

        v = inflater.inflate(R.layout.messaging_fragment, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        spinnner_sender_names = v.findViewById(R.id.spn_sendername);
        recipients = v.findViewById(R.id.phone_numbers);
        customsendername = v.findViewById(R.id.sendername);
        messagecounter = v.findViewById(R.id.messagecount);

        msg = v.findViewById(R.id.message_tobe_sent);
        uploadcontactsbyphone = v.findViewById(R.id.uploadcontactsbyphone);
        uploadcontactsbyexcel = v.findViewById(R.id.uploadcontactsbyexcel);

        SharedPreferences prefs = this.getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        role = prefs.getString(ACCOUNT_ROLE, "None");
        token = prefs.getString(TOKEN, "None");

        customsendername1 = v.findViewById(R.id.customsendername1);
        customsendername2 = v.findViewById(R.id.customsendername2);


        if (!role.equals("Agent") && !role.equals("Reseller")){
            customsendername2.setVisibility(View.GONE);
            if (checkConnection()){
                getSenderNames(token);
            } else {
                Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }

        } else {
            customsendername1.setVisibility(View.GONE);
        }

        //listener on the message field

        msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int totalchar = charSequence.length();
                int messagecount=0;
                int mode=160;

                if(totalchar<=160){
                    mode=160;

                    if((totalchar%mode)==0){
                        messagecount=totalchar/mode;

                        if (totalchar==1){
                            messagecounter.setText(Integer.toString(totalchar)+" Character of message " +Integer.toString(messagecount)+".");
                        }
                        else if (totalchar==0){
                            messagecounter.setText(Integer.toString(totalchar)+" Characters of message 1." );
                        }
                        else if(messagecount>1){
                            int length=(totalchar-((messagecount-1)*mode));
                            messagecounter.setText(Integer.toString(length)+" Characters of message "  + Integer.toString(messagecount)+".");
                        }
                        else{
                            messagecounter.setText(Integer.toString(totalchar)+" Characters of message " +Integer.toString(messagecount)+".");
                        }
                    }
                    else{
                        messagecount=(totalchar/mode)+1;
                        int length=(totalchar-((messagecount-1)*mode));
                        if (length==1){
                            messagecounter.setText(Integer.toString(length)+" Character of message "+Integer.toString(messagecount)+'.');
                        }
                        else if (length==0){
                            messagecounter.setText(Integer.toString(length)+" Characters of message 1.");
                        }else{
                            messagecounter.setText(Integer.toString(length)+ " Characters of message " + Integer.toString(messagecount)+'.');
                        }
                    }

                }else{
                    mode=153;

                    if((totalchar%mode)==0){
                        messagecount=(totalchar/mode);

                        if (totalchar==1){
                            messagecounter.setText(Integer.toString(totalchar)+" Character of message "+Integer.toString(messagecount)+'.');
                        }
                        else if (totalchar==0){
                            messagecounter.setText(Integer.toString(totalchar)+" Characters of message 1.");
                        }
                        else if(messagecount>1){
                            int length=(totalchar-((messagecount-1)*mode));
                            messagecounter.setText(Integer.toString(length)+" Characters of message "+Integer.toString(messagecount)+'.');
                        }
                        else{
                            messagecounter.setText(Integer.toString(totalchar)+" Characters of message "+Integer.toString(messagecount)+'.');
                        }
                    }
                    else{
                        messagecount=(totalchar/mode)+1;
                        int length=(totalchar-((messagecount-1)*mode));
                        if (length==1){
                            messagecounter.setText(Integer.toString(length)+" Character of message "+Integer.toString(messagecount)+'.');
                        }
                        else if (length==0){
                            messagecounter.setText(Integer.toString(messagecount)+" Characters of message 1.");
                        }else{
                            messagecounter.setText(Integer.toString(length)+" Characters of message "+Integer.toString(messagecount)+'.');
                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sendSmsButton = v.findViewById(R.id.btn_send_sms);
        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int totalchars = msg.getText().length();

                if (totalchars <=160){
                    sendMessage();
                } else{

                    int messagecount=0;
                    if((totalchars%153)==0){
                        messagecount=(totalchars/153);
                    }
                    else{
                        messagecount=(totalchars/153)+1;
                    }

                    String text = "The message text to be sent exceeds 160 characters. Would you like to send it as "+Integer.toString(messagecount)+" messages?";
                    confirm(text);
                }








            }
        });

        uploadcontactsbyphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Phone", Toast.LENGTH_SHORT).show();
            }
        });

        uploadcontactsbyphone.setVisibility(View.GONE);

        uploadcontactsbyexcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkPermission()){
                    requestPermission();
                } else {
                    openFileManager();
                }
            }
        });

        return v;
    }

    //private void openFileManager(){
        //Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        //chooseFile.setType("application/x-excel");
        //chooseFile.setType("*/*");
        //chooseFile.setType("application/vnd.ms-excel");
        //chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        //chooseFile.setType("application/vnd.ms-excel");
        //startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
    //

    private void sendMessage(){

        if (checkConnection()){

            if (recipients.getText().toString().isEmpty() || msg.getText().toString().isEmpty()){
                if (recipients.getText().toString().isEmpty()){
                    recipients.setError("Required*");
                }

                if (msg.getText().toString().isEmpty()){
                    msg.setError("Required*");
                }
                return;
            }

            if (!role.equals("Agent") && !role.equals("Reseller")){

                if (recipients.getText().length() == 0 || msg.getText().length()==0){
                    Toast.makeText(getContext(), "Please check again your fields", Toast.LENGTH_SHORT).show();
                } else {
                    sendSmsView(token, selected_sendername, recipients.getText().toString(),
                            msg.getText().toString());
                }

            } else {
                selected_sendername = customsendername.getText().toString();
                int len = selected_sendername.length();

                if (len == 0){

                    customsendername.setError("Please provide a sender name");
                } else{

                    sendSmsView(token, selected_sendername, recipients.getText().toString(),
                            msg.getText().toString());
                }

            }

        } else {
            Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void confirm(String text){

        boolean wrapInScrollView = true;

        final MaterialDialog custom_dialog =new MaterialDialog.Builder(getContext())
                .title("Confirm Multiple Messages")
                .customView(R.layout.confirm_multiplesms, wrapInScrollView)
                .negativeText("Cancel")
                .positiveText("Confirm")
                .autoDismiss(false)
                .cancelable(false)
                .build();

        final View dialog_view =custom_dialog.getCustomView();
        TextView textView = (TextView) dialog_view.findViewById(R.id.confirm_multiplesmsview);
        textView.setText(text);

        custom_dialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15); // Your size
        View positiveAction = custom_dialog.getActionButton(DialogAction.POSITIVE);
        View negativeAction = custom_dialog.getActionButton(DialogAction.NEGATIVE);

        positiveAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                custom_dialog.dismiss();
            }
        });

        negativeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_dialog.dismiss();
            }
        });

        custom_dialog.show();
    }

    private void openFileManager() {
        String[] mimeTypes = {"application/vnd.ms-excel" , "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
        Intent searchExcel = new Intent();
        searchExcel.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        searchExcel.setAction(Intent.ACTION_GET_CONTENT);
        //searchExcel.addCategory(Intent.CATEGORY_OPENABLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            searchExcel.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                searchExcel.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            searchExcel.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }

        startActivityForResult(Intent.createChooser(searchExcel,"Choose a file"), PICKFILE_RESULT_CODE);
    }

    private void getSenderNames(final String token){
        senderNameItems = new ArrayList<>();
        StartActivity.showProgressDialog("Retrieving Sender Names Please wait...", getContext());
        Call<ArrayList> call = apiService.getSenderNames("Token "+token);
        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

                if (!response.isSuccessful()){
                    StartActivity.hideProgressDialog();
                    Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<String> resp = response.body();

                try {
                    retrivedSenderNames = true;
                    final JSONObject parentObject = new JSONObject(resp.get(0));
                    final JSONArray senderNames = parentObject.getJSONArray("response");
                    int total= senderNames.length();

                    if (total > 0){

                        for(int x = 0; x < total; x++){
                            senderNameItems.add(new senderNames(
                                    senderNames.getJSONObject(x).getJSONObject("fields").getJSONObject("sender").getJSONObject("fields").getString("sendername")
                            ));
                        }
                        spinnner_sender_names.setAdapter(new SenderNamesAdapter(getContext(), senderNameItems));
                        spinnner_sender_names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                senderNames senderNames1 = (com.example.root.intouchsmsapp.Models.senderNames) adapterView.getItemAtPosition(i);
                                selected_sendername = senderNames1.getSendernames();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        StartActivity.hideProgressDialog();
                    }



                } catch (JSONException e){
                    StartActivity.hideProgressDialog();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

                StartActivity.hideProgressDialog();
                Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean isString(String sendername)
    {

        int len = sendername.trim().length();

        for (int i = 0; i < len; i++) {
            if ((Character.isLetter(sendername.trim().charAt(i)) == false)) {
                return false;
            }
        }
        return true;

    }

    private boolean checkifNum(String sn){
        Boolean check = false;

        String no = "\\d*\\.?\\d*";
        CharSequence inputStr = sn;

        Pattern pte = Pattern.compile(no, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pte.matcher(inputStr);

        if (matcher.matches()){
            check = true;
        }

        return check;
    }

    private boolean isValidMobile(String phone){

        return phone.charAt(0) == '0' && phone.charAt(1) == '7' && phone.length() == 10;
        //return phone.charAt(0) == '0' && phone.charAt(1) == '7' && phone.length() == 10 && phone.matches("[0-9+]");
    }
    /*private boolean isValidMobile(String phone) {

        //Mobile Number validation criteria:

        //The first digit should contain number between 7 to 8.
        //The rest 9 digit can contain any number between 0 to 9.
        //The mobile number can have 10 digits also by including 0 at the starting.
        //The mobile number can be of 12 digits also by including 25 at the starting

        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or +25
        // 2) Then contains 7 or 8.
        // 3) Then contains 8 digits
        Pattern p = Pattern.compile("(0/+25)?[7-8][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));
    }*/

    public void sendSmsView(String token, String sendername, String contacts, String msgs){
        StartActivity.showProgressDialog("Sending messages, Please wait", getContext());
        smsData smsData = new smsData(contacts, sendername, msgs);
        Call<String> call = apiService.sendsms("Token "+token, contacts, sendername, msgs);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                recipients.setText("");
                msg.setText("");
                customsendername.setText("");
                messagecounter.setText("0 Characters of 160.");

                String resp = response.body();

                if (!response.isSuccessful()){
                    StartActivity.hideProgressDialog();
                    Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{

                    final JSONObject parentObject = new JSONObject(resp);

                    if (parentObject.getBoolean("success")){

                        int totalmsgs = parentObject.getJSONObject("summary").getInt("totalmessages");
                        StartActivity.hideProgressDialog();
                        Intent intent = new Intent(getContext(), MessageLogSummary.class);
                        getActivity().startActivityForResult(intent, 999);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    } else {

                        StartActivity.hideProgressDialog();
                        Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();

                    }

                }catch (JSONException e){
                    StartActivity.hideProgressDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                StartActivity.hideProgressDialog();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contactsuploadicon, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Toast.makeText(getContext(), "On Options item selected", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){

            case R.id.excelupload:
                test();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }*/

    private void uploadexcel(String file){

        Workbook workbook = null;
        try {

            WorkbookSettings ws = new WorkbookSettings();

            String fileExtensionName = file.substring(file.indexOf("."));

            if (!(fileExtensionName.equals(".xlsx") || fileExtensionName.equals(".xls"))){

                Toast.makeText(getActivity(), "Please choose an excel file with format xls", Toast.LENGTH_SHORT).show();
                return;

            } else {
                if (fileExtensionName.equals(".xlsx")){

                    java.io.File myFile = new java.io.File(file);
                    FileInputStream fis = new FileInputStream(myFile); // Finds the workbook instance for XLSX file XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);

                    XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
                    XSSFSheet mySheet = myWorkBook.getSheetAt(0);
                    Row myRow;
                    Iterator<Row> rowIterator = mySheet.iterator();

                    DataFormatter fmt = new DataFormatter();

                    /*for (int rowIndex = 0; rowIndex <= mySheet.getLastRowNum(); rowIndex++) {

                        String phonenumbers = recipients.getText().toString();
                        recipients.getText().clear();

                        myRow = mySheet.getRow(rowIndex);
                        if (myRow != null) {
                            org.apache.poi.ss.usermodel.Cell cell = myRow.getCell(0);
                            if (cell != null) {
                                // Found column and there is value in the cell.
                                switch (cell.getCellType()) {

                                    case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
                                        recipients.setText(phonenumbers + "\n" + cell.getStringCellValue());
                                        break;

                                    case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
                                        Toast.makeText(getActivity(), fmt.formatCellValue(cell), Toast.LENGTH_SHORT).show();
                                        recipients.setText(phonenumbers + "\n" + fmt.formatCellValue(cell));
                                        break;

                                    case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
                                        recipients.setText(phonenumbers + "\n" + Boolean.toString(cell.getBooleanCellValue()));
                                        break;

                                    default:
                                }
                            }
                        }

                        recipients.setSelection(recipients.getText().length());
                    }*/

                    while (rowIterator.hasNext()){

                        String phonenumbers = recipients.getText().toString();
                        recipients.getText().clear();

                        Row row = rowIterator.next();
                        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

                        while (cellIterator.hasNext()) {
                            org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();

                            switch (cell.getCellType()) {

                                case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
                                    recipients.setText(phonenumbers + "\n" + cell.getStringCellValue());
                                    break;

                                case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
                                    recipients.setText(phonenumbers + "\n" + fmt.formatCellValue(cell));
                                    break;

                                case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
                                    recipients.setText(phonenumbers + "\n" + Boolean.toString(cell.getBooleanCellValue()));
                                    break;

                                default:
                            }

                            recipients.setSelection(recipients.getText().length());
                        }
                    }

                    StartActivity.hideProgressDialog();

                    return;
                }
            }



            java.io.File file1 = new java.io.File(file);

            workbook = Workbook.getWorkbook(file1, ws);

            Sheet sheet = workbook.getSheet(0);
            //first value column, second value row

            for (int i = 0; i < workbook.getSheet(0).getRows(); i++) {

                String num = sheet.getCell(0, i).getContents().toString();
                String phonenumbers = recipients.getText().toString();
                recipients.getText().clear();
                recipients.setText(phonenumbers + "\n" + num);
                recipients.setSelection(recipients.getText().length());


                /*if (recipients.getText().toString().isEmpty()){
                    recipients.setText(num);

                } else {

                }*/

                /*if (i+1 == workbook.getSheet(0).getRows()){

                }*/
            }






            //read all columns

            /*for (int i = 0; i < workbook.getSheet(0).getRows(); i++) {
                int n_rows = workbook.getSheet(0).getRows();

                Toast.makeText(getActivity(), sheet.getCell(0, i).getContents(), Toast.LENGTH_SHORT).show();
                for (int j = 0; j < workbook.getSheet(0).getColumns(); j++) {
                    //String testData = workbook.getSheet(0).getCell(i, j).getContents();
                    Toast.makeText(getActivity(), workbook.getSheet(0).getCell(i, j).getContents().toString(), Toast.LENGTH_SHORT).show();
                }
            }*/

            /*Cell cell1 = sheet.getCell(0, 2);
            Toast.makeText(getActivity(), cell1.getContents(), Toast.LENGTH_SHORT).show();*/
            /*System.out.print(cell1.getContents() + ":");    // Test Count + :
            Cell cell2 = sheet.getCell(0, 1);
            System.out.println(cell2.getContents());        // 1

            Cell cell3 = sheet.getCell(1, 0);
            System.out.print(cell3.getContents() + ":");    // Result + :
            Cell cell4 = sheet.getCell(1, 1);
            System.out.println(cell4.getContents());        // Passed

            System.out.print(cell1.getContents() + ":");    // Test Count + :
            cell2 = sheet.getCell(0, 2);
            System.out.println(cell2.getContents());        // 2

            System.out.print(cell3.getContents() + ":");    // Result + :
            cell4 = sheet.getCell(1, 2);
            System.out.println(cell4.getContents());        // Passed 2*/

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {

            if (workbook != null) {
                workbook.close();
            }

        }

        StartActivity.hideProgressDialog();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

        /*if (ActivityCompat.shouldShowRequestPermissionRationale(ExcelActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(ExcelActivity.this, "Read External Storage permission allows us to do read excel files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(ExcelActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFileManager();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {

                    StartActivity.showProgressDialog("Uploading", getActivity());
                    fileUri = data.getData();
                    //filePath = fileUri.getPath();
                    uploadexcel(getPathFromURI(getActivity(), fileUri));

                }

                break;
        }
    }

    public static String getPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
