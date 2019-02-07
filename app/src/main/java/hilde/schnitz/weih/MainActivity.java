package hilde.schnitz.weih;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


import static hilde.schnitz.weih.R.id.button;

//TODO: refactor me, zwischenebene einziehen und die 15 random Werte vorhalten, aus denen aussuchen...
//TODO: if created again save the latest random value
//TODO: create my onclick listener and forward data
//TODO: make the whole generation process for 7 days ( each day only one (kartoffel, reis, nudeln .. and compare)
//TODO: Braten nur am Sonntag, Feiertage ggfs
public class MainActivity extends AppCompatActivity {

    public final int FOOD_LIST_SIZE = 70;
    public String current_foodname = new String("nix");
    //SQLiteDatabase myDB = null;
    private DBHelper mydb;

    //SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
    //SharedPreferences.Editor editor = pref.edit();

    private int getMyRandom(){
        Random r = new Random();
        int rand_number = r.nextInt(FOOD_LIST_SIZE);
        return rand_number;
    }

    private int getLatestID(SQLiteDatabase foobar){
        Cursor c = this.getAllRows(foobar);
        int MAX_ROWS = c.getCount();
        return MAX_ROWS;
    }

    private Cursor getAllRows(SQLiteDatabase db){
        Cursor result = db.query("food",
                new String[] {"id", "foodname"},
                null,
                null,
                null,
                null,
                "id DESC");
        return result;
    }

    private Cursor getFoodNameForID(SQLiteDatabase db, int id){
        Cursor result = db.query("food",
                new String[] {"foodname"},
                "id = ?",
                new String[] {""+id},
                null,
                null,
                null);
        return result;
    }

    private void addEntry(String f, SQLiteDatabase foobar){
        ContentValues row1 = new ContentValues();
        row1.put("foodname", f);
        foobar.insert("weih", null, row1);
    }

    /*
    public void generateDataBase() {
        //myDB = SQLiteDatabase.openDatabase("weih.db", null, SQLiteDatabase.OPEN_READONLY);
        //if (myDB == null) {
            List<String>foodlist = setupFoodList();
            myDB = openOrCreateDatabase("weih.db", MODE_PRIVATE, null);
            //myDB.execSQL("DROP TABLE IF EXISTS food");
            myDB.execSQL("CREATE TABLE IF NOT EXISTS food (id INTEGER PRIMARY KEY AUTOINCREMENT, foodname VARCHAR(200), allow_sunday INT)"
            );
            Log.i("foobar",String.valueOf(getLatestID(myDB)));
            // DB has less entry or is empty
            if (this.getLatestID(myDB) < foodlist.size()){
                for (String temp : foodlist) {
                    this.addEntry(temp, myDB);
                    Log.i("foobar", temp);
                }
            }
            Log.i("foobar",String.valueOf(getLatestID(myDB)));
        //}
    }
    */

    private String retrieveData(SQLiteDatabase localDB, int id) {
        String ret = new String();
        boolean not_sunday = false;  // all food can be eaten at any day, if not it becomes true
        String foodname = new String();

        Cursor myCursor =
                localDB.rawQuery("select foodname, allow_sunday from food where id=" + id, null);
        while(myCursor.moveToNext()) {
            foodname = myCursor.getString(0);
            not_sunday = (myCursor.getInt(1)) == 1 ? true:false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        if (dayOfTheWeek.equals("Sunday")){
            if (not_sunday == true){
                ret = retrieveData(localDB, this.getMyRandom());
            }
        } else {
            ret = foodname;
        }
        myCursor.close();
        return ret;
    }

    public List<String> setupFoodList() {
        List<String> v = new ArrayList<String>();
        v.add("Mediterrane Nudelsuppe");
        v.add("Nudelsuppe");
        v.add("Cannelloni");
        v.add("Lasagne");
        v.add("Gemüselasagne");
        v.add("Nudeln mit Brokkoli");
        v.add("Spaghetti Carbonara");
        v.add("Nudelsalat");
        v.add("Nudelauflauf");
        v.add("Nudeln mit Tomatensoße");
        v.add("Krautnudeln");
        v.add("Nudeln mit Maggi");
        v.add("Nudeln mit Lachs");
        v.add("Kartoffeln mit Quark");
        v.add("Senfeier");
        v.add("Königsberger Klopse");
        v.add("Bouletten");
        v.add("Überbackene Hackbällchen");
        v.add("Kartoffeln mit Spinat und Ei");
        v.add("Kartoffeln mit Gemüse");
        v.add("Gemüseblech");
        v.add("Kartoffelecken");
        v.add("Kartoffelauflauf");
        v.add("Bratkartoffeln");
        v.add("Kartoffelbrei mit Leber");
        v.add("Kartoffelbrei mit fisch");
        v.add("Kartoffelsuppe");
        v.add("Käse-Lauch-suppe");
        v.add("Brokkolisuppe");
        v.add("Spargelsuppe");
        v.add("Kirschsuppe");
        v.add("Erbsensuppe");
        v.add("Linsensuppe");
        v.add("Kürbissuppe");
        v.add("Gemüseblech");
        v.add("Gefüllte Paprika mit Reis");
        v.add("Frikassee mit Reis");
        v.add("Letscho mit Reis");
        v.add("Reis mit Scheiß");
        v.add("Reis mit Reis");
        v.add("Stulle mit Brot");
        v.add("Gulasch");
        v.add("Schweinebraten");
        v.add("Rouladen");
        v.add("Kassler");
        v.add("Grießbrei");
        v.add("Blätterteigrollen");
        v.add("Pizza");
        v.add("Flammkuchen");
        v.add("Kartoffelsalat");
        v.add("Gemischter Salat mit Mango");
        v.add("Gemischter salat mit Granatapfel");
        v.add("Eierkuchen");
        v.add("Milchreis");
        v.add("Quiche");
        v.add("Chinesische Nudeln");
        v.add("Hähnchencurry");
        v.add("Pilzpfanne");
        v.add("Zwiebelfleisch");
        v.add("Cordon Bleu");
        v.add("Schlemmerfilet");
        v.add("Fischstäbchen");
        v.add("Hühnersuppe");
        v.add("Toast Hawaii");
        v.add("nix");
        v.add("Bauerntopf");
        v.add("Chili Con carne");
        v.add("Lachswraps");
        v.add("Pangasiusfilet an Zitronenkartoffeln");
        return v;
    }

    /*
    public String getFood(int rand, Vector<String> vec) {
        String whatFood = new String();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                //TODO: check if some specific food is set or not
                //run rekursiv
                break;
                default:
                    whatFood = vec.get(rand);
        }
        return whatFood;
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ===== database stuff
        Log.i("foobar", "starting");
        mydb = new DBHelper(this);

        List<String> foobar = setupFoodList();
        int i_max = foobar.size();
        int i = 0;
        for (String temp : foobar) {
            mydb.insertFood(i, temp);
            i++;
        }
        Log.i("foobar", "done with database i: "+i + " i_max: "+i_max);
        // ===== database stuff

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            //current_foodname = savedInstanceState.getString(); //todo: load saved key pair data
        } else {
            // Probably initialize members with default values for a new instance
            current_foodname = "nix";
        }

        //this.generateDataBase();
        /*Log.i("foobar", "database stuff done");

        Cursor foo = this.getFoodNameForID(myDB, 33);
        String foo2 = foo.getString(1);

        Log.i("foobar", foo2);*/

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Random r = new Random();
                int rand_number = r.nextInt(FOOD_LIST_SIZE);
                //String food = getFood(rand_number, this.v1);

                TextView tv = (TextView)findViewById(R.id.textview_essen);
                //tv.setText("Nudeln");
                //tv.setText(food.toCharArray(),0, food.length());
                String food = mydb.getDataString(rand_number);
                tv.setText(food.toCharArray(),0, food.length());
                //current_foodname = food;

                Log.i("foobar",mydb.getDataString(rand_number));
                Log.i("foobar",""+mydb.numberOfRows());
            }
        });


    }

    @Override
    protected void onPause(){
        super.onPause();
        // save Shared ... Data
        //editor.putString("foodname", current_foodname);
        //editor.commit();
    }

    @Override
    protected void onResume(){
        super.onResume();
        // get Shared ... Data
        //String food = pref.getString("foodname", null);
        //TextView tv = (TextView)findViewById(R.id.textview_essen);
        //tv.setText(food.toCharArray(),0, food.length());
        //Log.i("foobar","old data was: "+food);
    }

    @Override
    protected void onStop(){
        super.onStop();
    }
}
