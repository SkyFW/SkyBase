package org.skyfw.base.system.jar;

import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.result.TResult;

import java.io.File;

public class TJarFileUtils {

    public static String getVersionFromJarManifest(File jarfile) throws TJarFileException, TIllegalArgumentException {

        //java.io.file file = new java.io.file("/drivers/h2/h2-1.3.162.jar");
        try {
            java.util.jar.JarFile jar = new java.util.jar.JarFile(jarfile);
            java.util.jar.Manifest manifest = jar.getManifest();

            String versionNumber = "";
            java.util.jar.Attributes attributes = manifest.getMainAttributes();
            if (attributes != null) {
                java.util.Iterator it = attributes.keySet().iterator();
                while (it.hasNext()) {
                    java.util.jar.Attributes.Name key = (java.util.jar.Attributes.Name) it.next();
                    String keyword = key.toString();//
                    // IMPLEMENTATION_VERSION
                    if (keyword.equals("Implementation-Version") || keyword.equals("Bundle-Version")) {
                        versionNumber = (String) attributes.get(key);
                        break;
                    }
                }
            }
            jar.close();

            return versionNumber;
        }catch (Exception e){
            throw TJarFileException.create(jarfile, e);
        }
    }



    public static String getVersionFromJarName(File jarFile){

        //java.io.file file = new java.io.file("/drivers/h2/h2-1.3.162.jar");
        String versionNumber = "";
        String fileName = jarFile.getName().substring(0, jarFile.getName().lastIndexOf("."));
        if (fileName.contains(".")){
            String majorVersion = fileName.substring(0, fileName.indexOf("."));
            String minorVersion = fileName.substring(fileName.indexOf("."));
            int delimiter = majorVersion.lastIndexOf("-");
            if (majorVersion.indexOf("_")>delimiter) delimiter = majorVersion.indexOf("_");
            majorVersion = majorVersion.substring(delimiter+1, fileName.indexOf("."));
            versionNumber = majorVersion + minorVersion;
        }

        return versionNumber;
    }

    public static String getArtifactNameFromJarName(File jarFile) throws TJarFileException, TIllegalArgumentException{

        String artifactName = "";
        String fileName = jarFile.getName().substring(0, jarFile.getName().lastIndexOf("."));
        if ((fileName.contains(".")) && (fileName.contains("-"))){
            artifactName= fileName.substring(0, fileName.indexOf("."));
            artifactName= artifactName.substring(0, artifactName.lastIndexOf("-"));

            return artifactName;
        }

        throw TJarFileException.create(TJarFileMCode.JAR_MODULE_FILE_NAME_IS_NOT_IN_VALID_FORMAT, jarFile);
    }

}
