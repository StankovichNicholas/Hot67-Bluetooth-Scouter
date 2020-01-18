package org.hotteam67.firebaseviewer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * The Viewer schema, should be updated to match the schema created in the Server app (or written manually for the brave)
 *
 * Need to update all three - sumcolumns, calculated columns, and preferred order. FIRST do sumc olumns, THEN preferred order,
 * THEN calculated columns
 *
 * A note about "raw names" - they are either the name as named in SumColumns, or the name as named in the schema.
 * If the raw name is from the schema it has the most recent header first, such as "Teleop Hatch Panels".
 * If the raw name is from SumColumns(), you obviously need to add a SumColumns() entry, then you can use calculated columns on that
 */
public class ColumnSchema {

    /**
     * The calculated columns desired names, first argument is name as it appears in the viewer, second is the raw name
     * @return the calculated columns to show in the main view
     */
    public static List<CalculatedColumn> CalculatedColumns() {
        List<CalculatedColumn> calculatedColumns = new ArrayList<>();
        /*
        calculatedColumns.add(new CalculatedColumn("To. Pieces", "Total Game Pieces"));
        calculatedColumns.add(new CalculatedColumn("To. Cargo", "Total Cargo"));
        calculatedColumns.add(new CalculatedColumn("To. Hatches", "Total Hatch Panels"));
        calculatedColumns.add(new CalculatedColumn("Tel. Cargo", "Teleop Cargo"));
        calculatedColumns.add(new CalculatedColumn("Tel. Hatches", "Teleop Hatch Panels"));
        calculatedColumns.add(new CalculatedColumn("High Cargo", "Total Cargo High"));
        calculatedColumns.add(new CalculatedColumn("High Hatches", "Total Hatch Panels High"));
        calculatedColumns.add(new CalculatedColumn("A. Cargo", "Auton Cargo"));
        calculatedColumns.add(new CalculatedColumn("A. Hatches", "Auton Hatch Panels"));
        calculatedColumns.add(new CalculatedColumn("Def. Time", "Endgame Defense Effective Seconds"));
        calculatedColumns.add(new CalculatedColumn("End HAB", "Endgame HAB Level"));
        calculatedColumns.add(new CalculatedColumn("Dropped Hatches", "Teleop Dropped Hatches"));
        calculatedColumns.add(new CalculatedColumn("A. Crossed", "Sandstorm Crossed The Line"));
        calculatedColumns.add(new CalculatedColumn("A. Crossed", "Sandstorm Crossed The Line"));
        calculatedColumns.add(new CalculatedColumn("A. Crossed", "Sandstorm Crossed The Line"));
        calculatedColumns.add(new CalculatedColumn("A. Crossed", "Sandstorm Crossed The Line"));
        calculatedColumns.add(new CalculatedColumn("A. Crossed", "Sandstorm Crossed The Line"));
        */
        calculatedColumns.add(new CalculatedColumn("To. Cells", "Total Cells"));
        calculatedColumns.add(new CalculatedColumn("Out. Port", "Total Outer Port"));
        calculatedColumns.add(new CalculatedColumn("In. Port", "Total Inner Port"));
        calculatedColumns.add(new CalculatedColumn("Miss", "Teleop Misses"));
        calculatedColumns.add(new CalculatedColumn("A. In. Port", "Auton Inner Port"));
        calculatedColumns.add(new CalculatedColumn("A. Out. Port", "Auton Outer Port"));
        calculatedColumns.add(new CalculatedColumn("Cross the Line", "Auton Cross the Line"));
        calculatedColumns.add(new CalculatedColumn("Hang", "Endgame Climb"));
        calculatedColumns.add(new CalculatedColumn("Lv.", "Endgame Level"));
        calculatedColumns.add(new CalculatedColumn("Tel. In. Port", "Teleop Inner Port"));
        calculatedColumns.add(new CalculatedColumn("Tel. Out. Port", "Teleop Outer Port"));
        calculatedColumns.add(new CalculatedColumn("P. Control", "Teleop Position Control"));
        calculatedColumns.add(new CalculatedColumn("R. Control", "Teleop Rotation Control"));
        calculatedColumns.add(new CalculatedColumn("Def.", "Defense Defended Against"));
        calculatedColumns.add(new CalculatedColumn("L. Port", "Total Lower Port"));
        calculatedColumns.add(new CalculatedColumn("Park", "Endgame Parked"));
        calculatedColumns.add(new CalculatedColumn("Block Shot", "Defense Blocked Shots"));
        calculatedColumns.add(new CalculatedColumn("Eff. Secs.", "Defense Effective Seconds"));
        calculatedColumns.add(new CalculatedColumn("Most Freq. Shoot. Pos.", "Teleop Most Frequent Shooting Position"));
        calculatedColumns.add(new CalculatedColumn("Climb. Pos.", "Endgame Climbing Position"));

        return calculatedColumns;
    }

    /**
     * Populate this with all of the various "raw names" either from SumColumns() or the schema.
     * THIS NEEDS TO BE DONE FOR THE CALCULATED COLUMNS - so if you have a calculated column "To. Pieces" then "Total Pieces"
     * needs to be somewhere in here. This is a quirk of using arbitrarily ordered JSON that I never properly fixed.
     *
     * The actual functionality of this list of names is that it determines the order in which these columns appear in raw data
     * @return A list of raw column names which determines the order they appear in the viewer's raw data view
     */
    public static List<String> PreferredOrder()
    {
        return new ArrayList<>(Arrays.asList(
                "Total Cells",
                "Total Inner Port", "Total Outer Port", "Total Lower Port",

                "Total Outer Port",
                "Auton Outer Port", "Teleop Outer Port",

                "Total Inner Port",
                "Auton Inner Port", "Teleop Inner Port",

                "Teleop Misses",

                "Auton Inner Port",

                "Auton Outer Port",

                "Endgame Climb",

                "Endgame Level",

                "Defense Blocked Shots",

                "Defense Effective Seconds",

                "Teleop Inner Port",

                "Teleop Outer Port",

                "Teleop Position Control",

                "Teleop Rotation Control",

                "Defense Defended Against",

                "Teleop Most Frequent Shooting Position",

                "Total Lower Port",
                "Auton Lower Port", "Teleop Lower Port",

                "Endgame Climbing Position",

                "Engame Parked",

                "Auton Cross the Line"

        ));

    }

    /**
     * List of columns to sum, will add a "raw column" for each match scouted with the new calculated value. You might be
     * able to use sum columns in other sum columns, but it is definitely SAFER TO JUST WRITE THEM ALL MANUALLY.
     *
     * Here this is accomplished by using addAll from the other sumcolumns, rather than rewriting everything.
     * @return List of sumcolumns
     */
    public static List<SumColumn> SumColumns() {


        SumColumn innerPort = BuildSumColumn("Total Inner Port", "Teleop Inner Port","Auton Inner Port");
        SumColumn outerPort = BuildSumColumn("Total Outer Port", "Teleop Outer Port", "Auton Outer Port");
        SumColumn lowerPort = BuildSumColumn("Total Lower Port", "Teleop Lower Port", "Auton Lower Port");
        /*
        SumColumn misses = BuildSumColumn("Miss", "Misses");
        SumColumn climb = BuildSumColumn("Hang", "Climb");
        SumColumn level = BuildSumColumn("Lv.", "Level");
        SumColumn positioncontrol = BuildSumColumn("P. Control", "Position Control");
        SumColumn rotationcontrol = BuildSumColumn("R. Control", "Rotation Control");
        SumColumn defense = BuildSumColumn("Def.", "Defended Against");
        SumColumn park = BuildSumColumn("Park", "Parked");
        SumColumn blockshot = BuildSumColumn("Block Shot", "Blocked Shot");
        SumColumn effectiveseconds = BuildSumColumn("Eff. Secs.", "Effective Seconds");
        */
        SumColumn totalCells = new SumColumn();
        totalCells.columnName = "Total Cells";
        totalCells.columnsNames = new ArrayList<>();
        totalCells.columnsNames.addAll(innerPort.columnsNames);
        totalCells.columnsNames.addAll(outerPort.columnsNames);
        totalCells.columnsNames.addAll(lowerPort.columnsNames);


        ArrayList<SumColumn> sumColumns = new ArrayList<>();
        // No auton hatches rn
        addAll(sumColumns, totalCells, innerPort, outerPort, lowerPort);

        return sumColumns;
    }

    /**
     * Utility macro
     * @param addTo list to populate
     * @param values variable argument array, makes writing this easier
     */
    private static void addAll(List<SumColumn> addTo, SumColumn... values) {
        addTo.addAll(Arrays.asList(values));
    }

    /**
     * Another simple builder
     * @param name "raw name" of the sumcolumn
     * @param columns variable argument array, with the actual other raw names to sum
     * @return SumColumn
     */
    private static SumColumn BuildSumColumn(String name, String... columns) {
        SumColumn column = new SumColumn();
        column.columnName = name;
        column.columnsNames = new ArrayList<>(Arrays.asList(columns));
        return column;
    }

    public static class CalculatedColumn {
        public final String RawName;
        public final String Name;

        public CalculatedColumn(String name, String rawName) {
            RawName = rawName;
            Name = name;
        }
    }

    /**
     * Sum column, can be serialized for easier loading and saving of the table to/from memory
     */
    public static class SumColumn implements Serializable {
        public List<String> columnsNames;
        public String columnName;
    }
}