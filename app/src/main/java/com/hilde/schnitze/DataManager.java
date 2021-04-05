package com.hilde.schnitze;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class DataManager {

    private int FOOD_LIST_SIZE = 10;
    String essen_heute;
    List<String> food_list = new ArrayList<String>();

    Context context;

    DBHelper dbh;

    SharedPreferences sharedPreferences;

    DataManager(Context context) {
        this.context = context;
        dbh = new DBHelper(this.context);

        // TODO: put init of data in sub function if constructor is used twice in classes
        // TODO: create singleton (aka static object with semaphor)
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        boolean ret2 = dbh.hasDatabaseData(this.context);
        boolean ret = dbh.doesDatabaseExist(this.context);
        Log.i("DATA", ""+ret);

        boolean has_db_data = dbh.checkOrCreateDefaultData(this.context, this.setupFoodList());

        // rewrite Length
        FOOD_LIST_SIZE = this.setupFoodList().size();

        // read db data to SharedPreferences, if this shared data is not empty
        if (isCurrentFoodNameListEmpty()){
            List<String> my_list = dbh.getAllData();
            saveCurrentFoodList(my_list);
        }

        if (isCurrentFoodNameEmpty()){
            saveCurrentFood( getRandomFoodName() );
        }


    }

    // current Food single String
    public void saveCurrentFood(String current_foodname) {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FoodName", current_foodname);
        editor.commit();
        Log.i("DATA",current_foodname);
    }

    public String loadCurrentFood() {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FoodName", "");
    }

    public boolean isCurrentFoodNameEmpty() {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        boolean isFoodEmpty = sharedPreferences.getString("FoodName", "").isEmpty();
        return isFoodEmpty;
    }

    // week Food List of String
    public void saveCurrentFoodList(List<String> current_foodname_list) {
        Set<String> foodset = new HashSet<>(current_foodname_list); // framework depended cast, potential app crash here
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("FoodNameList",foodset);
        editor.commit();
        for (Iterator it = foodset.iterator(); it.hasNext();) {
            Log.i("DATA",it.next().toString());
        }
    }

    public List<String> loadCurrentFoodList() {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        Set<String> foodset = sharedPreferences.getStringSet("FoodNameList", null);
        List<String> targetList = new ArrayList<>(foodset);
        return targetList;
    }

    public boolean isCurrentFoodNameListEmpty() {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        Set<String> foodset = sharedPreferences.getStringSet("FoodNameList", null);
        boolean isFoodEmpty = true;
        if (foodset != null){
            isFoodEmpty = foodset.isEmpty();
        }

        return isFoodEmpty;
    }


    public String getRandomFoodName(){
        if (food_list.isEmpty()){
            food_list = setupFoodList();
        }
        essen_heute = food_list.get(this.getMyRandom());
        return essen_heute;
    }

    // TODO: add this sharedPreferences.registerOnSharedPreferenceChangeListener(); to react on change FOOD_LIST_SIZE
    public int getMyRandom(){
        this.FOOD_LIST_SIZE = dbh.numberOfRows();
        Random r = new Random();
        int rand_number = r.nextInt(FOOD_LIST_SIZE);
        return rand_number;
    }

    public int getMyRandom(int random_range){
        Random r = new Random();
        int rand_number = r.nextInt(random_range);
        return rand_number;
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

    // close sql connection, keeps android system clean
    public void closeDB() {
        dbh.closeDatabaseConnection();
    }

}