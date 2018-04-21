package org.hotteam67.common;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Created by Jakob on 3/26/2017.
 */

public final class FileHandler
{
    public static final String SERVER_FILE = "serverDatabase.json"; // Server scouted matches database
    public static final String SCHEMA_FILE = "schema.csv"; // Server/scouter schema
    public static final String SCOUTER_FILE = "scouterDatabase.csv"; // Scouter scouted/unscouted matches database
    public static final String MATCHES_FILE = "serverMatches.csv"; // Server unscouted matches datbase (match schedule)
    public static final String TEAM_NAMES_FILE = "teamNames.json";
    public static final String RANKS_FILE = "teamRanks.json";
    public static final String CUSTOM_TEAMS_FILE = "customTeams.csv";
    public static final String ALLIANCES_FILE = "alliances.csv";
    public static final String VIEWER_MATCHES_FILE = "viewerMatches.csv";
    public static final String AVERAGES_CACHE = "averagesCache";
    public static final String MAXIMUMS_CACHE = "maximumsCache";
    public static final String RAW_CACHE = "matchesCache";
    private static final String DIRECTORY =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/BluetoothScouter/";


    private static String file(String file)
    {
        return DIRECTORY + file;
    }

    public static BufferedReader GetReader(String FILE)
    {
        try
        {
            return new BufferedReader(new FileReader(file(FILE)));
        }
        catch (FileNotFoundException e)
        {
            l("File not found: " + file(FILE));
            new File(DIRECTORY).mkdirs();

            try
            {
                new File(file(FILE)).createNewFile();
                return new BufferedReader(new FileReader(file(FILE)));
            }
            catch (Exception ex)
            {
                return null;
            }
        }
        catch (Exception e)
        {
            l("Exception occured in loading reader : " + e.getMessage());
        }

        return null;
    }

    private static FileWriter GetWriter(String FILE)
    {
        String f = file(FILE);

        try
        {
            FileWriter writer = new FileWriter(new File(f).getAbsolutePath(), false);
            return writer;
        }
        catch (FileNotFoundException e)
        {
            l("File not found");
            new File(DIRECTORY).mkdirs();

            try
            {
                new File(f).createNewFile();
                return new FileWriter(new File(f).getAbsolutePath(), false);
            }
            catch (Exception ex)
            {
                return null;
            }
        }
        catch (Exception e)
        {
            l("Exception occured in loading reader : " + e.getMessage());
        }

        return null;
    }

    public static String LoadContents(String file)
    {
        String content = "";
        BufferedReader r = GetReader(file);
        try
        {
            String line = r.readLine();
            while (line != null)
            {
                content += line + "\n";
                line = r.readLine();
            }
        }
        catch (Exception e)
        {
            l("Failed to load :" + e.getMessage());
        }

        return content;
    }

    public static void Write(String FILE, String s)
    {
        try
        {
            FileWriter w = GetWriter(FILE);
            if (w != null)
            {
                w.write(s);
                w.close();
            }
        }
        catch (Exception e)
        {
            l("Failed to write: " + s + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void Serialize(Serializable o, String file)
    {
        try {
            if (file(file) == null)
                return;
            FileOutputStream fileOutputStream = new FileOutputStream(file(file));
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(o);
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Serializable DeSerialize(String file)
    {
        try
        {
            if (file(file) == null)
                return null;
            FileInputStream fileInputStream = new FileInputStream(file(file));
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Object returnObject = inputStream.readObject();

            inputStream.close();

            return (Serializable)returnObject;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static final void l(String s)
    {
        Log.d("[File Handling]", s);
    }
}
