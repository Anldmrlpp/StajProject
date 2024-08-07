package com.StajProject.Company.exception;

public class ErrorMessages {

    private ErrorMessages(){}

    public static final String DEFAULT_ERROR_MESSAGE = "An unexpected error occurred! Please contact the api support!";

    public static final String EMPLOYEE_NOT_FOUND = "Employee Not Found!";

    public static final String DONT_HAVE_ENOUGH_PERMISSION = "You don't have enough permission days";

    public static final String PERMISSION_NOT_FOUND_FOR_EMPLOYEE = "Permission not found for this employee!";

    public static final String PERMISSION_NOT_FOUND = "Permission Not Found!";

    public static final String ADMIN_NOT_FOUND = "Admin Not Found";

    public static final String EMAIL_NOT_FOUND = "Email Not Found";

    public static final String UNAUTHORIZED = "UNAUTHORIZED";

    public static final String INCORRECT_LOGIN = "Incorrect Email or Password Entry";

    public static final String WRONG_ADMIN_KEY = "Wrong Admin Key. You Are Not Admin";

    public static final String FILE_NOT_FOUND = "File Not Found!";

    public static final String FILE_CANNOT_DELETE = "An error occurred while deleting the file!";

    public static final String FILE_CANNOT_WRITE = "An error occurred while uploading the file!";

    public static final String UNSUPPORTED_FILE_TYPE = "Unsupported file. Only PNG, JPEG, JPG, MP3, MP4, PDF and CSV supported";

    public static final String CONTACT_NOT_FOUND = "Message Not Found!";

    public static final String CONFIG_PROPERTIES_NOT_FOUND = "Config properties not found in the database";


}