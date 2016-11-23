package hu.elte.databasesystems.model.util;

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
    private final ArrayList<String> readLines;
    private BufferedReader br;
    private Integer absMaxValue;
    private Integer totalLoaded;

    public ReadFromFile(File file) {
        this.readLines = new ArrayList<String>();

        try {
            this.br = new BufferedReader(new FileReader(file));
            String actLine;

            while ((actLine = br.readLine()) != null) {
                this.readLines.add(actLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public RTree<Integer, DataObject> ParseFile() {
        @SuppressWarnings("unchecked") RTree<Integer, DataObject> rTree = new RTree();

        Range r = new Range();
        euclideanDistance d = new euclideanDistance();
        Integer idGenerator = 0;

        for (String item : this.readLines) {
            StringTokenizer st = new StringTokenizer(item, ":");
            /*if(st.countTokens() != 3){
                NoSuchMethodException e = new NoSuchMethodException();
                throw e;
            }*/
            while (st.hasMoreElements()) {
                Integer x = (Integer.parseInt(st.nextElement().toString()));
                Integer y = (Integer.parseInt(st.nextElement().toString()));
                String name = (st.nextElement().toString());

                DataObject dataObj = new DataObject(x, y, name);
                dataObj.setQuadrant();
                dataObj.setEdge();
                d.setDistance(x, y);
                dataObj.setDistance(d.getDistance());

                r.minMaxSearch(x, y);

                //noinspection unchecked
                rTree.add(new Entry(idGenerator, dataObj));
            }
        }
        absMaxValue = r.calculateRange();
        setTotalLoaded(rTree.getSize());
        return rTree;
    }

    public Integer getTotalLoaded() {
        return totalLoaded;
    }

    private void setTotalLoaded(Integer loaded) {
        this.totalLoaded = loaded;
    }

    public Integer getAbsMaxValue() {
        return absMaxValue;
    }

}
