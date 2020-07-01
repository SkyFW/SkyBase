package org.skyfw.base.test;

import org.skyfw.base.log.TLogger;

public class TLogger_Tester {

    public static void doTest(){


        TLogger logger= TLogger.getLogger();


        logger.debug("Java - Exceptions. An exception (or exceptional event) is a problem that arises during the execution of a program. When an exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.");
        logger.trace("Java - Exceptions. An exception (or exceptional event) is a problem that arises during the execution of a program. When an exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.");
        logger.info("Java - Exceptions. An exception (or exceptional event) is a problem that arises during the execution of a program. When an exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.");
        logger.warn("Java - Exceptions. An exception (or exceptional event) is a problem that arises during the execution of a program. When an exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.");
        logger.error("Java - Exceptions. An exception (or exceptional event) is a problem that arises during the execution of a program. When an exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.");
        logger.fatal("Java - Exceptions. An exception (or exceptional event) is a problem that arises during the execution of a program. When an exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.");


        System.out.println(System.currentTimeMillis());
        for (int i= 0; i < 10; i++){
            logger.debug("test");
        }
        System.out.println(System.currentTimeMillis());



    }



}
