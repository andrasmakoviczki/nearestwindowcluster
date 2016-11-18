package hu.elte.databasesystems.util;

import hu.elte.databasesystems.model.DataObject;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Andras Makoviczki on 2016. 11. 07..
 */
public class ReadFromFile {
    private BufferedReader br;
    private File file;
    private ArrayList<String> readLines;
    private Integer absMaxValue;
    private Integer totalLoaded;

    public ReadFromFile(File file) {
        this.file = file;
        this.readLines = new ArrayList<String>();

        try {
            this.br = new BufferedReader(new FileReader(file));
            String actLine;

            while((actLine = br.readLine()) != null){
                this.readLines.add(actLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                this.br.close();
            }catch (IOException e) {
                e.printStackTrace();
            } ;
        }
    }
    public ArrayList<DataObject> ParseFile(String delimiter) {
        ArrayList<DataObject> dataObjArray = new ArrayList<DataObject>();
        Range r = new Range();
        euclidianDistance d = new euclidianDistance();

        for (String item: this.readLines) {
            StringTokenizer st = new StringTokenizer(item,delimiter);
            /*if(st.countTokens() != 3){
                NoSuchMethodException e = new NoSuchMethodException();
                throw e;
            }*/
            while(st.hasMoreElements()) {
                Integer x = (Integer.parseInt(st.nextElement().toString()));
                Integer y = (Integer.parseInt(st.nextElement().toString()));
                String name = (st.nextElement().toString());
                DataObject dataObj = new DataObject(x,y,name);
                dataObj.setQuadrant(x,y);
                d.setDistance(x,y,0,0);
                dataObj.setDistance(d.getDistance());
                r.minMaxSearch(x,y);
                dataObjArray.add(dataObj);
            }
        }
        absMaxValue = r.calculateRange();
        setTotalLoaded(dataObjArray);
        return dataObjArray;
    }

    public Integer getTotalLoaded() {
        return totalLoaded;
    }

    public void setTotalLoaded(ArrayList<DataObject> loaded) {
        this.totalLoaded = loaded.size();
    }

    public Integer getAbsMaxValue() {
        return absMaxValue;
    }

    public void setAbsMaxValue(Integer absMaxValue) {
        this.absMaxValue = absMaxValue;
    }
}
