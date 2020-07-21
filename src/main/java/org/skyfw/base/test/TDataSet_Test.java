package org.skyfw.base.test;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.skyfw.base.collection.TDataTreeSet;
import org.skyfw.base.datamodel.*;
import org.skyfw.base.datamodel.exception.TDataModelInitializationException;
import org.skyfw.base.exception.TException;
import org.skyfw.base.message.TGenericResponse;
import org.skyfw.base.serializing.TSerializer;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.test.datamodels.TTestMessage;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class TDataSet_Test {

    public static boolean doTest() throws TException{

        try {
            TDataModelInitiator.init(TTestMessage.class);
        }catch (TDataModelInitializationException e){
            return false;
        }


        TTestMessage models[] = new TTestMessage[10];
        for (int i = 0; i < models.length; i++) {
            models[i] = new TTestMessage();
            models[i].setMessageId(((Long)(long)new Random().nextInt(1000)).toString());
            models[i].setCreationTime(ThreadLocalRandom.current().nextLong(10000, 90000));
            models[i].setEditTime(ThreadLocalRandom.current().nextLong(10000, 90000));
            models[i].setMessageText("Alex" +  new Random().nextInt(10));
        }

        models[0].setMessageText("test");
        models[1].setMessageText("test");
        models[3].setMessageText(null);

        TDataSet<TTestMessage> dataSet = new TDataSet<>(models);
        dataSet.toArray();

        try{
            TFieldDescriptor fieldDescriptor= TDataModelHelper.getFieldDescriptor(dataSet.get(0), "editTime");
            TSortedDataSet<TTestMessage> sortedDataSet= new TDataTreeSet<>(fieldDescriptor);

            for (int i=0; i < dataSet.size(); i++){
                System.out.println("Item " +i+ ":\n" + dataSet.get(i));
                sortedDataSet.add(dataSet.get(i));
            }

            sortedDataSet.forEach(tTestMessage -> {
                //System.out.println(tTestMessage);
            });


            AtomicLong atomicLong= new AtomicLong(0);
            Spliterator<TTestMessage> spliterator1= sortedDataSet.spliterator();
            Spliterator<TTestMessage> spliterator2= spliterator1.trySplit();
            Spliterator<TTestMessage> spliterator3= spliterator2.trySplit();

            spliterator1.forEachRemaining(testMessage -> {
                System.out.println(testMessage.getEditTime());
                atomicLong.incrementAndGet();
                //sortedDataSet.add(models[0]);
            });

            spliterator2.forEachRemaining(testMessage -> {
                System.out.println(testMessage.getEditTime());
                atomicLong.incrementAndGet();
            });

            spliterator3.forEachRemaining(testMessage -> {
                System.out.println(testMessage.getEditTime());
                atomicLong.incrementAndGet();
            });

            System.out.println(atomicLong);

        }catch (Exception e){
            System.out.println(e);
        }




        TDataSet<TTestMessage> dataSet2= dataSet.search("messageText", "test").toDataSet()
                .search("messageText", null).toDataSet();

        dataSet2= dataSet.search("creationTime", 100l, 70000000l).toDataSet();

        dataSet2= dataSet.search("messageText", "Alex0", "Alex2").toDataSet();

        dataSet2= dataSet.search("messageText", null).toDataSet();



        dataSet.sortBy("messageText");

        dataSet.sortBy("editTime");


        String json= TSerializer.serializeToString(dataSet, TGsonAdapter.class);









        TDataSet<TTestMessage> dataSet3= TSerializer.deserializeFromString(json, TGsonAdapter.class
                , TDataSet.class, TTestMessage.class);
        System.out.println(dataSet3.size());


        try {
            dataSet.serializeToTextFile(new File("TestDataSet.txt"), TGsonAdapter.class);
        }catch (TException e){

        }

        try {
            TDataSet oldDataSet= new TDataSet(new TTestMessage[]{});
            oldDataSet.deserializeFromTextFile(new File("TestDataSet.txt"), TTestMessage.class);
            System.out.println(oldDataSet.size());
        } catch (TException e){

        }

        System.out.println(dataSet.getDataModelClass());

        try {
            TGenericResponse response = TGenericResponse.createTResponse(true);
            response.setValue("dataSet", dataSet);
            String s = response.serializeToString(TGsonAdapter.class);

            TGenericResponse response2 = TGenericResponse.createTResponse(true);
            response2.deserializeFromString(s, TGsonAdapter.class);
            System.out.println(response.getSize());
        }catch (TException e){
            return false;
        }


        /*for (int i = 0; i < dataSet.getOrginalSet().size(); i++) {
            system.out.println(dataSet.getOrginalSet().get(i).toString());//getFieldValue("name") + "  ");
            system.out.println(dataSet.getOrginalSet().get(i).toString());//getFieldValue("x") + "  ");
        }*/

        System.out.println();

        Long lll= models[0].getFieldValueAsLong("x");

        System.out.println(System.currentTimeMillis());
        //dataSet.seek("creationTime", 40000l, 50000l).toDataSet();
        TDataSet ds1 = dataSet.search("creationTime", 40000l, 50000l).toDataSet();
        ds1= ds1.search("messageText", "Alex1").toDataSet();
        System.out.println(System.currentTimeMillis());
        LinkedList<TDataModel> searched = ds1;
        for (int i = 0; i < searched.size(); i++) {
            System.out.print(searched.get(i).get("MessageText") + "  ");
            System.out.print(searched.get(i).get("CreationTime") + "  ");
        }
        System.out.println(System.currentTimeMillis());
        /*
        searched = ds1.getOrginalSet();
        for (int i = 0; i < searched.size(); i++)
            system.out.print(searched.get(i).getFieldValue("name") + "  ");
            */

        return true;
    }
}
