package org.skyfw.base.result;

import org.skyfw.base.mcodes.TMCode;

/*
       _.--,_......----..__
       \  .'          '    ```--...__      \
        \;           '            .  '.   ||
        :           '            '     \ .''.
      .';          :            '       .|  |.--..___
     /   \         |           :        :|  /.------.\
    /    .'._      :           |        ||  ||      |\\
   /.-. /|-| `-.               :        ;|  ||______|_\`.______
  //  // |-|    \   '           '      / |  ||='      | |      `.
 //  //\\|-|     `-._'           '   .'  |  ||        | |        \
/.-.//  \\-|_________```------------` ___'. ||        | '_.--.   <)
'._.'  /  .-----.   .-----.   .''''''''.    |'--..____| /  _  \   |
       |_/.'   '.\_/.'   '.\_[ [ [  ] ] ]___|_________.'.'   '.\  ]
         :  .-.  : :  .-.  :  '........'    (_________):  .-.  :`-'
         :  '-'  : :  '-'  :                           :  '-'  :
          '._ _.'   '._ _.'                             '._ _.'
*********************************************************************************************************
    _______ _     _       _____               ____        _ _     _              _____ _
   |__   __| |   (_)     |_   _|             |  _ \      (_) |   | |            / ____| |
      | |  | |__  _ ___    | |  ___    __ _  | |_) |_   _ _| | __| | ___ _ __  | |    | | __ _ ___ ___
      | |  | '_ \| / __|   | | / __|  / _` | |  _ <| | | | | |/ _` |/ _ \ '__| | |    | |/ _` / __/ __|
      | |  | | | | \__ \  _| |_\__ \ | (_| | | |_) | |_| | | | (_| |  __/ |    | |____| | (_| \__ \__ \
      |_|  |_| |_|_|___/ |_____|___/  \__,_| |____/ \__,_|_|_|\__,_|\___|_|     \_____|_|\__,_|___/___/
*********************************************************************************************************
*/
public class TResultBuilder {
    private TMCode resultCode;
    private Object resultObject;

    public TResultBuilder setResultCode(TMCode resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public TResultBuilder setResultObject(Object resultObject) {
        this.resultObject = resultObject;
        return this;
    }

    public TResult createTResult() {
        return new TResult(resultCode, resultObject);
    }

    public static TResultBuilder getTResultBuilder(){return new TResultBuilder();}
}