package edu.umb.cs443.finalProject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Arrays;
import java.util.Random;

public class MainActivity extends Activity {

    private Integer xZone;
    private Integer yZone;
    private Integer column;
    private Integer row;
    private Integer panelKeeper;
    EditText inputNum;
    private Integer countX;
    GridView gridView;


    private static int w=9,curx,cury, prevx, prevy, tempx, tempy;

    private Random r = new Random();
    private Integer currentPos;
    String tiles[] = new String [81];

    public MainActivity() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView1);
        inputNum = (EditText) findViewById(R.id.editTextNumber);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, tiles);

        gridView.setAdapter(adapter);

        init();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                    Integer Clicked = new Integer(position);
                    panelKeeper = Clicked;
                    boolean illegalMove = false;
                    String input = inputNum.getText().toString();

                    /*
                    if ((input != "9") && (input != "8") &&(input != "7")&&
                            (input !="6")&& (input != "5")&& (input !="3")&&(input !="2")
                            &&(input !="1")&&(input !="4")){
                        Toast.makeText(getApplicationContext(),
                                (CharSequence) "invalid entry",
                                Toast.LENGTH_SHORT).show();
                       illegalMove = true;
                    }


                     */
                    //extract rows and columns info
                    column = Clicked%9 + 1;
                    row = Clicked/9 + 1;

                    //extract zones
                    if (tiles[Clicked] == "") {
                        xZone = ((Clicked%9)/3) + 1;
                        if (Clicked < 27){
                            yZone = 1;
                        }
                        else if (Clicked>26 && Clicked < 54){
                            yZone = 2;
                        }
                        else yZone = 3;
/*

                        // compare horizontal entries:
                        for (int i = 0; i < 9; i++){
                            int check = i + row;
                            check %= 9;
                            if (tiles[Clicked]== tiles[check]&& Clicked != check) {
                                illegalMove = true;
                            }
                        }


                        // compare vertical entries:
                        for (int i = 1; i< 10; i++){
                            int check = i * 10;
                            check %= 90;
                            if (tiles[Clicked]== tiles[i] && Clicked != check) {
                                illegalMove = true;
                            }
                        }

                        // compare group entries
                        if (yZone == 1 && xZone == 1){
                            String arr[] = {"0","1","2","9","10","11", "18", "19", "20"};
                            for(int i  = 0; i< arr.length; i++){
                                if (tiles[Clicked] == tiles[Integer.parseInt(arr[i])]&&
                                        Clicked != Integer.parseInt(arr[i])) {
                                    illegalMove = true;

                                }
                            }
                        }
                         if (yZone == 2 && xZone == 1){
                            String arr[] = {"27","28","29","36","37","38", "45", "46", "47"};
                            for(int i  = 0; i< arr.length; i++) {
                                if (tiles[Clicked] == tiles[Integer.parseInt(arr[i])]&&
                                        Clicked != Integer.parseInt(arr[i])) {
                                    illegalMove = true;
                                }
                            }
                        }
                        else if (yZone == 3 && xZone == 1){
                            String arr[] = {"54","55","56","63","64","65", "72", "73", "74"};
                            for(int i  = 0; i< arr.length; i++) {
                                if (tiles[Clicked] == tiles[Integer.parseInt(arr[i])]&&
                                Clicked != Integer.parseInt(arr[i])) {
                                    illegalMove = true;
                                }
                            }
                        }
                        else if (yZone == 1 && xZone == 2){
                            String arr[] = {"3","4","5","12","13","14", "21", "22", "23"};
                            for(int i  = 0; i< arr.length; i++) {
                                if (tiles[Clicked] == tiles[Integer.parseInt(arr[i])]&&
                                        Clicked != Integer.parseInt(arr[i])) {
                                    illegalMove = true;
                                }
                            }
                        }
                        else if (yZone == 2 && xZone == 2){
                            String arr[] = {"30","31","32","39","40","41", "48", "49", "50"};
                            for(int i  = 0; i< arr.length; i++) {
                                if (tiles[Clicked] == tiles[Integer.parseInt(arr[i])]&&
                                        Clicked != Integer.parseInt(arr[i])) {
                                    illegalMove = true;
                                }
                            }
                        }
                        else if (yZone == 3 && xZone == 2){
                            String arr[] = {"60","61","62","69","70","71", "78", "79", "80"};
                            for(int i  = 0; i< arr.length; i++) {
                                if (tiles[Clicked] == tiles[Integer.parseInt(arr[i])]&&
                                        Clicked != Integer.parseInt(arr[i])) {
                                    illegalMove = true;
                                }
                            }
                        }
                        else if (yZone == 1 && xZone == 3){
                            String arr[] = {"6","7","8","15","16","17", "24", "25", "26"};
                            for(int i  = 0; i< arr.length; i++) {
                                if (tiles[Clicked] == tiles[Integer.parseInt(arr[i])]&&
                                        Clicked != Integer.parseInt(arr[i])) {
                                    illegalMove = true;
                                }
                            }
                        }
                        else if (yZone == 2 && xZone == 3){
                            String arr[] = {"33","34","35","42","43","44", "51", "52", "53"};
                            for(int i  = 0; i< arr.length; i++) {
                                if (tiles[Clicked] == tiles[Integer.parseInt(arr[i])]&&
                                        Clicked != Integer.parseInt(arr[i])) {
                                    illegalMove = true;
                                }
                            }
                        }
                        else if (yZone == 3 && xZone == 3){
                            String arr[] = {"60","61","62","69","70","71", "78", "79", "80"};
                            for(int i  = 0; i< arr.length; i++) {
                                if (tiles[Clicked] == tiles[Integer.parseInt(arr[i])]&&
                                        Clicked != Integer.parseInt(arr[i])) {
                                    illegalMove = true;
                                }
                            }
                        }

 */

                        if (illegalMove == false) {
                            tiles[Clicked] = input;
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    (CharSequence) "illegal move",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                (CharSequence) "tile already populated",
                                Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(),
                        (CharSequence) (Clicked.toString()) + " -> " + (input)
                                + "\nyZone = " + (yZone).toString() + "\nxZone = "
                                +(xZone).toString() + "\ncolumn = "+(column).toString()
                                + "\nrow = " + (row).toString(),
                        Toast.LENGTH_SHORT).show();
                    panelKeeper = Clicked;
                    Runner runnable = new Runner(Clicked);
                    new Thread(runnable).start();

                /* tiles[currentPos]="";
                tiles[Clicked]="O";
                currentPos = Clicked;
                ((ArrayAdapter)gridView.getAdapter()).notifyDataSetChanged();
                 */

            }


        });

    }

    public void delete(View view){
        tiles[panelKeeper] = "";
        Runner runnable = new Runner(panelKeeper);
        new Thread(runnable).start();
    }

    public void reset(View view){
        init();
    }

    //**************HANDLER****************/
    public Handler threadHandler = new Handler(){
        public void handleMessage (android.os.Message message){
        }

    };

    // *************RUNNABLE***************/
    class Runner implements Runnable{
        Integer Clicked;
        Runner(Integer Clicked){
            this.Clicked = Clicked;
        }

        @Override
        public void run(){
            curx = Clicked % 9;
            cury = ( Clicked - curx )/ 9;
            String input = inputNum.getText().toString();
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    ((ArrayAdapter)gridView.getAdapter()).notifyDataSetChanged();
                    threadHandler.sendEmptyMessage(0);
                }
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void init(){
        Arrays.setAll(tiles, i -> "");
        tiles[1] = "3";
        tiles[4] = "1";
        tiles[7] = "6";
        tiles[9] = "7";
        tiles[10] = "5";
        tiles[13] = "3";
        tiles[16] = "4";
        tiles[17] = "8";
        tiles[20] = "6";
        tiles[21] = "9";
        tiles[22] = "8";
        tiles[23] = "4";
        tiles[24] = "3";
        tiles[29] = "3";
        tiles[33] = "8";
        tiles[36] = "9";
        tiles[37] = "1";
        tiles[38] = "2";
        tiles[42] = "6";
        tiles[43] = "7";
        tiles[44] = "4";
        tiles[47] = "4";
        tiles[51] = "5";
        tiles[56] = "1";
        tiles[57] = "6";
        tiles[58] = "7";
        tiles[59] = "5";
        tiles[60] = "2";
        tiles[63] = "6";
        tiles[64] = "8";
        tiles[67] = "9";
        tiles[70] = "1";
        tiles[71] = "5";
        tiles[73] = "9";
        tiles[76] = "4";
        tiles[79] = "3";

        ((ArrayAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }


}
