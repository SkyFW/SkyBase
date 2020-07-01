package org.skyfw.base.classes;

import org.skyfw.base.system.file.TFileUtils;

import java.io.File;

public class TFile extends File {

    public TFile(String pathname) {

        super(TFileUtils.convertSeparatorsToSystem(pathname));
    }
}
