package org.skyfw.base.system.maven;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.*;

public class TMavenUtils {

    public synchronized String getVersion(String jarFilePath) {
        String version = null;

        // try to load from maven properties first
        try {
            Properties p = new Properties();
            InputStream is = getClass().getResourceAsStream("/META-INF/maven/com.my.group/my-artefact/pom.properties");
            if (is != null) {
                p.load(is);
                version = p.getProperty("version", "");
            }
        } catch (Exception e) {
            // ignore
        }

        // fallback to using Java API
        if (version == null) {
            Package aPackage = getClass().getPackage();
            if (aPackage != null) {
                version = aPackage.getImplementationVersion();
                if (version == null) {
                    version = aPackage.getSpecificationVersion();
                }
            }
        }

        if (version == null) {
            // we could not compute the version so use a blank
            version = "";
        }

        return version;
    }

    public static Model getMavenModelFromJarFile(File  file){

        try {
            //Var(s)
            Model model = null;
            JarFile jarFile = new JarFile(file);
            try {
                Enumeration<JarEntry> enu = jarFile.entries();
                MavenXpp3Reader reader = new MavenXpp3Reader();
                while (enu.hasMoreElements()) {
                    JarEntry entry = enu.nextElement();
                    String name = entry.getName();
                    if (name.endsWith("pom.xml")) {
                        InputStream inputStream = jarFile.getInputStream(entry);
                        model = reader.read(new InputStreamReader(inputStream));
                        break;
                    }
                }//End Of: while Loop
            }finally {
                jarFile.close();
            }

            /*if (model == null)
                return result.fail(TMavenMCodes.MAVEN_MODEL_NOT_FOUND, null);
            else
                return result.succeed(model);*/
            return model;

        }catch (Exception e){
            return null;
            //return result.fail(TBaseMCode.BAD_REQUEST, null/*"EXCEPTION: " + e.toString()*/);
        }
    }


    public static String getVersionFromPOM(){

        /*try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model;
            if ((new File("pom.xml")).exists())
                model = reader.read(new FileReader("pom.xml"));
            else
                model = reader.read(
                        new InputStreamReader(
                                Application.class.getResourceAsStream(
                                        "/META-INF/maven/org.SkyFW/SkyBase/pom.xml"
                                )
                        )
                );

            //model.vers
            //model.getVersion()
            //system.out.println(model.getId());
            //system.out.println(model.getGroupId());
            //system.out.println(model.getArtifactId());
            System.out.println("Version: " + model.getVersion());
        } catch (Exception e){

        }


        */return null;
    }



}
