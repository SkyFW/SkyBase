package org.skyfw.base.serializing;

import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.pool.exception.TObjectPoolException;
import org.skyfw.base.serializing.adapters.TDefaultStringSerializerAdapter;
import org.skyfw.base.serializing.adapters.TStringSerializerAdapter;
import org.skyfw.base.result.TResult;
import org.skyfw.base.serializing.exception.TDeserializeException;
import org.skyfw.base.serializing.exception.TSerializeException;

import java.io.*;

public interface TSerializable<T extends TSerializable<T>> {

    static TLogger logger= TLogger.getLogger();


    static final String classNameToken= "ClassName:";
    static final String serializerLibNameToken= "SerializerLib:";


    public default String serializeToString(Class<? extends TStringSerializerAdapter> adapterClass)
            throws TSerializeException, TObjectPoolException, TIllegalArgumentException {

        return TSerializer.serializeToString(this, adapterClass);
    }

    /*public byte[] getAsByteArray(TStringSerializerLib encoderLib){
        byte[] result= byte[];

        return result;
    }*/

    public default ByteArrayOutputStream deserializeFromBytesArr(TDefaultStringSerializerAdapter encoderLib){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
        }
        catch(Exception e){
            this.logger.error("");
            return null;
        }

        return baos;
    }

    /*
    public TResult writeToOutputStream(OutputStream outputStream){

    }
    */

    //SAVE To AND LOAD From FILE METHODS
    //----------------------------------
    public default TResult serializeToTextFile(File file, Class<? extends TStringSerializerAdapter> adapterClass) throws TException{
        TResult result= TResult.create();


        String jsonString= this.serializeToString(adapterClass);
        /*if ((jsonString == null) || (jsonString.isEmpty()))
            return result.fail(TBaseMCode.SERVER_INTERNAL_ERROR, "SERIALIZATION_RESULT_IS_EMPTY");*/

        String className= this.getClass().getName();

        //Note: Common Pattern to Write file Line By Line
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            bw.write("ClassName: " + className);
            bw.newLine();

            bw.write("SerializerLib: " + adapterClass.toString());
            bw.newLine();

            bw.write(jsonString);

            bw.close();
        }catch (Exception e){

        }

        return result.finish();
    }











    public default T deserializeFromString(String jsonString, Class<? extends TStringSerializerAdapter> adapterClass
            , Class<T> mainClass, Class[] genericParams)
            throws TDeserializeException, TObjectPoolException, TIllegalArgumentException {

        return TSerializer.deserializeFromString(jsonString, adapterClass, mainClass, genericParams);
    }



    public default T deserializeFromString(String jsonString, Class<? extends TStringSerializerAdapter> adapterClass)
            throws TDeserializeException, TObjectPoolException, TIllegalArgumentException{

        return this.deserializeFromString(jsonString, adapterClass, (Class<T>) this.getClass(), null);
    }








    /*public byte[] getAsByteArray(TStringSerializerLib encoderLib){
        byte[] result= byte[];

        return result;
    }*/

   /* public Object fromByteArrayOutputStream(ByteArrayOutputStream stream, TStringSerializerLib encoderLib){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(obj);
            oos.close();
        }
        catch(exception e){
            TLogger.error();
            return null;
        }

        return stream;
    }*/

    public default T deserializeFromTextFile(File file, Class<? extends TStringSerializerAdapter> adapterClass
            , Class<T> mainClass, Class[] genericParams) throws TException{
        /*
        String className= null;
        String serializerLibName= null;

        try {
            String fileContent = TStringUtils.getStringFromFile(file);

            Scanner scanner = new Scanner(fileContent);
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int classNameIndex= line.indexOf(classNameToken);
                if (classNameIndex > -1){
                    className= line.substring(classNameIndex + classNameToken.length()
                            , line.length() - classNameIndex).trim();
                }
            }
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int serializerLibIndex= line.indexOf(serializerLibNameToken);
                if (serializerLibIndex > -1){
                    serializerLibName= line.substring(serializerLibIndex + serializerLibNameToken.length()
                            , line.length() - serializerLibIndex).trim();
                }
            }

            //Read All Remained Lines Of file
            scanner.useDelimiter("\\A");
            String remainedFileContent = scanner.hasNext() ? scanner.next() : null;
            scanner.close();

            if (mainClass == null) {
                mainClass = TSkyClassLoaderImpl1.loadByName(className);
            }

            if (mainClass == null)
                throw TException.create(TBaseMCode.UNKNOWN_DATATYPES);//, [className]);

            if (serializerLib == null)
                serializerLib= TDefaultStringSerializerAdapter.valueOf(serializerLibName);

            if (serializerLib == null)
                throw TException.create(TBaseMCode.BAD_REQUEST);

            //system.out.println(className + serializerLibName);

            TResult<T> fromJSON_Result=  gsonAdapterFromString(remainedFileContent, mainClass);
            result.addSubMethodResult(fromJSON_Result);

            return result.finish(fromJSON_Result);


        } catch (exception e) {
            return result.fail(TBaseMCode.SERVER_INTERNAL_ERROR, "exception: " + e.toString());
        }
        */



        /*
        //Note: Common Pattern to Read file Line By Line
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while (true) {
                line = br.readLine();
                system.out.println(line);
                // process the line.
            }
        }catch (exception e){ }
        */
        return null;
    }

    public default T deserializeFromTextFile(File file, Class<? extends TStringSerializerAdapter> adapterClass) throws TException{

        return this.deserializeFromTextFile(file, adapterClass, null, null);
    }


    public default T deserializeFromTextFile(File file
            , Class<? extends TStringSerializerAdapter> adapterClass
            , Class<T> mainClass) throws TException{

        return this.deserializeFromTextFile(file, adapterClass, mainClass, null);
    }


    //End Of: SAVE To AND LOAD From FILE METHODS
    //---------------------------------------------------------




}
