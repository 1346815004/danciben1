package com.example.danciben1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DBOpenHelper dbOpenHelper;
    Button btn_1,btn_2,btn_3,btn_4;
    EditText et_1;
    ArrayList danciming = new ArrayList();
    ListView lv_1;
    String string1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbOpenHelper = new DBOpenHelper(MainActivity.this,"db_danci",null,1);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        et_1 = findViewById(R.id.et_1);
        lv_1 = findViewById(R.id.lv_1);

        ArrayList<Map<String, String>> dancilist = findall(dbOpenHelper.getReadableDatabase());
        if( dancilist != null && dancilist.size() != 0){
            danciming.clear();
            for ( int i=0; i<dancilist.size(); i++ ){
                danciming.add(dancilist.get(i).getOrDefault("danci","未查到"));
            }
            gengxin();
        }

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                string1 = (String) parent.getItemAtPosition(position);
                xiangxi();
            }
        });

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String danci = et_1.getText().toString();
                ArrayList<Map<String, String>> dancilist = find(dbOpenHelper.getReadableDatabase(), danci);
                if( dancilist != null && dancilist.size() != 0){
                    danciming.clear();
                    for ( int i=0; i<dancilist.size(); i++ ){
                        danciming.add(dancilist.get(i).getOrDefault("danci","未查到"));
                    }
                    gengxin();
                }else{
                    Toast.makeText(MainActivity.this, "未找到该单词", Toast.LENGTH_SHORT).show();
                }
                et_1.setText("");
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shangchuan();
            }
        });

        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiugai();
            }
        });

        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shanchu();
            }
        });


    }

    public void gengxinquanbu(){
        ArrayList<Map<String, String>> dancilist = findall(dbOpenHelper.getReadableDatabase());
        if( dancilist != null && dancilist.size() != 0){
            danciming.clear();
            for ( int i=0; i<dancilist.size(); i++ ){
                danciming.add(dancilist.get(i).getOrDefault("danci","未查到"));
            }
            Toast.makeText(MainActivity.this, "刷新", Toast.LENGTH_SHORT).show();
            gengxin();
        }else{
            Toast.makeText(MainActivity.this, "没刷新", Toast.LENGTH_SHORT).show();
        }
    }

    public void gengxin(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,danciming);
        ListView lv_1 = findViewById(R.id.lv_1);
        lv_1.setAdapter(adapter);
    }

    private void insertData(SQLiteDatabase sqLiteDatabase, String danci, String hanyi, String shili){
        ContentValues values = new ContentValues();
        values.put("danci",danci);
        values.put("hanyi",hanyi);
        values.put("shili",shili);
        sqLiteDatabase.insert("tb_danci", null, values);

    }

    private int update(SQLiteDatabase sqLiteDatabase, String danci, String hanyi, String shili){
        ContentValues values = new ContentValues();
        if(!hanyi.equals(""))
            values.put("hanyi",hanyi);
        if(!shili.equals(""))
            values.put("shili",shili);
        int number = sqLiteDatabase.update("tb_danci", values, "danci=?", new String[]{danci});

        return number;
    }

    private int delete(SQLiteDatabase sqLiteDatabase, String danci){
        int number = sqLiteDatabase.delete("tb_danci","danci=?", new String[]{danci});
        return number;
    }

    private ArrayList<Map<String, String>> find(SQLiteDatabase sqLiteDatabase, String danci){
//        Cursor cursor = sqLiteDatabase.query("tb_danci", null,
//                "danci=?", new String[]{danci}, null, null, null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from tb_danci where danci like ?", new String[]{"%"+danci+"%"});
        ArrayList<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        while (cursor.moveToNext()){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id",cursor.getString(0));
            map.put("danci",cursor.getString(1));
            map.put("hanyi",cursor.getString(2));
            map.put("shili",cursor.getString(3));
            resultList.add(map);
        }
        return resultList;
    }

    private ArrayList<Map<String, String>> findall(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.query("tb_danci", null,
                "", new String[]{}, null, null, null);
        ArrayList<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        while (cursor.moveToNext()){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id",cursor.getString(0));
            map.put("danci",cursor.getString(1));
            map.put("hanyi",cursor.getString(2));
            map.put("shili",cursor.getString(3));
            resultList.add(map);
        }
        return resultList;
    }

    public void xiangxi() {
        TextView tv_1 = findViewById(R.id.tv_1);
        TextView tv_2 = findViewById(R.id.tv_2);
        TextView tv_3 = findViewById(R.id.tv_3);

        ArrayList<Map<String, String>> dancilist = find(dbOpenHelper.getReadableDatabase(), string1);
        tv_1.setText(dancilist.get(0).getOrDefault("danci","test"));
        tv_2.setText(dancilist.get(0).getOrDefault("hanyi","test"));
        tv_3.setText(dancilist.get(0).getOrDefault("shili","test"));
    }

    public void shangchuan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.shangchuan_layout, null, false);
        builder.setTitle("上传单词")
                .setView(viewDialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_2 = viewDialog.findViewById(R.id.et_2);
                        EditText et_3 = viewDialog.findViewById(R.id.et_3);
                        EditText et_4 = viewDialog.findViewById(R.id.et_4);

                        String danci = et_2.getText().toString();
                        String hanyi = et_3.getText().toString();
                        String shili = et_4.getText().toString();

                        if (danci.equals("") || hanyi.equals("") || shili.equals("")){
                            Toast.makeText(MainActivity.this, "请全部填写", Toast.LENGTH_SHORT).show();
                        }else{
                            insertData(dbOpenHelper.getWritableDatabase(), danci, hanyi, shili);
                            Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            gengxinquanbu();
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    public void xiugai() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.shangchuan_layout, null, false);
        builder.setTitle("修改单词")
                .setView(viewDialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_2 = viewDialog.findViewById(R.id.et_2);
                        EditText et_3 = viewDialog.findViewById(R.id.et_3);
                        EditText et_4 = viewDialog.findViewById(R.id.et_4);

                        String danci = et_2.getText().toString();
                        String hanyi = et_3.getText().toString();
                        String shili = et_4.getText().toString();

                        if ( danci.equals("") || (hanyi.equals("") && shili.equals("")) ){
                            Toast.makeText(MainActivity.this, "请重新填写", Toast.LENGTH_SHORT).show();
                        }else{
                            int num = update(dbOpenHelper.getWritableDatabase(), danci, hanyi, shili);
                            if( num == 1 ){
                                Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                gengxinquanbu();
                            }
                            else
                                Toast.makeText(MainActivity.this, "未找到修改项", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    public void shanchu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.shanchu_layout, null, false);
        builder.setTitle("删除单词")
                .setView(viewDialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_5 = viewDialog.findViewById(R.id.et_5);

                        String danci = et_5.getText().toString();

                        if ( danci.equals("") ){
                            Toast.makeText(MainActivity.this, "请重新填写", Toast.LENGTH_SHORT).show();
                        }else{
                            int num = delete(dbOpenHelper.getWritableDatabase(), danci);
                            if( num > 0 ){
                                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                gengxinquanbu();
                            }
                            else
                                Toast.makeText(MainActivity.this, "未找到删除项", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( dbOpenHelper!=null){
            dbOpenHelper.close();
        }
    }
}