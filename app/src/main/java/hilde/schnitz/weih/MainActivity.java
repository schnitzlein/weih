package hilde.schnitz.weih;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

import static hilde.schnitz.weih.R.id.button;

public class MainActivity extends AppCompatActivity {

    public Vector<String> setupFoodList() {
        Vector<String> v = new Vector<String>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            Vector<String> v1 = setupFoodList();  // bad style should only be loaded one time

            public void onClick(View v) {

                Random r = new Random();
                int rand_number = r.nextInt(v1.size());
                String food = getFood(rand_number, this.v1);

                TextView tv = (TextView)findViewById(R.id.textview_essen);
                //tv.setText("Nudeln");
                tv.setText(food.toCharArray(),0, food.length());
            }
        });


    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }
}
