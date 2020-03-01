package ew.finalwork.utilities;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import ew.finalwork.model.Test;

public class DataUtility {
    public static final String USERS = "users";
    public static final String TESTS = "tests";
    public static final String RESULTS = "results";
    public static final String USER_INFO = "user_info";
    public static final String METADATA = "metadata";
    public static final String TEST_NAME = "name";
    public static final String QUESTIONS = "questions";

    public static void synchronizeTests(Context context) {

    }

    public static void synchronizeTests(Context context, String email) {

    }

    public static void synchronizeTests(Context context, ArrayList<Test> tests) {
        for (Test test : tests) {
            writeTestToFile(context, test);
        }
        //context.stopService(new Intent(context, SyncService.class));
    }

    private static void writeTestToFile(Context context, Test test) {
        removeAllFiles(context);
        File file = new File(test.getTestName());
        try {
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(file.getName(), Context.MODE_PRIVATE)));
            bw.write(test.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeAllFiles(Context context) {
        File[] listOfFiles = context.getFilesDir().listFiles();
        for (File listOfFile : listOfFiles) {
            listOfFile.delete();
        }
    }
}
