package hu.elte.databasesystems.util;

import hu.elte.databasesystems.model.DataObject;
import hu.elte.databasesystems.model.RTree;
import hu.elte.databasesystems.model.rtree.Entry;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Andras Makoviczki on 2016. 11. 07.
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
            }
        }
    }

    public RTree<Integer,DataObject> ParseFile(String delimiter) {
        RTree<Integer,DataObject> rTree = new RTree();

        Range r = new Range();
        euclidianDistance d = new euclidianDistance();
        Integer idGenerator = 0;

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

                rTree.add(new Entry(idGenerator,dataObj));
            }
        }
        absMaxValue = r.calculateRange();
        setTotalLoaded(rTree.getSize());
        return rTree;
    }

    public Integer getTotalLoaded() {
        return totalLoaded;
    }

    public void setTotalLoaded(Integer loaded) {
        this.totalLoaded = loaded;
    }

    public Integer getAbsMaxValue() {
        return absMaxValue;
    }

    public void setAbsMaxValue(Integer absMaxValue) {
        this.absMaxValue = absMaxValue;
    }
}
